package com.github.akhuntsaria.reddittotwitterbot.dto;

public class RedditPost {
    private RedditPostData data;

    public RedditPostData getData() {
        return data;
    }

    public void setData(RedditPostData data) {
        this.data = data;
    }

    public long getCreated() {
        return this.data.getCreated();
    }

    public String getName() {
       return this.data.getName();
    }

    public boolean isOver18() {
        return this.data.isOver18();
    }

    public String getPermalink() {
        return this.data.getPermalink();
    }

    public int getScore() {
        return this.data.getScore();
    }

    public String getTitle() {
        return this.data.getTitle();
    }

    @Override
    public String toString() {
        return "RedditPost{" +
                "data=" + data +
                '}';
    }
}
