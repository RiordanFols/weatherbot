package ru.chernov.weatherbot.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.chernov.weatherbot.bot.MessageManager;
import ru.chernov.weatherbot.dto.ForecastDto;
import ru.chernov.weatherbot.dto.WeatherDto;

/**
 * @author Pavel Chernov
 */
@Component
public class WeatherManager {

    @Value("${openweather.api.key}")
    private String openweatherApiKey;

    @Value("${openweather.api.weather.uri}")
    private String weatherUri;

    @Value("${openweather.api.forecast.uri}")
    private String forecastUri;

    private final RestTemplate restTemplate;
    private final MessageManager messageManager;

    @Autowired
    public WeatherManager(RestTemplate restTemplate, MessageManager messageManager) {
        this.restTemplate = restTemplate;
        this.messageManager = messageManager;
    }

    public boolean checkCityExisting(String cityName) {
        String uri = String.format(weatherUri, cityName, openweatherApiKey);
        String url = "https://" + uri;

        try {
            return restTemplate.getForObject(url, WeatherDto.class) != null;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }

    public String getWeather(String cityName) {
        String uri = String.format(weatherUri, cityName, openweatherApiKey);
        String url = "https://" + uri;

        WeatherDto dto = restTemplate.getForObject(url, WeatherDto.class);
        return messageManager.weatherInfo(dto);
    }

    public String getForecast(String cityName, int days) {
        if (days < 0 || days > 16) {
            throw new IllegalArgumentException("Wrong number of days");
        }

        if (days == 0)
            return getWeather(cityName);

        String uri = String.format(forecastUri, cityName, days, openweatherApiKey);
        String url = "https://" + uri;

        ForecastDto dto = restTemplate.getForObject(url, ForecastDto.class);
        return messageManager.forecastInfo(dto);
    }
}
