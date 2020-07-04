package com.github.akhuntsaria.reddittotwitterbot.repository;

import com.github.akhuntsaria.reddittotwitterbot.domain.PostHistoryEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHistoryRepository extends JpaRepository<PostHistoryEntry, Long> {

    long countByRedditPostFullName(String redditPostFullName);
}
