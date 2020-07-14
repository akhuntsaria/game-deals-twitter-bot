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
}
