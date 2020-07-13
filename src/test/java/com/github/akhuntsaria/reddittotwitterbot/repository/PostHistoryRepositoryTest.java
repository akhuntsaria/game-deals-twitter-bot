package com.github.akhuntsaria.reddittotwitterbot.repository;

import com.github.akhuntsaria.reddittotwitterbot.domain.PostHistoryEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostHistoryRepositoryTest {

    @Autowired
    private PostHistoryRepository postHistoryRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCountByRedditPostFullName() {
        // given
        entityManager.persist(new PostHistoryEntry("t3_haucpf"));
        entityManager.flush();

        // assert
        assertThat(postHistoryRepository.countByRedditPostFullName("t3_haucpf")).isEqualTo(1);
        assertThat(postHistoryRepository.countByRedditPostFullName("t3_haucp")).isEqualTo(0);
    }
}
