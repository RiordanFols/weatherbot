package ru.chernov.weatherbot.service.bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.chernov.weatherbot.config.property.TelegramBotProperties;
import ru.chernov.weatherbot.exception.message.AbstractMessageException;
import ru.chernov.weatherbot.exception.message.WeatherBotException;
import ru.chernov.weatherbot.service.handler.RequestHandlerService;

import javax.annotation.PostConstruct;


@Component
public class WeatherBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = LogManager.getLogger(WeatherBot.class);

    private final TelegramBotProperties telegramBotProperties;
    private final RequestHandlerService requestHandlerService;


    @Autowired
    public WeatherBot(TelegramBotProperties telegramBotProperties, RequestHandlerService requestHandlerService) {
        this.telegramBotProperties = telegramBotProperties;
        this.requestHandlerService = requestHandlerService;
    }


    @Override
    public void onUpdateReceived(Update update) {
        try {
            execute(requestHandlerService.handle(update));
        } catch (AbstractMessageException messageException) {
            SendMessage errorSendMessage = new SendMessage();
            errorSendMessage.setText(messageException.getErrorMessage());
            errorSendMessage.setChatId(update.getMessage().getChatId().toString());
            try {
                execute(errorSendMessage);
            } catch (TelegramApiException e) {
                LOGGER.error("Error [{}].", e.getMessage());
                throw new WeatherBotException(e);
            }
        } catch (TelegramApiException | JsonProcessingException e) {
            LOGGER.error("Error [{}].", e.getMessage());
            throw new WeatherBotException(e);
        }
    }


    @Override
    public String getBotToken() {
        return telegramBotProperties.getToken();
    }


    @Override
    public String getBotUsername() {
        return telegramBotProperties.getUsername();
    }


    @PostConstruct
    private void register() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            LOGGER.error("Error occurred while registering bot");
        }
    }

}
