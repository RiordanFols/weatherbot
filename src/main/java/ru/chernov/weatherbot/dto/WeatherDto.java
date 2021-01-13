package ru.chernov.weatherbot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * Dto, содержащий информацию о текущей погоде
 *
 * @author Pavel Chernov
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public final class WeatherDto {

    // weather
    private String condition; // "Rain", "Snow", "Clear", etc

    // main
    private double temp; // °C
    private double tempFeelsLike; // °C
    private long pressure; // гПа
    private int humidity; // 0-100

    // sys
    private String countryName; // Iso2
    private int sunriseTime; // e.g. 08:12:20
    private int sunsetTime; // same

    // wind
    private int windSpeed; // m/s
    private double windDeg; // 0-360

    @JsonProperty("cod")
    private int status; // HTTP status code

    @JsonProperty("name")
    private String cityName;

    @JsonProperty
    private int timezone; // in seconds

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
        this.countryName = sys.get("country").toString();
    }

    @JsonProperty("wind")
    private void unpackWind(Map<String, Object> wind) {
        this.windSpeed = (int) Double.parseDouble(wind.get("speed").toString());
        this.windDeg = Double.parseDouble(wind.get("deg").toString());
    }
}
