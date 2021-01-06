package ru.chernov.weatherbot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Pavel Chernov
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastDto {

    // list -> weather
    private List<String> condition = new ArrayList<>();

    // list -> temp
    private List<Double> dayTemp = new ArrayList<>();
    private List<Double> nightTemp = new ArrayList<>();

    // city
    private String cityName;

    @JsonProperty("cnt")
    private int days;

    @JsonProperty("city")
    private void unpackCity(Map<String, Object> map) {
        this.cityName = (String) map.get("name");
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
