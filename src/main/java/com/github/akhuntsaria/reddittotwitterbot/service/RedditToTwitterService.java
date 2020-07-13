package com.github.akhuntsaria.reddittotwitterbot.service;

import com.github.akhuntsaria.reddittotwitterbot.domain.PostHistoryEntry;
import com.github.akhuntsaria.reddittotwitterbot.dto.RedditPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RedditToTwitterService {

    private static final Logger log = LoggerFactory.getLogger(RedditToTwitterService.class);

    private final PostHistoryService postHistoryService;

    private final RedditService redditService;

    private final TwitterService twitterService;

    public RedditToTwitterService(PostHistoryService postHistoryService, RedditService redditService, TwitterService twitterService) {
        this.postHistoryService = postHistoryService;
        this.redditService = redditService;
        this.twitterService = twitterService;
    }

    public void findPostAndUpdateStatus() {
        RedditPost post = redditService.getFilteredPost();

        if (post == null) {
            log.info("No matching posts were found.");
            return;
        } else {
            log.info("Updating status with post {}", post.toString());
        }

        boolean updatedSuccessfully = twitterService.updateStatus(post);

        if (updatedSuccessfully) {
            postHistoryService.save(new PostHistoryEntry(post.getName()));
        }
    }
}
