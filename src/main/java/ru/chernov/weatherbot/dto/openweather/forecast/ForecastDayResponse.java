package ru.chernov.weatherbot.dto.openweather.forecast;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ru.chernov.weatherbot.dto.openweather.WeatherConditionResponse;

import java.util.List;


@Getter
@Setter
public class ForecastDayResponse {

    @JsonProperty("temp")
    private ForecastDayTemperatureResponse temperature;

    @JsonProperty("weather")
    private List<WeatherConditionResponse> conditions;
    
}
