package ru.chernov.weatherbot.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Pavel Chernov
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherDto {
    @JsonAlias("cod")
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
