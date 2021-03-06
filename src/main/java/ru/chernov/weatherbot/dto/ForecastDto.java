package ru.chernov.weatherbot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Dto, содержащий информацию о прогнозе погоды
 *
 * @author Pavel Chernov
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastDto {

    // list -> weather
    private List<String> condition = new ArrayList<>(); // "Rain", "Snow", "Clear", etc

    // list -> temp
    private List<Double> dayTemp = new ArrayList<>(); // °C
    private List<Double> nightTemp = new ArrayList<>(); // °C

    // city
    private String cityName;
    private String countryName; // Iso2

    @JsonProperty("cnt")
    private int days; // 1 - 17

    @JsonProperty("cod")
    private int status; // HTTP status code

    @JsonProperty("city")
    private void unpackCity(Map<String, Object> map) {
        this.cityName = map.get("name").toString();
        this.countryName = map.get("country").toString();
    }

    @JsonProperty("list")
    @SuppressWarnings("unchecked")
    private void unpackList(List<Map<String, Object>> list) {
        for (Map<String, Object> map : list) {
            unpackTemp((Map<String, Object>) map.get("temp"));
            unpackWeather((List<Map<String, Object>>) map.get("weather"));
        }
    }

    private void unpackTemp(Map<String, Object> map) {
        this.dayTemp.add(Double.parseDouble(map.get("day").toString()));
        this.nightTemp.add(Double.parseDouble(map.get("night").toString()));
    }

    private void unpackWeather(List<Map<String, Object>> weather) {
        this.condition.add((String) weather.get(0).get("main"));
    }

}
