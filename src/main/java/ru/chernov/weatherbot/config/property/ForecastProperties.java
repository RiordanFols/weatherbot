package ru.chernov.weatherbot.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


@Getter
@Setter
@ConfigurationProperties(prefix = "forecast")
public class ForecastProperties {

    private List<Button> buttons;

    @Getter
    @Setter
    public static class Button {

        private String text;
        private Integer days;

    }

}
