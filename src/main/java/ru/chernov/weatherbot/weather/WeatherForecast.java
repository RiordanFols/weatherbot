package ru.chernov.weatherbot.weather;

import lombok.Getter;
import ru.chernov.weatherbot.dto.OpenWeatherDto;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * @author Pavel Chernov
 */
@Getter
public final class WeatherForecast {

    private final WeatherCondition condition;

    private final String temp;
    private final String tempFeelsLike;

    private final String pressure;
    private final String humidity;

    private final String sunriseTime;
    private final String sunsetTime;

    public WeatherForecast(OpenWeatherDto dto) {
        this.condition = WeatherCondition.valueOf(dto.getCondition().toUpperCase());
        this.pressure = String.format("%.0f мм рт.ст.", dto.getPressure() * 3 / 4.0);
        this.humidity = dto.getHumidity() + "%";

        this.temp = String.format("%.0f°C", dto.getTemp());
        this.tempFeelsLike = String.format("%.0f°C", dto.getTempFeelsLike());

        this.sunriseTime = getZonedTime(dto.getSunriseTime(), dto.getTimezone());
        this.sunsetTime = getZonedTime(dto.getSunsetTime(), dto.getTimezone());
    }

    private String getZonedTime(long secs, int zoneSecs) {
        var instant = Instant.ofEpochSecond(secs);
        var zoneOffset = ZoneOffset.ofTotalSeconds(zoneSecs);
        var zonedDateTime = ZonedDateTime.ofInstant(instant, zoneOffset);
        return zonedDateTime.toLocalTime().toString().split("\\.")[0];
    }
}
