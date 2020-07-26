# Reddit to Twitter Bot
Fetches posts from some subreddit and tweets them. Most of the functionality is configurable. Database is used for saving tweeted posts (to prevent tweeting of duplicates).

# Demo
https://twitter.com/gamedeals_bot

# Requirements
* Java 11.*
* Maven 3.*
* PostgreSQL 12.*
* (Optional) Docker version 19.*

# Installation
* Change properties in application.properties
    * Database credentials
    * Subreddit's name
    * Minimum score of Reddit post
    * Tweet interval (default is 'once every hour')
    * User agent for Reddit API (to prevent 'too many requests' errors)
    * Allow or not NSFW posts
    * Maximum age of posts
* Set Twitter API credentials in twitter4j.properties
* (Optional) Setup PostgreSQL in Docker
```shell script
sudo docker run --rm --name postgres-local \
    -e POSTGRES_DB=reddit_to_twitter_bot \
    -e POSTGRES_PASSWORD=12345678 \
    -d -p 5432:5432 \
    postgres:12.3
```
* (Optional) Set database credentials in application.properties
* Run ```mvn install```
* Run ```java -jar application-name.jar```

# TODO
* Optionally delete old post history rows
* Make it possible to pass properties via program arguments
    * Add default values to properties
* Add support for appending hashtags to tweets