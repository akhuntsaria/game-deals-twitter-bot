package com.github.akhuntsaria.gamedealstwitterbot;

import com.github.akhuntsaria.gamedealstwitterbot.model.RedditListing;
import com.github.akhuntsaria.gamedealstwitterbot.model.RedditPost;
import com.github.akhuntsaria.gamedealstwitterbot.model.RedditPostData;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@EnableScheduling
@SpringBootApplication
public class Application {

	private static final String FILE_LATEST_TWEETED = "latest-tweeted.txt";


	@Scheduled(fixedDelay = 360_000) // once every hour
	protected void scheduledTweet() throws TwitterException {
		String postTitle = getLatestNotTweetedPostTitle();

		if (postTitle == null) {
			System.out.println("No new posts");
		} else {
			Twitter twitter = TwitterFactory.getSingleton();
			Status status = twitter.updateStatus(postTitle);
			System.out.println("Successfully updated the status to [" + status + "].");
		}
	}

	protected String getLatestNotTweetedPostTitle() {
		//https://www.reddit.com/r/gamedeals/new/.json?limit=1&before=t2_3omuebkp

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("User-Agent", "GameDeals Twitter Bot 0.1");

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://www.reddit.com/r/gamedeals/new/.json")
				.queryParam("limit", 1);

		String latestTweetedPostName = getLatestTweetedPostName();
		if (latestTweetedPostName != null) {
			builder.queryParam("before", latestTweetedPostName);
		}

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<RedditListing> response = (new RestTemplate()).exchange(
				builder.toUriString(),
				HttpMethod.GET,
				entity,
				RedditListing.class);

		System.out.println(response);

		RedditListing redditListing = response.getBody();
		List<RedditPost> posts = Objects.requireNonNull(redditListing).getData().getChildren();

		if (posts.size() > 0) {
			RedditPostData redditPostData = posts.get(0).getData();
			System.out.println(redditPostData.toString());

			saveLatestTweetedPostName(redditPostData.getName());

			return redditPostData.getTitle() + " https://reddit.com" + redditPostData.getPermalink();
		}

		return null;
	}

	protected String getLatestTweetedPostName() {
		try {
			FileInputStream fileInputStream = new FileInputStream(getLatestTweetedFile());

			String latestTweetedPostName = IOUtils.toString(fileInputStream, StandardCharsets.UTF_8);

			if (StringUtils.isEmpty(latestTweetedPostName)) {
				return null;
			}

			return latestTweetedPostName;
		} catch (IOException e) {
			e.printStackTrace();

			return null;
		}
	}

	protected void saveLatestTweetedPostName(String latestTweetedPostName) {
		try {
			FileUtils.writeStringToFile(getLatestTweetedFile(), latestTweetedPostName, StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected File getLatestTweetedFile() {
		return new File(ClassLoader.getSystemResource(FILE_LATEST_TWEETED).getFile());
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
