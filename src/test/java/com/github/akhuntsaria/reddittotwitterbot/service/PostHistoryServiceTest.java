package com.github.akhuntsaria.reddittotwitterbot.service;

import com.github.akhuntsaria.reddittotwitterbot.domain.PostHistoryEntry;
import com.github.akhuntsaria.reddittotwitterbot.repository.PostHistoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class PostHistoryServiceTest {

    @Mock
    private PostHistoryRepository postHistoryRepository;

    @InjectMocks
    private PostHistoryService postHistoryService;

    @Test
    public void testPostHistoryEntryExists() {
        given(postHistoryRepository.countByRedditPostFullName("t1_something")).willReturn(1L);
        given(postHistoryRepository.countByRedditPostFullName("t1_something_else")).willReturn(0L);

        assertThat(postHistoryService.postHistoryEntryExists("t1_something")).isEqualTo(true);
        assertThat(postHistoryService.postHistoryEntryExists("t1_something_else")).isEqualTo(false);
    }

    @Test
    public void testSave() {
        PostHistoryEntry postHistoryEntry = new PostHistoryEntry("t1_something");
        given(postHistoryRepository.save(postHistoryEntry)).willReturn(postHistoryEntry);

        assertThat(postHistoryService.save(postHistoryEntry)).isEqualTo(postHistoryEntry);
    }
}
