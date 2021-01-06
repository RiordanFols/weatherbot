package ru.chernov.weatherbot.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.chernov.weatherbot.dto.ForecastDto;
import ru.chernov.weatherbot.dto.WeatherDto;
import ru.chernov.weatherbot.exception.WeatherUnavailableException;

/**
 * @author Pavel Chernov
 */
@Component
public class WeatherReceiver {

    @Value("${openweather.api.key}")
    private String openweatherApiKey;

    @Value("${openweather.api.weather.uri}")
    private String weatherUri;

    @Value("${openweather.api.forecast.uri}")
    private String forecastUri;

    private final RestTemplate restTemplate;
    private final WeatherManager weatherManager;

    @Autowired
    public WeatherReceiver(RestTemplate restTemplate, WeatherManager weatherManager) {
        this.restTemplate = restTemplate;
        this.weatherManager = weatherManager;
    }

    public String getWeather(String cityName) {
        String uri = String.format(weatherUri, cityName, openweatherApiKey);
        String url = "https://" + uri;

        try {
            WeatherDto dto = restTemplate.getForObject(url, WeatherDto.class);
            return weatherManager.weatherInfo(dto);
        } catch (HttpClientErrorException.NotFound e) {
            return "Город не найден";
        } catch (WeatherUnavailableException e) {
            return "К сожалению, сервис погоды недоступен, попробуйте позже";
        }
    }

    public String getForecast(String cityName, int days) {
        String uri = String.format(forecastUri, cityName, days, openweatherApiKey);
        String url = "https://" + uri;

        if (days < 1 || days > 16) {
            throw new IllegalArgumentException("Wrong number of days");
        }

        try {
            ForecastDto dto = restTemplate.getForObject(url, ForecastDto.class);
            return weatherManager.forecastInfo(dto);
        } catch (HttpClientErrorException.NotFound e) {
            return "Город не найден";
        } catch (WeatherUnavailableException e) {
            return "К сожалению, сервис погоды недоступен, попробуйте позже";
        }
    }
}
