package ru.chernov.weatherbot;

import org.springframework.stereotype.Component;
import ru.chernov.weatherbot.dto.OpenWeatherDto;

/**
 * @author Pavel Chernov
 */
@Component
public class WeatherManager {

    public String getStatus(OpenWeatherDto dto) {
        return String.valueOf(dto.getStatus());
    }
}
