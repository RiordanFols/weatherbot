package ru.chernov.weatherbot.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author Pavel Chernov
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public final class OpenWeatherDto {

    // weather
    private String condition;

    // main
    private double temp;
    private double tempFeelsLike;
    private long pressure;
    private int humidity;

    // sys
    private int sunriseTime;
    private int sunsetTime;

    @JsonAlias("cod")
    private int status;

    @JsonProperty
    private int timezone;

    @JsonProperty("weather")
    private void unpackWeather(List<Map<String, Object>> weather) {
        this.condition = (String) weather.get(0).get("main");
    }

    @JsonProperty("main")
    private void unpackMain(Map<String, Object> main) {
        this.temp = Double.parseDouble(main.get("temp").toString());
        this.tempFeelsLike = Double.parseDouble(main.get("feels_like").toString());
        this.pressure = (Integer) main.get("pressure");
        this.humidity = (Integer) main.get("humidity");
    }

    @JsonProperty("sys")
    private void unpackSys(Map<String, Object> sys) {
        this.sunriseTime = (Integer) sys.get("sunrise");
        this.sunsetTime = (Integer) sys.get("sunset");
    }


}
