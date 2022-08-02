package ru.chernov.weatherbot.dto.openweather.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CurrentWeatherWindResponse {

    private Integer speed; // m/s

    @JsonProperty("deg")
    private Integer degree;

}
