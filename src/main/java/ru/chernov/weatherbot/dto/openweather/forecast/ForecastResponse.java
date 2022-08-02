package ru.chernov.weatherbot.dto.openweather.forecast;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ForecastResponse {

    private ForecastCityResponse city;

    @JsonProperty("list")
    private List<ForecastDayResponse> dayForecasts;

}
