package com.github.akhuntsaria.reddittotwitterbot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "reddit-to-twitter-bot")
public class BotConfiguration {

    private String subreddit;

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }
}
