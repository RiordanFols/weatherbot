package ru.chernov.weatherbot.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.chernov.weatherbot.dto.OpenWeatherDto;
import ru.chernov.weatherbot.exception.WeatherUnavailableException;

/**
 * @author Pavel Chernov
 */
@Component
public class WeatherReceiver {

    @Value("${openweather.api.key}")
    private String openweatherApiKey;

    @Value("${openweather.api.uri}")
    private String openweatherUri;

    private final RestTemplate restTemplate;
    private final WeatherManager weatherManager;

    @Autowired
    public WeatherReceiver(RestTemplate restTemplate, WeatherManager weatherManager) {
        this.restTemplate = restTemplate;
        this.weatherManager = weatherManager;
    }

    public String executeRequest(String cityName) {
        String uri = String.format(openweatherUri, cityName, openweatherApiKey);
        String url = "https://" + uri;

        try {
            OpenWeatherDto dto = restTemplate.getForObject(url, OpenWeatherDto.class);
            return weatherManager.getWeather(dto);
        } catch (HttpClientErrorException.NotFound e) {
            return "City not found";
        } catch (WeatherUnavailableException e) {
            return "Weather service is unavailable";
        }

    }
}
