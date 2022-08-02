package ru.chernov.weatherbot.service.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.chernov.weatherbot.service.handler.impl.RequestHandler;

import java.util.Set;


@Component
public class RequestHandlerService {

    private static final Logger LOGGER = LogManager.getLogger(RequestHandlerService.class);
    private final Set<RequestHandler> requestHandlers;


    @Autowired
    public RequestHandlerService(Set<RequestHandler> requestHandlers) {
        this.requestHandlers = requestHandlers;
    }


    public SendMessage handle(Update update) throws JsonProcessingException {
        for (RequestHandler handler : requestHandlers) {
            if (handler.isApplicable(update)) {
                return handler.handle(update);
            }
        }
        LOGGER.error("Cannot find appropriate handler.");
        throw new UnsupportedOperationException();
    }

}
