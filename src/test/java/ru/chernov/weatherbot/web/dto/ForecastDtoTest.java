package ru.chernov.weatherbot.web.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.chernov.weatherbot.dto.ForecastDto;

/**
 * @author Pavel Chernov
 */
@SpringBootTest
@TestPropertySource(locations = {"/test.properties"})
public class ForecastDtoTest {

    private final RestTemplate restTemplate;

    @Value("${openweather.api.key}")
    private String openweatherApiKey;

    @Value("${openweather.api.forecast.uri}")
    private String forecastUri;

    @Autowired
    public ForecastDtoTest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Test
    void shouldMakeDto() {
        String[] cities = new String[]{"Санкт-Петербург", "Москва", "Вашингтон", "Берлин", "Пермь",
                "Saint Petersburg", "London", "Sydney", "Rome", "Dublin"};
        for (var city : cities) {
            String uri = String.format(forecastUri, city, 3, openweatherApiKey);
            String url = "https://" + uri;
            ForecastDto dto = restTemplate.getForObject(url, ForecastDto.class);

            Assertions.assertNotNull(dto);
            Assertions.assertEquals(dto.getStatus(), 200);
        }
    }

    @Test
    void shouldNotFindCityCauseBadCityName() {
        Assertions.assertThrows(HttpClientErrorException.NotFound.class, () -> {
            String cityName = "notRealCityName";
            String uri = String.format(forecastUri, cityName, 3, openweatherApiKey);
            String url = "https://" + uri;
            restTemplate.getForObject(url, ForecastDto.class);
        });
    }

    @Test
    void shouldCollapseCauseWrongDaysNumber() {
        // < 1 is forbidden
        Assertions.assertThrows(HttpClientErrorException.BadRequest.class, () -> {
            String city = "Москва";
            String uri = String.format(forecastUri, city, -5, openweatherApiKey);
            String url = "https://" + uri;
            restTemplate.getForObject(url, ForecastDto.class);
        });

        // > 17 is forbidden
        Assertions.assertThrows(HttpClientErrorException.BadRequest.class, () -> {
            String city = "Москва";
            String uri = String.format(forecastUri, city, 20, openweatherApiKey);
            String url = "https://" + uri;
            restTemplate.getForObject(url, ForecastDto.class);
        });
    }
}
