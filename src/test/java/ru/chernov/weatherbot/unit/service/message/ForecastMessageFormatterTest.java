package ru.chernov.weatherbot.unit.service.message;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.chernov.weatherbot.AbstractTest;
import ru.chernov.weatherbot.dto.openweather.forecast.ForecastCityResponse;
import ru.chernov.weatherbot.dto.openweather.forecast.ForecastResponse;
import ru.chernov.weatherbot.enums.Country;
import ru.chernov.weatherbot.service.time.TimeProvider;
import ru.chernov.weatherbot.utils.WeatherProvider;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


public class ForecastMessageFormatterTest extends AbstractTest {

    private static final String EXPECTED_MESSAGE =
            """
                    *Берлин* \uD83C\uDDE9\uD83C\uDDEA
                    30 июл: \uD83C\uDF27 24°C макс. / 16°C мин.
                    31 июл: \uD83C\uDF29 22°C макс. / 13°C мин.
                    1 авг: \u2600 28°C макс. / 17°C мин.""";

    @MockBean
    private TimeProvider timeProvider;


    @Test
    void shouldFormatMessage() {
        ForecastCityResponse cityResponse = new ForecastCityResponse();
        cityResponse.setCountryCode(Country.DE.name());
        cityResponse.setName(TEST_CITY_NAME);

        when(timeProvider.today()).thenReturn(LocalDate.of(2022, 7, 30));
        ForecastResponse response = WeatherProvider.composeForecast(TEST_CITY_NAME, List.of(
                WeatherProvider.composeDayResponse("Rain", 24, 16),
                WeatherProvider.composeDayResponse("THUNDERSTORM", 22, 13),
                WeatherProvider.composeDayResponse("CLEAR", 28, 17)
        ));
        assertEquals(EXPECTED_MESSAGE, forecastMessageFormatter.formatMessage(response));
    }

}
