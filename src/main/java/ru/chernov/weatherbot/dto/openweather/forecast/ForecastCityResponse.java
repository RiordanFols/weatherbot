package ru.chernov.weatherbot.dto.openweather.forecast;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ForecastCityResponse {

    private String name;

    @JsonProperty("country")
    private String countryCode;

}
