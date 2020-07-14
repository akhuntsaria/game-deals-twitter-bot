package com.github.akhuntsaria.reddittotwitterbot.util;

public class DateUtil {

    public static long getCurrentUtcTimestampInSeconds() {
        return System.currentTimeMillis() / 1000;
    }
}
