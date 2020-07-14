package com.github.akhuntsaria.reddittotwitterbot.service;

import com.github.akhuntsaria.reddittotwitterbot.domain.PostHistoryEntry;
import com.github.akhuntsaria.reddittotwitterbot.repository.PostHistoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostHistoryServiceTest {

    @Mock
    private PostHistoryRepository postHistoryRepository;

    @InjectMocks
    private PostHistoryService postHistoryService;

    @Test
    public void testPostHistoryEntryExists() {
        when(postHistoryRepository.countByRedditPostFullName("t1_something")).thenReturn(1L);
        when(postHistoryRepository.countByRedditPostFullName("t1_something_else")).thenReturn(0L);

        assertThat(postHistoryService.postHistoryEntryExists("t1_something")).isEqualTo(true);
        assertThat(postHistoryService.postHistoryEntryExists("t1_something_else")).isEqualTo(false);
    }

    @Test
    public void testSave() {
        PostHistoryEntry postHistoryEntry = new PostHistoryEntry("t1_something");

        when(postHistoryRepository.save(any(PostHistoryEntry.class))).thenReturn(postHistoryEntry);

        assertThat(postHistoryService.save(postHistoryEntry)).isEqualTo(postHistoryEntry);
        verify(postHistoryRepository).save(any(PostHistoryEntry.class));
    }
}
