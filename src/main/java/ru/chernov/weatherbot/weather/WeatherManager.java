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
        return forecast.getCondition().getRu() + " " + forecast.getCondition().getEmoji() + "\n" +
                "Температура: " + forecast.getTemp() + "\n" +
                "Ощущается как: " + forecast.getTempFeelsLike() + "\n" +
                "Давление: " + forecast.getPressure() + "\n" +
                "Влажность: " + forecast.getHumidity() + "\n" +
                "Рассвет: " + forecast.getSunriseTime() + "\n" +
                "Закат: " + forecast.getSunsetTime();
    }


}
