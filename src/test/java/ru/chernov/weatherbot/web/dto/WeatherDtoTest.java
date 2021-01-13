package ru.chernov.weatherbot.web.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.chernov.weatherbot.dto.WeatherDto;

/**
 * @author Pavel Chernov
 */
@SpringBootTest
@TestPropertySource(locations = {"/test.properties"})
public class WeatherDtoTest {

    private final RestTemplate restTemplate;

    @Value("${openweather.api.key}")
    private String openweatherApiKey;

    @Value("${openweather.api.weather.uri}")
    private String weatherUri;

    @Autowired
    public WeatherDtoTest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Test
    void shouldMakeDto() {
        String[] cities = new String[]{"Санкт-Петербург", "Москва", "Вашингтон", "Берлин", "Пермь",
                "Saint Petersburg", "London", "Sydney", "Rome", "Dublin"};
        for (var city : cities) {
            String uri = String.format(weatherUri, city, openweatherApiKey);
            String url = "https://" + uri;
            WeatherDto dto = restTemplate.getForObject(url, WeatherDto.class);

            Assertions.assertNotNull(dto);
            Assertions.assertEquals(dto.getStatus(), 200);
        }
    }

    @Test
    void shouldNotFindCity() {
        Assertions.assertThrows(HttpClientErrorException.NotFound.class, () -> {
            String city = "PA-ECV<E3391-915913iu5jfj";
            String uri = String.format(weatherUri, city, openweatherApiKey);
            String url = "https://" + uri;
            restTemplate.getForObject(url, WeatherDto.class);
        });
    }
}
