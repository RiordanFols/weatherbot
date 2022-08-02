package ru.chernov.weatherbot.service.handler.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.chernov.weatherbot.dto.internal.ForecastButtonDto;
import ru.chernov.weatherbot.service.rest.OpenWeatherRestService;


@Component
public class CallbackRequestHandler implements RequestHandler {

    private final OpenWeatherRestService openWeatherRestService;
    private final ObjectMapper objectMapper;


    @Autowired
    public CallbackRequestHandler(OpenWeatherRestService openWeatherRestService, ObjectMapper objectMapper) {
        this.openWeatherRestService = openWeatherRestService;
        this.objectMapper = objectMapper;
    }


    @Override
    public Boolean isApplicable(Update update) {
        return update.hasCallbackQuery();
    }


    @Override
    public SendMessage handle(Update update) throws JsonProcessingException {
        CallbackQuery callbackQuery = update.getCallbackQuery();

        ForecastButtonDto forecastButtonDto = objectMapper.readValue(callbackQuery.getData(), ForecastButtonDto.class);
        String forecast = openWeatherRestService.getForecast(forecastButtonDto.getCity(), forecastButtonDto.getDays());

        // получение прогноза
        return getBuilder()
                .text(forecast)
                .chatId(callbackQuery.getMessage().getChatId().toString())
                .build();
    }

}
