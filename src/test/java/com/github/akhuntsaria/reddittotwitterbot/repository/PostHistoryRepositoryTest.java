package com.github.akhuntsaria.reddittotwitterbot.repository;

import com.github.akhuntsaria.reddittotwitterbot.domain.PostHistoryEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

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

    @Test
    public void testSave() {
        // init
        postHistoryRepository.deleteAll();
        assertThat(postHistoryRepository.count()).isEqualTo(0);

        // when
        postHistoryRepository.save(new PostHistoryEntry("t1_something"));

        // assert
        assertThat(1).isEqualTo(postHistoryRepository.count());

        PostHistoryEntry actualPostHistoryEntry = postHistoryRepository.findAll().get(0);
        assertThat(actualPostHistoryEntry.getId()).isNotNull();
        assertThat(actualPostHistoryEntry.getPostedAt()).isCloseTo(new Date(), 1000);
        assertThat(actualPostHistoryEntry.getRedditPostFullName()).isEqualTo("t1_something");
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testSave_RedditPostFullNameUniqueConstraint() {
        postHistoryRepository.save(new PostHistoryEntry("t3_haucpf"));
        postHistoryRepository.save(new PostHistoryEntry("t3_haucpf"));
    }
}
