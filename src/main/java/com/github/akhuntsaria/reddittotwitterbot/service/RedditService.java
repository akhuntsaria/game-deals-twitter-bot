package com.github.akhuntsaria.reddittotwitterbot.service;

import com.github.akhuntsaria.reddittotwitterbot.config.BotConfiguration;
import com.github.akhuntsaria.reddittotwitterbot.dto.RedditListing;
import com.github.akhuntsaria.reddittotwitterbot.dto.RedditPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RedditService {

    private static final Logger log = LoggerFactory.getLogger(RedditService.class);

    // %s is for subreddit's name
    private static final String API_NEW_POSTS_ENDPOINT = "https://www.reddit.com/r/%s/new/.json";
    private static final int POSTS_LIMIT = 100; // reddit allows fetching of max 100 posts

    private final PostHistoryService postHistoryService;

    private final BotConfiguration botConfiguration;

    public RedditService(PostHistoryService postHistoryService, BotConfiguration botConfiguration) {
        this.postHistoryService = postHistoryService;
        this.botConfiguration = botConfiguration;
    }

    /**
     * Return post with which haven't been tweeted yet and which matches filter properties.
     */
    public RedditPost getFilteredPost() {
        List<RedditPost> posts = getFilteredPosts();

        if (posts.size() == 0) {
            return null;
        }

        List<String> postHistoryFullNames = postHistoryService.findRedditPostFullNames();

        for (RedditPost post : posts) {
            if (postHistoryFullNames.contains(post.getName())) {
                continue;
            }

            log.debug("Found post to tweet: {}", post.toString());
            return post;
        }

        log.debug("Couldn't find any post to tweet.");
        return null;
    }

    /**
     * Filter posts by minimum score and other properties
     */
    private List<RedditPost> getFilteredPosts() {
        // min created timestamp in seconds
        long postMinCreated = getCurrentUtcTimestampInSeconds() - botConfiguration.getMaxAge();

        List<RedditPost> posts = getNewPosts();
        posts = posts.stream()
                .filter(redditPost -> redditPost.getScore() >= botConfiguration.getMinScore())
                .filter(redditPost -> redditPost.isOver18() == botConfiguration.getAllowNsfw())
                .filter(redditPost -> redditPost.getCreated() >= postMinCreated)
                .collect(Collectors.toList());

        log.debug("There are {} new posts with score more than {}", posts.size(), botConfiguration.getMinScore());

        // changing order from date DESC to date ASC to tweet posts in correct order
        Collections.reverse(posts);
        return posts;
    }

    /**
     * Fetch POSTS_LIMIT posts from API_NEW_POSTS_ENDPOINT sorted by date descending.
     */
    private List<RedditPost> getNewPosts() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        // User-Agent is set to avoid 'too many requests' errors
        headers.set("User-Agent", botConfiguration.getUserAgent());

        final String newPostsUrl = String.format(API_NEW_POSTS_ENDPOINT, botConfiguration.getSubreddit());

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(newPostsUrl)
                .queryParam("limit", POSTS_LIMIT);

        try {
            HttpEntity<RedditListing> response = (new RestTemplate()).exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    RedditListing.class);

            RedditListing redditListing = response.getBody();
            List<RedditPost> redditPosts = Objects.requireNonNull(redditListing).getData().getChildren();

            log.debug("Fetched {} new posts from {}", redditPosts.size(), newPostsUrl);
            return redditPosts;

        } catch (Exception e) {
            log.error("Couldn't fetch posts from {}", API_NEW_POSTS_ENDPOINT);
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private long getCurrentUtcTimestampInSeconds() {
        return System.currentTimeMillis() / 1000;
    }
}
