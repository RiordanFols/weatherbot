package ru.chernov.weatherbot.weather;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.chernov.weatherbot.bot.MessageFormatter;
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
    private final MessageFormatter messageFormatter;

    @Autowired
    public WeatherManager(RestTemplate restTemplate, MessageFormatter messageFormatter) {
        this.restTemplate = restTemplate;
        this.messageFormatter = messageFormatter;
    }

    public boolean checkCityExisting(String cityName) {
        if (StringUtils.isEmpty(cityName))
            throw new IllegalArgumentException("Empty city name");

        String uri = String.format(weatherUri, cityName, openweatherApiKey);
        String url = "https://" + uri;

        try {
            return restTemplate.getForObject(url, WeatherDto.class) != null;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }

    private String getWeather(String cityName) {
        String uri = String.format(weatherUri, cityName, openweatherApiKey);
        String url = "https://" + uri;

        WeatherDto dto = restTemplate.getForObject(url, WeatherDto.class);

        return messageFormatter.weatherInfo(dto);
    }

    public String getForecast(String cityName, int days) {
        if (StringUtils.isEmpty(cityName))
            throw new IllegalArgumentException("Empty city name");

        if (days < 0 || days > 16) {
            throw new IllegalArgumentException("Wrong number of days");
        }

        if (days == 0)
            return getWeather(cityName);

        String uri = String.format(forecastUri, cityName, days, openweatherApiKey);
        String url = "https://" + uri;

        ForecastDto dto = restTemplate.getForObject(url, ForecastDto.class);

        return messageFormatter.forecastInfo(dto);
    }
}
