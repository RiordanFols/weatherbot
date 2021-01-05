package ru.chernov.weatherbot.weather;

import org.springframework.stereotype.Component;
import ru.chernov.weatherbot.dto.OpenWeatherDto;
import ru.chernov.weatherbot.exception.WeatherUnavailableException;

/**
 * @author Pavel Chernov
 */
@Component
public class WeatherManager {

    public String generalInfo(OpenWeatherDto dto) {
        if (dto == null || dto.getStatus() != 200)
            throw new WeatherUnavailableException("Can't get weather");

        var forecast = new WeatherForecast(dto);
        return forecast.getCondition() + "  " + forecast.getTemp() + "\n" +
                "Feels like: " + forecast.getTempFeelsLike() + "\n" +
                "Pressure: " + forecast.getPressure() + "\n" +
                "Humidity: " + forecast.getHumidity() + "\n" +
                "Sunrise: " + forecast.getSunriseTime() + "\n" +
                "Sunset: " + forecast.getSunsetTime();
    }


}
