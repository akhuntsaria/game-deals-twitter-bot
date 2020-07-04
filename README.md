Fetches top posts from some subreddit and tweets them. Database is used for saving tweeted posts (to prevent tweeting of duplicates).

# Requirements
* Java 8+
* Maven 3.6.3+
* PostgreSQL 12.3+
* (Optional) Docker version 19.03.8

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
sudo docker run --rm --name postgres-local
    -e POSTGRES_DB=reddit_to_twitter_bot
    -e POSTGRES_PASSWORD=12345678
    -d -p 5432:5432
    postgres:12.3
```
* (Optional) Set database credentials in application.properties
* Run ```mvn install```
* Run ```java -jar application-name.jar```

# TODO
* Filter by maximum age of posts 
* Write tests for Reddit API
* Optionally delete old post history rows
* Optimize filtering of already-twitted posts
* Make it possible to pass properties via program arguments
    * Add default values to properties