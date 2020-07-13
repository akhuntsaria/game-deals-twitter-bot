package com.github.akhuntsaria.reddittotwitterbot;

import com.github.akhuntsaria.reddittotwitterbot.service.RedditToTwitterService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private final RedditToTwitterService redditToTwitterService;

    public ScheduledTasks(RedditToTwitterService redditToTwitterService) {
        this.redditToTwitterService = redditToTwitterService;
    }

    @Scheduled(fixedDelayString = "${reddit-to-twitter-bot.tweet-interval}")
    protected void scheduled() {
        redditToTwitterService.findPostAndUpdateStatus();
    }
}
