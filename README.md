Functionality: fetch top posts from subreddit r/GameDeals and tweet them via Twitter bot.

# Requirements
* Java 8+
* Maven 3.6.3+
* PostgreSQL 12.3+
* (Optional) Docker version 19.03.8

# Installation
* Set Twitter API credentials in twitter4j.properties
* (Optional) Setup PostgreSQL in Docker
```shell script
sudo docker run --rm --name postgres-local
    -e POSTGRES_DB=game_deals_bot
    -e POSTGRES_PASSWORD=12345678
    -d -p 5432:5432
    postgres:12.3
```
* (Optional) Set database credentials in application.properties
* Run ```mvn install```
* Run ```java -jar application-name.jar```

# TODO
* Generalize application (rename, move subreddit name to configuration, etc.)
* Move hardcoded values to properties (minimum score. etc.)
* Optionally filter NSFW
* Filter by maximum age of posts 
