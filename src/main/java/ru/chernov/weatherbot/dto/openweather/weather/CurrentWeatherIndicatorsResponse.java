package ru.chernov.weatherbot.dto.openweather.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CurrentWeatherIndicatorsResponse {

    @JsonProperty("temp")
    private Integer temperature; // °C

    @JsonProperty("feels_like")
    private Integer temperatureFeeling; // °C

    private Integer pressure; // гПа

    private Integer humidity;

}
