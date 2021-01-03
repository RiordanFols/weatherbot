package ru.chernov.weatherbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.chernov.weatherbot.dto.OpenWeatherDto;

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

        OpenWeatherDto dto;

        try {
            dto = restTemplate.getForObject(url, OpenWeatherDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            return "City not found";
        //todo
        } catch (Exception e) {
            return "Unknown error";
        }

        return weatherManager.getStatus(dto);
    }
}
