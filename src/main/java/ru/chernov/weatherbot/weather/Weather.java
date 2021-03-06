package ru.chernov.weatherbot.weather;

import lombok.Getter;
import ru.chernov.weatherbot.dto.WeatherDto;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Класс погоды, трансформируемый из WeatherDto
 *
 * @author Pavel Chernov
 */
@Getter
public final class Weather {

    private final String cityName;
    private final Country country;

    private final WeatherCondition weatherCondition;

    private final String temp;
    private final String tempFeelsLike;

    private final WindCondition windCondition;

    private final String pressure;
    private final String humidity;

    private final String sunriseTime;
    private final String sunsetTime;

    public Weather(WeatherDto dto) {
        if (dto == null || dto.getStatus() != 200)
            throw new IllegalArgumentException("Bad dto");

        this.country = Country.getByName(dto.getCountryName());
        this.cityName = dto.getCityName();

        this.weatherCondition = WeatherCondition.valueOf(dto.getCondition().toUpperCase());
        this.windCondition = WindCondition.getWind(dto.getWindDeg(), dto.getWindSpeed());

        this.temp = String.format("%.0f°C", dto.getTemp());
        this.tempFeelsLike = String.format("%.0f°C", dto.getTempFeelsLike());

        this.pressure = String.format("%.0f мм рт.ст.", dto.getPressure() * 3 / 4.0);
        this.humidity = dto.getHumidity() + "%";

        this.sunriseTime = getZonedTime(dto.getSunriseTime(), dto.getTimezone());
        this.sunsetTime = getZonedTime(dto.getSunsetTime(), dto.getTimezone());
    }

    /**
     * Получение зонового времени
     *
     * @param secs     секунды с начала эры
     * @param zoneSecs зоновое время в секундах по UTC
     * @return время в формате HH:mm:SS
     */
    private String getZonedTime(long secs, int zoneSecs) {
        var instant = Instant.ofEpochSecond(secs);
        var zoneOffset = ZoneOffset.ofTotalSeconds(zoneSecs);
        var zonedDateTime = ZonedDateTime.ofInstant(instant, zoneOffset);
        return zonedDateTime.toLocalTime().toString();
    }
}
