package ru.chernov.weatherbot.service.handler.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.chernov.weatherbot.exception.message.CityNotFoundException;
import ru.chernov.weatherbot.exception.message.UnacceptableSymbolException;
import ru.chernov.weatherbot.service.keyboard.KeyboardGenerator;
import ru.chernov.weatherbot.service.rest.OpenWeatherRestService;

import java.util.regex.Pattern;


@Component
public class TextRequestHandler implements RequestHandler {

    private static final Logger LOGGER = LogManager.getLogger(TextRequestHandler.class);
    private static final Pattern CORRECT_SYMBOLS_PATTERN = Pattern.compile("[a-zA-Z]*|[а-яА-Я]*|\s*-*");

    private final KeyboardGenerator keyboardGenerator;
    private final OpenWeatherRestService openWeatherRestService;


    @Autowired
    public TextRequestHandler(KeyboardGenerator keyboardGenerator, OpenWeatherRestService openWeatherRestService) {
        this.keyboardGenerator = keyboardGenerator;
        this.openWeatherRestService = openWeatherRestService;
    }


    @Override
    public Boolean isApplicable(Update update) {
        return update.hasMessage() && !update.getMessage().getText().startsWith("/");
    }


    @Override
    public SendMessage handle(Update update) throws JsonProcessingException {
        Message message = update.getMessage();
        String text = message.getText();

        if (!CORRECT_SYMBOLS_PATTERN.matcher(text).matches()) {
            LOGGER.error("Unacceptable symbols in [{}].", text);
            throw new UnacceptableSymbolException();
        }

        try {
            openWeatherRestService.getWeather(text);
        } catch (HttpClientErrorException.NotFound ignored) {
            LOGGER.error("Cannot find city [{}] in open weather api.", text);
            throw new CityNotFoundException();
        }

        return getBuilder()
                .chatId(message.getChatId().toString())
                .text("Что тебя интересует?")
                .replyMarkup(keyboardGenerator.getForecastButtons(text))
                .build();
    }

}
