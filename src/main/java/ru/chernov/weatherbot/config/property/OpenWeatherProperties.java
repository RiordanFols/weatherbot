package ru.chernov.weatherbot.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@Setter
@ConfigurationProperties(prefix = "open.weather")
public class OpenWeatherProperties {

    private String weatherUrl;
    private String forecastUrl;
    private String key;

}
