package ru.chernov.weatherbot.unit.service.handler;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.chernov.weatherbot.AbstractTest;
import ru.chernov.weatherbot.service.handler.impl.CallbackRequestHandler;
import ru.chernov.weatherbot.service.handler.impl.CommandRequestHandler;
import ru.chernov.weatherbot.service.handler.impl.TextRequestHandler;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class RequestHandlerServiceTest extends AbstractTest {

    @MockBean
    private TextRequestHandler textRequestHandler;
    @MockBean
    private CallbackRequestHandler callbackRequestHandler;
    @MockBean
    private CommandRequestHandler commandRequestHandler;


    @Test
    void shouldNotFoundAppropriateHandler() {
        when(textRequestHandler.isApplicable(any(Update.class))).thenReturn(false);
        when(callbackRequestHandler.isApplicable(any(Update.class))).thenReturn(false);
        when(commandRequestHandler.isApplicable(any(Update.class))).thenReturn(false);
        Update update = new Update();
        assertThrows(UnsupportedOperationException.class, () -> requestHandlerService.handle(update));
    }

}
