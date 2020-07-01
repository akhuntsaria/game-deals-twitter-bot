package com.github.akhuntsaria.gamedealstwitterbot.service;

import com.github.akhuntsaria.gamedealstwitterbot.domain.PostHistoryEntry;
import com.github.akhuntsaria.gamedealstwitterbot.dto.RedditPost;
import com.github.akhuntsaria.gamedealstwitterbot.repository.PostHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

//TODO: delete old (> 1 week) post history rows
@Service
public class PostHistoryService {

    private static final Logger log = LoggerFactory.getLogger(PostHistoryService.class);

    private final PostHistoryRepository postHistoryRepository;

    public PostHistoryService(PostHistoryRepository postHistoryRepository) {
        this.postHistoryRepository = postHistoryRepository;
    }

    public List<String> findRedditPostFullNames() {
        return postHistoryRepository.findRedditPostFullNames();
    }

    public void save(String postFullName) {
        PostHistoryEntry postHistoryEntry = new PostHistoryEntry(postFullName);
        postHistoryEntry = postHistoryRepository.save(postHistoryEntry);

        log.debug("Save new post history entry: {}", postHistoryEntry.toString());
    }
}
