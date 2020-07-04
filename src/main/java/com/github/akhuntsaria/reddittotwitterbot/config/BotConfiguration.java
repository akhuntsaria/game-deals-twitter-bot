package com.github.akhuntsaria.reddittotwitterbot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "reddit-to-twitter-bot")
public class BotConfiguration {

    private int minimumScore;

    private String subreddit;

    private int tweetInterval;

    private String userAgent;

    public int getMinimumScore() {
        return minimumScore;
    }

    public void setMinimumScore(int minimumScore) {
        this.minimumScore = minimumScore;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public int getTweetInterval() {
        return tweetInterval;
    }

    public void setTweetInterval(int tweetInterval) {
        this.tweetInterval = tweetInterval;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
