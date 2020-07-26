package com.github.akhuntsaria.reddittotwitterbot.service;

import com.github.akhuntsaria.reddittotwitterbot.config.BotConfiguration;
import com.github.akhuntsaria.reddittotwitterbot.dto.RedditPost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RedditServiceTest {

    @Mock
    private BotConfiguration botConfiguration;

    @Mock
    private PostHistoryService postHistoryService;

    @InjectMocks
    private RedditService redditService;

    @Test
    public void testGetFilteredPost_shouldReturnNull_whenNoPosts() {
        // Spying is needed to stub local method
        RedditService redditServiceSpy = spy(redditService);

        doReturn(new ArrayList<>()).when(redditServiceSpy).getFilteredPosts();

        assertThat(redditServiceSpy.getFilteredPost()).isNull();
        verify(postHistoryService, never()).postHistoryEntryExists(anyString());
    }

    @Test
    public void testGetFilteredPost_shouldReturnNull_whenPostHistoryEntryExists() {
        RedditService redditServiceSpy = spy(redditService);

        RedditPost redditPost = new RedditPost();
        redditPost.getData().setName("t3_something");

        doReturn(Collections.singletonList(redditPost)).when(redditServiceSpy).getFilteredPosts();
        when(postHistoryService.postHistoryEntryExists(redditPost.getName())).thenReturn(true);

        assertThat(redditServiceSpy.getFilteredPost()).isNull();
    }

    /**
     * Test for default behaviour
     */
    @Test
    public void testGetFilteredPost() {
        RedditService redditServiceSpy = spy(redditService);

        RedditPost redditPost = new RedditPost();
        redditPost.getData().setName("t3_something");

        doReturn(Collections.singletonList(redditPost)).when(redditServiceSpy).getFilteredPosts();
        // post wasn't used yet
        when(postHistoryService.postHistoryEntryExists(redditPost.getName())).thenReturn(false);

        assertThat(redditServiceSpy.getFilteredPost()).isEqualTo(redditPost);
    }

    @Test
    public void testGetNewPosts() {
        // Mocking the configuration for fetching posts
        when(botConfiguration.getUserAgent()).thenReturn("Reddit to Twitter Bot Test");
        when(botConfiguration.getSubreddit()).thenReturn("announcements"); // The most popular subreddit
        when(botConfiguration.getLimitOfPostsToDownload()).thenReturn(1); // Changing to 1 for performance (default is 100)


        List<RedditPost> redditPostList = redditService.getNewPosts();
        assertThat(redditPostList.size()).isEqualTo(1);

        // Make sure fields which are used in this application are not not or empty
        RedditPost redditPost = redditPostList.get(0);
        assertThat(redditPost.getData()).isNotNull();
        assertThat(redditPost.getCreated()).isGreaterThan(0);
        assertThat(redditPost.getName()).hasSizeGreaterThan(0);
        assertThat(redditPost.getPermalink()).hasSizeGreaterThan(0);
        // Assuming post in the most popular subreddit can't practically have 0 score
        assertThat(redditPost.getScore()).isNotEqualTo(0);
        assertThat(redditPost.getTitle()).hasSizeGreaterThan(0);
    }
}
