package com.github.akhuntsaria.reddittotwitterbot.dto;

import java.util.List;

public class RedditListingData {
    private List<RedditPost> children;

    public List<RedditPost> getChildren() {
        return children;
    }

    public void setChildren(List<RedditPost> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "RedditListingData{" +
                "children=" + children +
                '}';
    }
}
