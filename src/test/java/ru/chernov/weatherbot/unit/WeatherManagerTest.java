package ru.chernov.weatherbot.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import ru.chernov.weatherbot.answer.MessageFormatter;
import ru.chernov.weatherbot.dto.ForecastDto;
import ru.chernov.weatherbot.dto.WeatherDto;
import ru.chernov.weatherbot.weather.WeatherManager;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

/**
 * @author Pavel Chernov
 */
@SpringBootTest
@TestPropertySource(locations = {"/test.properties"})
@AutoConfigureMockMvc
public class WeatherManagerTest {

    private final WeatherManager weatherManager;

    @MockBean
    public MessageFormatter messageFormatter;

    @Autowired
    public WeatherManagerTest(WeatherManager weatherManager) {
        this.weatherManager = weatherManager;
    }

    @Test
    void shouldCheckCityExisting() {
        Assertions.assertTrue(weatherManager.checkCityExisting("Санкт-Петербург"));
        Assertions.assertFalse(weatherManager.checkCityExisting("Спб"));
    }

    @Test
    void shouldGetWeather() {
        weatherManager.getForecast("Moscow", 0);
        Mockito.verify(messageFormatter).weatherInfo(any(WeatherDto.class));
    }

    @Test
    void shouldGetForecast() {
        weatherManager.getForecast("Moscow", 1);
        Mockito.verify(messageFormatter).forecastInfo(any(ForecastDto.class));
    }

    @Test
    void shouldCollapseCauseBadArguments() {
        assertThrows(IllegalArgumentException.class, () -> weatherManager.checkCityExisting(null));
        assertThrows(IllegalArgumentException.class, () -> weatherManager.checkCityExisting(""));

        assertThrows(IllegalArgumentException.class, () -> weatherManager.getForecast(null, 1));
        assertThrows(IllegalArgumentException.class, () -> weatherManager.getForecast("", 1));
    }
}
