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

    private static final int TWITTER_STATUS_CHAR_LIMIT = 280;

    /**
     * @return true on success
     */
    public boolean updateStatus(RedditPost post) {
        String statusContent = getStatusContentFromRedditPost(post);
        return updateStatus(statusContent);
    }

    public boolean updateStatus(String content) {
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

    private String getStatusContentFromRedditPost(RedditPost redditPost) {
        String postLink =  "https://reddit.com" + redditPost.getPermalink();
        String status = redditPost.getTitle() + " " + postLink;

        // If compound title is longer that allowed, tweet only link. There doesn't seem to be a limit for links/
        if (status.length() > TWITTER_STATUS_CHAR_LIMIT) {
            return postLink;
        }

        return status;
    }
}
