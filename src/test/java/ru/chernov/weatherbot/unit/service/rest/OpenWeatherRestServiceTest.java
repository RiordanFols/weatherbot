package ru.chernov.weatherbot.unit.service.rest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;
import ru.chernov.weatherbot.AbstractTest;
import ru.chernov.weatherbot.dto.openweather.forecast.ForecastResponse;
import ru.chernov.weatherbot.dto.openweather.weather.CurrentWeatherResponse;
import ru.chernov.weatherbot.service.message.ForecastMessageFormatter;
import ru.chernov.weatherbot.service.message.WeatherMessageFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class OpenWeatherRestServiceTest extends AbstractTest {

    @MockBean
    private WeatherMessageFormatter weatherMessageFormatter;
    @MockBean
    protected ForecastMessageFormatter forecastMessageFormatter;
    @MockBean
    protected RestTemplate restTemplate;


    @Test
    void shouldProvideWeather() {
        when(restTemplate.getForObject(anyString(), any())).thenReturn(new CurrentWeatherResponse());
        when(weatherMessageFormatter.formatMessage(any(CurrentWeatherResponse.class))).thenReturn("Formatted message stub");

        openWeatherRestService.getWeather(TEST_CITY_NAME);

        verify(restTemplate, times(1)).getForObject(anyString(), any());
        verify(weatherMessageFormatter, times(1)).formatMessage(any(CurrentWeatherResponse.class));
    }


    @Test
    void shouldProvideForecast() {
        when(restTemplate.getForObject(anyString(), any())).thenReturn(new ForecastResponse());
        when(forecastMessageFormatter.formatMessage(any(ForecastResponse.class))).thenReturn("Formatted message stub");

        openWeatherRestService.getForecast(TEST_CITY_NAME, 3);

        verify(restTemplate, times(1)).getForObject(anyString(), any());
        verify(forecastMessageFormatter, times(1)).formatMessage(any(ForecastResponse.class));
    }


    @Test
    void shouldRequestCurrentWeatherForZeroDaysForecast() {
        when(restTemplate.getForObject(anyString(), any())).thenReturn(new CurrentWeatherResponse());
        when(weatherMessageFormatter.formatMessage(any(CurrentWeatherResponse.class))).thenReturn("Formatted message stub");

        openWeatherRestService.getForecast(TEST_CITY_NAME, 0);

        verify(restTemplate, times(1)).getForObject(anyString(), any());
        verify(weatherMessageFormatter, times(1)).formatMessage(any(CurrentWeatherResponse.class));
    }

}
