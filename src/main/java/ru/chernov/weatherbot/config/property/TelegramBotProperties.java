package ru.chernov.weatherbot.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@Setter
@ConfigurationProperties(prefix = "telegram.weather.bot")
public class TelegramBotProperties {

    private String token;
    private String username;

}
