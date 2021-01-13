package ru.chernov.weatherbot.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.chernov.weatherbot.dto.WeatherDto;
import ru.chernov.weatherbot.weather.Country;
import ru.chernov.weatherbot.weather.Weather;
import ru.chernov.weatherbot.weather.WeatherCondition;
import ru.chernov.weatherbot.weather.WindCondition;

/**
 * @author Pavel Chernov
 */
@SpringBootTest
@TestPropertySource(locations = {"/test.properties"})
public class WeatherTest {

    private static final WeatherDto weatherDto = new WeatherDto();

    @BeforeEach
    void createDto() {
        weatherDto.setCondition("Rain");
        weatherDto.setTemp(0);
        weatherDto.setTempFeelsLike(-2);
        weatherDto.setPressure(1013);
        weatherDto.setHumidity(80);
        weatherDto.setCountryName("RU");
        weatherDto.setSunriseTime(1610434199);
        weatherDto.setSunsetTime(1610457845);
        weatherDto.setWindSpeed(2);
        weatherDto.setWindDeg(64);
        weatherDto.setStatus(200);
        weatherDto.setCityName("Санкт-Петербург");
        weatherDto.setTimezone(10800);
    }

    @Test
    void shouldMakeWeatherFromDto() {
        Weather weather = new Weather(weatherDto);

        Assertions.assertEquals("Санкт-Петербург", weather.getCityName());
        Assertions.assertEquals(Country.RU, weather.getCountry());
        Assertions.assertEquals(WeatherCondition.RAIN, weather.getWeatherCondition());
        Assertions.assertEquals("0°C", weather.getTemp());
        Assertions.assertEquals("-2°C", weather.getTempFeelsLike());
        WindCondition windCondition = WindCondition.NE;
        windCondition.setSpeed(2);
        Assertions.assertEquals(windCondition, weather.getWindCondition());
        Assertions.assertEquals("760 мм рт.ст.", weather.getPressure());
        Assertions.assertEquals("80%", weather.getHumidity());
        Assertions.assertEquals("09:49:59", weather.getSunriseTime());
        Assertions.assertEquals("16:24:05", weather.getSunsetTime());
    }

    @Test
    void shouldCollapseCauseBadDto() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            weatherDto.setStatus(404);  // not 200
            new Weather(weatherDto);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Weather(null));
    }
}
