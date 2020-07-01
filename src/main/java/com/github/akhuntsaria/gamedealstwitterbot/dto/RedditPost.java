package com.github.akhuntsaria.gamedealstwitterbot.dto;

public class RedditPost {
    private RedditPostData data;

    public RedditPostData getData() {
        return data;
    }

    public void setData(RedditPostData data) {
        this.data = data;
    }

    public String getName() {
       return this.data.getName();
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
