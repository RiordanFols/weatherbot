package ru.chernov.weatherbot.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.chernov.weatherbot.answer.MessageFormatter;
import ru.chernov.weatherbot.dto.ForecastDto;
import ru.chernov.weatherbot.dto.WeatherDto;

import java.util.Arrays;

/**
 * @author Pavel Chernov
 */
@SpringBootTest
@TestPropertySource(locations = {"/test.properties"})
public class MessageFormatterTest {

    private final MessageFormatter messageFormatter;
    private static final WeatherDto weatherDto = new WeatherDto();
    private static final ForecastDto forecastDto = new ForecastDto();

    @BeforeEach
    void createWeatherDto() {
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

    @BeforeEach
    void createForecastDto() {
        forecastDto.setCondition(Arrays.asList("Rain", "Snow", "Clear"));
        forecastDto.setDayTemp(Arrays.asList(2.0, -2.0, 6.0));
        forecastDto.setNightTemp(Arrays.asList(-3.0, -1.0, 2.0));
        forecastDto.setCityName("Санкт-Петербург");
        forecastDto.setCountryName("RU");
        forecastDto.setDays(3);
        forecastDto.setStatus(200);
    }

    @Autowired
    public MessageFormatterTest(MessageFormatter messageFormatter) {
        this.messageFormatter = messageFormatter;
    }

    @Test
    void shouldGetWeatherInfo() {
        String answer = messageFormatter.weatherInfo(weatherDto);
        Assertions.assertEquals("*Санкт-Петербург* \uD83C\uDDF7\uD83C\uDDFA\n" +
                "Дождь \uD83C\uDF27\n" +
                "Температура: 0°C\n" +
                "Ощущается как: -2°C\n" +
                "Ветер: ↙ 2 м/c\n" +
                "Давление: 760 мм рт.ст.\n" +
                "Влажность: 80%\n" +
                "Рассвет: 09:49:59\n" +
                "Закат: 16:24:05", answer);

    }

    @Test
    void shouldGetForecastInfo() {
        String answer = messageFormatter.forecastInfo(forecastDto);
        System.out.println(answer);
        Assertions.assertEquals("*Санкт-Петербург* \uD83C\uDDF7\uD83C\uDDFA", answer.split("\n")[0]);
        Assertions.assertEquals(" Дождь\uD83C\uDF27 2°C / -3°C", answer.split("\n")[1].split(":")[1]);
        Assertions.assertEquals(" Снег\uD83C\uDF28 -2°C / -1°C", answer.split("\n")[2].split(":")[1]);
        Assertions.assertEquals(" Ясно☀ 6°C / 2°C", answer.split("\n")[3].split(":")[1]);
    }
}
