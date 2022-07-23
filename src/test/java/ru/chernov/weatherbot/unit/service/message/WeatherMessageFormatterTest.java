package ru.chernov.weatherbot.unit.service.message;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.chernov.weatherbot.AbstractTest;
import ru.chernov.weatherbot.dto.internal.WindDto;
import ru.chernov.weatherbot.dto.openweather.weather.CurrentWeatherResponse;
import ru.chernov.weatherbot.enums.Country;
import ru.chernov.weatherbot.enums.WindDirection;
import ru.chernov.weatherbot.service.time.TimeProvider;
import ru.chernov.weatherbot.service.wind.WindProvider;
import ru.chernov.weatherbot.utils.WeatherProvider;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


public class WeatherMessageFormatterTest extends AbstractTest {

    private static final String EXPECTED_MESSAGE = """
            *Берлин* \uD83C\uDDE9\uD83C\uDDEA
            Дождь \uD83C\uDF27
            Температура: 22°C
            Ощущается как: 20°C
            Ветер: ⬇ 4 м/c
            Давление: 760 мм рт.ст.
            Влажность: 82%
            Рассвет: 04:30
            Закат: 21:10
            """;

    @MockBean
    private WindProvider windProvider;
    @Mock
    private TimeProvider timeProvider;


    @Test
    void shouldFormatMessage() {
        WindDto wind = new WindDto();
        wind.setDirection(WindDirection.NORTH);
        wind.setSpeed(4);
        when(windProvider.getWind(anyInt(), anyInt())).thenReturn(wind);
        when(timeProvider.today()).thenReturn(LocalDate.of(2020, 5, 25));

        LocalDate today = timeProvider.today();
        CurrentWeatherResponse currentWeather = WeatherProvider.composeCurrentWeather("Rain", 82, 1014, 22, 20,
                Country.DE, today.atTime(4, 30), today.atTime(21, 10), 20, 4, TEST_CITY_NAME);
        String message = weatherMessageFormatter.formatMessage(currentWeather);
        assertEquals(EXPECTED_MESSAGE, message);
    }

}
