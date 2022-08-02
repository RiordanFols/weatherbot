package ru.chernov.weatherbot.service.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.chernov.weatherbot.config.property.OpenWeatherProperties;
import ru.chernov.weatherbot.dto.openweather.forecast.ForecastResponse;
import ru.chernov.weatherbot.dto.openweather.weather.CurrentWeatherResponse;
import ru.chernov.weatherbot.exception.message.OpenWeatherApiException;
import ru.chernov.weatherbot.service.message.ForecastMessageFormatter;
import ru.chernov.weatherbot.service.message.WeatherMessageFormatter;


@Service
public class OpenWeatherRestService {

    private static final Logger LOGGER = LogManager.getLogger(OpenWeatherRestService.class);

    private final RestTemplate restTemplate;
    private final OpenWeatherProperties openWeatherProperties;
    private final WeatherMessageFormatter weatherMessageFormatter;
    private final ForecastMessageFormatter forecastMessageFormatter;


    @Autowired
    public OpenWeatherRestService(RestTemplate restTemplate,
                                  OpenWeatherProperties openWeatherProperties,
                                  WeatherMessageFormatter weatherMessageFormatter,
                                  ForecastMessageFormatter forecastMessageFormatter) {
        this.restTemplate = restTemplate;
        this.openWeatherProperties = openWeatherProperties;
        this.weatherMessageFormatter = weatherMessageFormatter;
        this.forecastMessageFormatter = forecastMessageFormatter;
    }


    public String getWeather(String cityName) {
        String url = String.format(openWeatherProperties.getWeatherUrl(), cityName, openWeatherProperties.getKey());
        CurrentWeatherResponse currentWeatherResponse = getForObject(url, CurrentWeatherResponse.class);
        return weatherMessageFormatter.formatMessage(currentWeatherResponse);
    }


    public String getForecast(String cityName, Integer days) {
        if (days == 0) {
            return getWeather(cityName);
        }

        String url = String.format(openWeatherProperties.getForecastUrl(), cityName, days, openWeatherProperties.getKey());
        ForecastResponse forecastResponse = getForObject(url, ForecastResponse.class);
        return forecastMessageFormatter.formatMessage(forecastResponse);
    }


    private <T> T getForObject(String url, Class<T> clazz) {
        T response = restTemplate.getForObject(url, clazz);
        if (response == null) {
            LOGGER.error("Empty result from [{}].", url);
            throw new OpenWeatherApiException();
        }
        return response;
    }

}
