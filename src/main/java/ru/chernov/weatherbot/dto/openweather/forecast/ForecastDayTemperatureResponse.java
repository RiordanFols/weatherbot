package ru.chernov.weatherbot.dto.openweather.forecast;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ForecastDayTemperatureResponse {

    @JsonProperty("max")
    private Integer maxTemperature;

    @JsonProperty("min")
    private Integer minTemperature;

}
