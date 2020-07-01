package com.github.akhuntsaria.gamedealstwitterbot.model;

public class RedditPostData {

    private String name;

    private String permalink;

    private String title;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
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
                ", title='" + title + '\'' +
                '}';
    }
}
