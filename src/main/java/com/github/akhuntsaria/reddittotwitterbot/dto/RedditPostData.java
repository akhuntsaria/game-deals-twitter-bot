package com.github.akhuntsaria.reddittotwitterbot.dto;

public class RedditPostData {

    private String name;

    private boolean over_18;

    private String permalink;

    private int score;

    private String title;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOver18() {
        return over_18;
    }

    public void setOver18(boolean over_18) {
        this.over_18 = over_18;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "RedditPostData{" +
                "name='" + name + '\'' +
                ", permalink='" + permalink + '\'' +
                ", score=" + score +
                ", title='" + title + '\'' +
                '}';
    }
}
