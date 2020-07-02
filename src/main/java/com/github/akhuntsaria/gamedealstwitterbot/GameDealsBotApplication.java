package com.github.akhuntsaria.gamedealstwitterbot;

import com.github.akhuntsaria.gamedealstwitterbot.dto.RedditPost;
import com.github.akhuntsaria.gamedealstwitterbot.service.RedditToTwitterService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.TimeZone;

//TODO: rename, generalize project
@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class GameDealsBotApplication {

	private final RedditToTwitterService redditToTwitterService;

	public GameDealsBotApplication(RedditToTwitterService redditToTwitterService) {
		this.redditToTwitterService = redditToTwitterService;

		// Setting time zone manually, because it doesn't work in hibernate's properties for some reason.
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}


	public static void main(String[] args) {
		SpringApplication.run(GameDealsBotApplication.class, args);
	}

	@Scheduled(fixedDelay = 3600_000) // once every hour
	protected void scheduled() {
		redditToTwitterService.findPostAndUpdateStatus();
	}
}
