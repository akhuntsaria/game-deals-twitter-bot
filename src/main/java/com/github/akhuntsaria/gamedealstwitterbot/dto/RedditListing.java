package com.github.akhuntsaria.gamedealstwitterbot.dto;

public class RedditListing {

    private RedditListingData data;

    public RedditListingData getData() {
        return data;
    }

    public void setData(RedditListingData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RedditListing{" +
                "data=" + data +
                '}';
    }
}
