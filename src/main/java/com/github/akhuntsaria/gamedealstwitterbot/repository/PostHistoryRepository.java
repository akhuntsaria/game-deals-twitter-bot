package com.github.akhuntsaria.gamedealstwitterbot.repository;

import com.github.akhuntsaria.gamedealstwitterbot.domain.PostHistoryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostHistoryRepository extends JpaRepository<PostHistoryEntry, Long> {

    @Query("select phe.redditPostFullName from PostHistoryEntry phe")
    List<String> findRedditPostFullNames();
}
