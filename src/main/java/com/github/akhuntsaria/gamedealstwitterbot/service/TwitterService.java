package com.github.akhuntsaria.gamedealstwitterbot.service;

import com.github.akhuntsaria.gamedealstwitterbot.dto.RedditPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

@Service
public class TwitterService {

    private static final Logger log = LoggerFactory.getLogger(TwitterService.class);

    /**
     * @return true on success
     */
    public boolean updateStatus(RedditPost post) {
        String statusContent = getStatusContentFromRedditPost(post);
        return updateStatus(statusContent);
    }

    private String getStatusContentFromRedditPost(RedditPost redditPost) {
        return redditPost.getTitle() + " " + "https://reddit.com" + redditPost.getPermalink();
    }

    private boolean updateStatus(String content) {
        try {
            Twitter twitter = TwitterFactory.getSingleton();
            twitter.updateStatus(content);

            log.debug("Updated status to:  {}", content);
            return true;
        } catch (TwitterException e) {
            log.error("Couldn't update status with content: {}", content);
            e.printStackTrace();

            return false;
        }
    }
}
