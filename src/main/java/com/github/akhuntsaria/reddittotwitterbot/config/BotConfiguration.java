package com.github.akhuntsaria.reddittotwitterbot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "reddit-to-twitter-bot")
public class BotConfiguration {

    private boolean allowNsfw;

    // in seconds
    private int maxAge;

    private int minScore;

    private String subreddit;

    // in milliseconds
    private int tweetInterval;

    private String userAgent;

    public boolean getAllowNsfw() {
        return allowNsfw;
    }

    public void setAllowNsfw(boolean allowNsfw) {
        this.allowNsfw = allowNsfw;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public int getMinScore() {
        return minScore;
    }

    public void setMinScore(int minScore) {
        this.minScore = minScore;
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
