package ru.chernov.weatherbot.service.handler.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.chernov.weatherbot.enums.BotCommand;
import ru.chernov.weatherbot.exception.message.UnknownCommandException;


@Component
public class CommandRequestHandler implements RequestHandler {

    private static final Logger LOGGER = LogManager.getLogger(CommandRequestHandler.class);


    @Override
    public Boolean isApplicable(Update update) {
        return update.hasMessage() && update.getMessage().getText().startsWith("/");
    }


    @Override
    public SendMessage handle(Update update) {
        Message message = update.getMessage();
        String commandText = message.getText();

        BotCommand botCommand = BotCommand.ALL.get(commandText.toLowerCase());
        if (botCommand == null) {
            LOGGER.error("Unknown command [{}].", commandText);
            throw new UnknownCommandException();
        }

        return getBuilder()
                .text(botCommand.getAnswer())
                .chatId(message.getChatId().toString())
                .build();
    }

}
