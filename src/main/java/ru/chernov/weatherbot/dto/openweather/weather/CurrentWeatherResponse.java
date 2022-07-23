package ru.chernov.weatherbot.dto.openweather.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ru.chernov.weatherbot.dto.openweather.WeatherConditionResponse;

import java.util.List;


@Getter
@Setter
public final class CurrentWeatherResponse {

    @JsonProperty("weather")
    private List<WeatherConditionResponse> conditions;

    @JsonProperty("main")
    private CurrentWeatherIndicatorsResponse indicators;

    @JsonProperty("sys")
    private CurrentWeatherSystemResponse system;

    private CurrentWeatherWindResponse wind;

    @JsonProperty("name")
    private String cityName;

    @JsonProperty("timezone")
    private Integer timezoneSeconds;

}
