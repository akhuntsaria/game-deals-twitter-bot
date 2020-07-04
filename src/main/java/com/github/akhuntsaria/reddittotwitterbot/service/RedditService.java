package com.github.akhuntsaria.reddittotwitterbot.service;

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

//TODO: move subreddit's name to properties
@Service
public class RedditService {

    private static final Logger log = LoggerFactory.getLogger(RedditService.class);

    private static final String API_NEW_POSTS_ENDPOINT = "https://www.reddit.com/r/gamedeals/new/.json";
    private static final String API_USER_AGENT = "Reddit-To-Twitter Bot 0.1";
    private static final int POSTS_LIMIT = 100;
    private static final int POST_MINIMUM_SCORE = 50;

    private final PostHistoryService postHistoryService;

    public RedditService(PostHistoryService postHistoryService) {
        this.postHistoryService = postHistoryService;
    }

    /**
     * Return post with which haven't been tweeted yet and with score >= POST_MINIMUM_SCORE.
     */
    public RedditPost getNewPostWithMinimumScore() {
        List<RedditPost> posts = getNewPostsWithMinimumScore();

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

    private List<RedditPost> getNewPostsWithMinimumScore() {
        List<RedditPost> posts = getNewPosts();
        posts = posts.stream().filter(redditPost -> redditPost.getScore() >= POST_MINIMUM_SCORE)
                .collect(Collectors.toList());

        log.debug("There are {} new posts with score more than {}", posts.size(), POST_MINIMUM_SCORE);

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
        headers.set("User-Agent", API_USER_AGENT);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_NEW_POSTS_ENDPOINT)
                .queryParam("limit", POSTS_LIMIT);

        try {
            HttpEntity<RedditListing> response = (new RestTemplate()).exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    RedditListing.class);

            RedditListing redditListing = response.getBody();
            List<RedditPost> redditPosts = Objects.requireNonNull(redditListing).getData().getChildren();

            log.debug("Fetched {} new posts from {}", redditPosts.size(), API_NEW_POSTS_ENDPOINT);
            return redditPosts;

        } catch (Exception e) {
            log.error("Couldn't fetch posts from {}", API_NEW_POSTS_ENDPOINT);
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
