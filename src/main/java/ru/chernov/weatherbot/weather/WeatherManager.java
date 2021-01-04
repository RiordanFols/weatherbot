package ru.chernov.weatherbot.weather;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.chernov.weatherbot.dto.OpenWeatherDto;
import ru.chernov.weatherbot.exception.WeatherUnavailableException;

/**
 * @author Pavel Chernov
 */
@Component
public class WeatherManager {

    public String getWeather(OpenWeatherDto dto) throws WeatherUnavailableException {
        if (dto == null || dto.getStatus() != 200)
            throw new WeatherUnavailableException("Can't get weather");

        return getCondition(dto) + ", " + getCelsiusTemp(dto);
    }

    private String getCelsiusTemp(OpenWeatherDto dto) {
        String temp = String.format("%.0f", TempConverter.kelvinToCelsius(dto.getTemp()));

        return temp + "°C";
    }

    private String getFahrenheitTemp(OpenWeatherDto dto) {
        String temp = String.format("%.0f", TempConverter.kelvinToFahrenheit(dto.getTemp()));

        return temp + "°F";
    }


    private String getCondition(OpenWeatherDto dto) throws WeatherUnavailableException {
        if (!StringUtils.hasLength(dto.getCondition()))
            throw new WeatherUnavailableException("Can't get weather condition");

        return dto.getCondition();
    }

    public static class TempConverter {

        private static final double CCV = -273.15; // Celsius Conversion Value;

        public static double kelvinToCelsius(double kelvinTemp) {
            return kelvinTemp + CCV;
        }

        public static double kelvinToFahrenheit(double kelvinTemp) {
            return (kelvinTemp + CCV) * 1.8 + 32;
        }

    }
}
