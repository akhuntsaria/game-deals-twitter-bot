package com.github.akhuntsaria.reddittotwitterbot;

import com.github.akhuntsaria.reddittotwitterbot.service.RedditToTwitterService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.TimeZone;

@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class Application {

	private final RedditToTwitterService redditToTwitterService;

	public Application(RedditToTwitterService redditToTwitterService) {
		this.redditToTwitterService = redditToTwitterService;

		// Setting time zone manually, because it doesn't work in hibernate's properties for some reason.
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Scheduled(fixedDelay = 3600_000) // once every hour
	protected void scheduled() {
		redditToTwitterService.findPostAndUpdateStatus();
	}
}
