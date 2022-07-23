package ru.chernov.weatherbot.unit.service.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.chernov.weatherbot.AbstractTest;
import ru.chernov.weatherbot.service.rest.OpenWeatherRestService;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class CallbackRequestHandlerTest extends AbstractTest {

    @MockBean
    private OpenWeatherRestService openWeatherRestService;


    @Test
    void shouldRequestForecast() throws JsonProcessingException {
        when(openWeatherRestService.getForecast(anyString(), anyInt())).thenReturn("Stub answer");

        CallbackQuery callbackQuery = new CallbackQuery();
        callbackQuery.setData(composeCallbackData(3));
        callbackQuery.setMessage(composeMessage("Weather"));

        Update update = new Update();
        update.setCallbackQuery(callbackQuery);

        callbackRequestHandler.handle(update);
        verify(openWeatherRestService, times(1)).getForecast(anyString(), anyInt());
    }

}
