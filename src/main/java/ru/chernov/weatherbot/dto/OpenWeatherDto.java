package ru.chernov.weatherbot.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * @author Pavel Chernov
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherDto {

    private String condition;
    private double temp = 0;

    @JsonAlias("cod")
    private int status;


    @JsonProperty("main")
    private void unpackMain(Map<String, Object> main) {
        this.temp = (Double) main.get("temp");
    }

    @JsonProperty("weather")
    private void unpackWeather(List<Map<String, Object>> weather) {
        this.condition = (String) weather.get(0).get("main");
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
