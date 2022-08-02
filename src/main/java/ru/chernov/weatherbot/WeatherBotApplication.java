package ru.chernov.weatherbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.chernov.weatherbot.config.property.ForecastProperties;
import ru.chernov.weatherbot.config.property.OpenWeatherProperties;
import ru.chernov.weatherbot.config.property.TelegramBotProperties;


@EnableConfigurationProperties({
        TelegramBotProperties.class,
        OpenWeatherProperties.class,
        ForecastProperties.class
})
@SpringBootApplication
public class WeatherBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherBotApplication.class, args);
    }

}
