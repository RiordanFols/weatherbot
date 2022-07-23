package ru.chernov.weatherbot.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.chernov.weatherbot.AbstractTest;
import ru.chernov.weatherbot.dto.openweather.forecast.ForecastResponse;
import ru.chernov.weatherbot.dto.openweather.weather.CurrentWeatherResponse;
import ru.chernov.weatherbot.enums.BotCommand;
import ru.chernov.weatherbot.enums.Country;
import ru.chernov.weatherbot.service.time.TimeProvider;
import ru.chernov.weatherbot.utils.WeatherProvider;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


public class BotTest extends AbstractTest {

    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private TimeProvider timeProvider;


    @Test
    void shouldProvideButtons() throws JsonProcessingException {
        LocalDate today = LocalDate.now();
        CurrentWeatherResponse currentWeather = WeatherProvider.composeCurrentWeather("Snow", 71, 1020, -5, -7, Country.DE,
                today.atTime(9, 50), today.atTime(18, 35), 56, 3, TEST_CITY_NAME);
        when(restTemplate.getForObject(anyString(), any())).thenReturn(currentWeather);

        Update update = new Update();
        update.setMessage(composeMessage(TEST_CITY_NAME));
        SendMessage sendMessage = requestHandlerService.handle(update);
        assertNotNull(sendMessage.getText());
        assertNotNull(sendMessage.getReplyMarkup());
        assertEquals(TEST_FAKE_CHAT.getId().toString(), sendMessage.getChatId());

    }


    @Test
    void shouldProvideWeather() throws JsonProcessingException {
        LocalDate today = LocalDate.now();
        CurrentWeatherResponse currentWeather = WeatherProvider.composeCurrentWeather("Snow", 71, 1014, -5, -7,
                Country.DE, today.atTime(9, 50), today.atTime(18, 35), 56, 3, TEST_CITY_NAME);
        when(restTemplate.getForObject(anyString(), any())).thenReturn(currentWeather);
        when(timeProvider.today()).thenReturn(LocalDate.of(2022, 11, 30));
        String expectedMessage = """
                *Берлин* \uD83C\uDDE9\uD83C\uDDEA
                Снег \uD83C\uDF28
                Температура: -5°C
                Ощущается как: -7°C
                Ветер: ↙ 3 м/c
                Давление: 760 мм рт.ст.
                Влажность: 71%
                Рассвет: 09:50
                Закат: 18:35
                """;
        assertCorrectResult(0, expectedMessage);
    }


    @Test
    void shouldProvideForecast() throws JsonProcessingException {
        ForecastResponse response = WeatherProvider.composeForecast(TEST_CITY_NAME, List.of(
                WeatherProvider.composeDayResponse("Clear", -11, -19),
                WeatherProvider.composeDayResponse("Snow", -9, -14),
                WeatherProvider.composeDayResponse("Clear", -12, -20)
        ));
        when(restTemplate.getForObject(anyString(), any())).thenReturn(response);
        when(timeProvider.today()).thenReturn(LocalDate.of(2022, 12, 30));
        String expectedMessage = """
                *Берлин* \uD83C\uDDE9\uD83C\uDDEA
                30 дек: \u2600 -11°C макс. / -19°C мин.
                31 дек: \uD83C\uDF28 -9°C макс. / -14°C мин.
                1 янв: \u2600 -12°C макс. / -20°C мин.""";
        assertCorrectResult(7, expectedMessage);
    }


    @Test
    void shouldProcessCommand() throws JsonProcessingException {
        Update update = new Update();
        update.setMessage(composeMessage("/help"));
        SendMessage sendMessage = requestHandlerService.handle(update);
        assertEquals(BotCommand.HELP.getAnswer(), sendMessage.getText());
    }


    private void assertCorrectResult(Integer days, String expectedMessage) throws JsonProcessingException {
        CallbackQuery callbackQuery = new CallbackQuery();
        callbackQuery.setData(composeCallbackData(days));
        callbackQuery.setMessage(composeMessage("Weather"));

        Update update = new Update();
        update.setCallbackQuery(callbackQuery);
        SendMessage sendMessage = requestHandlerService.handle(update);

        String text = sendMessage.getText();
        assertEquals(expectedMessage, text);
        assertTrue(text.contains(TEST_CITY_NAME));
        assertNull(sendMessage.getReplyMarkup());
    }

}
