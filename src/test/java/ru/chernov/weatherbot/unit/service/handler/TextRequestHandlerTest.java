package ru.chernov.weatherbot.unit.service.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.chernov.weatherbot.AbstractTest;
import ru.chernov.weatherbot.exception.message.CityNotFoundException;
import ru.chernov.weatherbot.exception.message.UnacceptableSymbolException;
import ru.chernov.weatherbot.service.keyboard.KeyboardGenerator;
import ru.chernov.weatherbot.service.rest.OpenWeatherRestService;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class TextRequestHandlerTest extends AbstractTest {

    @MockBean
    private KeyboardGenerator keyboardGenerator;
    @MockBean
    private OpenWeatherRestService openWeatherRestService;


    @Test
    void shouldProvideButtons() throws JsonProcessingException {
        when(openWeatherRestService.getWeather(anyString())).thenReturn("Stub weather");

        Update update = new Update();
        update.setMessage(composeMessage(TEST_CITY_NAME));

        textRequestHandler.handle(update);
        verify(keyboardGenerator, times(1)).getForecastButtons(anyString());
    }


    @Test
    void shouldNotFoundCity() {
        when(openWeatherRestService.getWeather(anyString())).thenThrow(CityNotFoundException.class);

        Update update = new Update();
        update.setMessage(composeMessage("Абракадабраумцкпцкпцк"));
        assertThrows(CityNotFoundException.class, () -> textRequestHandler.handle(update));

        verify(openWeatherRestService, times(1)).getWeather(anyString());
    }


    @Test
    void shouldFoundUnacceptableSymbols() {
        Update update = new Update();
        update.setMessage(composeMessage("137370#&^#T$FGgfg82fg28fehuhy2"));
        assertThrows(UnacceptableSymbolException.class, () -> textRequestHandler.handle(update));
    }

}
