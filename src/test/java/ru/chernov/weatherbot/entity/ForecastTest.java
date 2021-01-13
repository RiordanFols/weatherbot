package ru.chernov.weatherbot.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.chernov.weatherbot.dto.ForecastDto;
import ru.chernov.weatherbot.weather.Country;
import ru.chernov.weatherbot.weather.Forecast;
import ru.chernov.weatherbot.weather.Forecast.DayForecast;
import ru.chernov.weatherbot.weather.WeatherCondition;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * @author Pavel Chernov
 */
@SpringBootTest
@TestPropertySource(locations = {"/test.properties"})
public class ForecastTest {

    private static final ForecastDto forecastDto = new ForecastDto();

    @BeforeEach
    void createDto() {
        forecastDto.setCondition(Arrays.asList("Rain", "Snow", "Clear"));
        forecastDto.setDayTemp(Arrays.asList(2.0, -2.0, 6.0));
        forecastDto.setNightTemp(Arrays.asList(-3.0, -1.0, 2.0));
        forecastDto.setCityName("Санкт-Петербург");
        forecastDto.setCountryName("RU");
        forecastDto.setDays(3);
        forecastDto.setStatus(200);
    }

    @Test
    void shouldMakeForecastFromDto() {
        Forecast forecast = new Forecast(forecastDto);

        Assertions.assertEquals("Санкт-Петербург", forecast.getCityName());
        Assertions.assertEquals(Country.RU, forecast.getCountry());
        Assertions.assertEquals(3, forecast.getDays());

        DayForecast forecast1 = new DayForecast(WeatherCondition.RAIN, "2°C", "-3°C", LocalDate.now());
        DayForecast forecast2 = new DayForecast(WeatherCondition.SNOW, "-2°C", "-1°C", LocalDate.now().plusDays(1));
        DayForecast forecast3 = new DayForecast(WeatherCondition.CLEAR, "6°C", "2°C", LocalDate.now().plusDays(2));

        Assertions.assertEquals(forecast1, forecast.getForecasts().get(0));
        Assertions.assertEquals(forecast2, forecast.getForecasts().get(1));
        Assertions.assertEquals(forecast3, forecast.getForecasts().get(2));
    }

    @Test
    void shouldCollapseCauseBadDto() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            forecastDto.setStatus(404);  // not 200
            new Forecast(forecastDto);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Forecast(null));
    }
}
