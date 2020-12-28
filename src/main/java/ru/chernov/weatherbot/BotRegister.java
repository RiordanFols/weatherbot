package ru.chernov.weatherbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * @author Pavel Chernov
 */
@Component
public class BotRegister {

    private final Bot bot;

    @Autowired
    public BotRegister(Bot bot) {
        this.bot = bot;
        register(bot);
    }

    public void register(Bot bot) {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public Bot getBot() {
        return bot;
    }
}
