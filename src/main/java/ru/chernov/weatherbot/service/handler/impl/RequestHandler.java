package ru.chernov.weatherbot.service.handler.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


public interface RequestHandler {

    Boolean isApplicable(Update update);

    SendMessage handle(Update update) throws JsonProcessingException;


    default SendMessage.SendMessageBuilder getBuilder() {
        return SendMessage.builder().parseMode("markdown");
    }

}
