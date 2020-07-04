package com.github.akhuntsaria.reddittotwitterbot.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Contains Reddit posts which were tweeted.
 */
@Entity
@Table(name = "post_history")
public class PostHistoryEntry implements Serializable {

    private static final long serialVersionUID = 3933544674429709392L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TODO: @CreatedAt? There's NPE
    @Column(nullable = false)
    private Date postedAt;

    /**
     * https://www.reddit.com/dev/api/#listings
     * type + id, e.g. t3_15bfi0
     */
    @Column(nullable = false)
    private String redditPostFullName;

    public PostHistoryEntry() {
    }

    public PostHistoryEntry(String redditPostFullName) {
        this.postedAt = new Date();
        this.redditPostFullName = redditPostFullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(Date postedAt) {
        this.postedAt = postedAt;
    }

    public String getRedditPostFullName() {
        return redditPostFullName;
    }

    public void setRedditPostFullName(String redditPostFullName) {
        this.redditPostFullName = redditPostFullName;
    }

    @Override
    public String toString() {
        return "PostHistoryEntry{" +
                "id=" + id +
                ", postedAt=" + postedAt +
                ", redditPostFullName='" + redditPostFullName + '\'' +
                '}';
    }
}
