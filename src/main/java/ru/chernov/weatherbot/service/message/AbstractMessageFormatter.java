package ru.chernov.weatherbot.service.message;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;


public interface AbstractMessageFormatter<T> {

    String formatMessage(T dto);


    default String getZonedTime(Integer secs, Integer zoneSecs) {
        Instant instant = Instant.ofEpochSecond(secs);
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(zoneSecs);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, zoneOffset);
        return zonedDateTime.toLocalTime().toString();
    }

}
