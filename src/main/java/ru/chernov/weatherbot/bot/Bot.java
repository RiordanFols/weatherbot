package ru.chernov.weatherbot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author Pavel Chernov
 */
@Component
public class Bot extends TelegramLongPollingBot {

    private final AnswerCreator answerCreator;

    @Value("${telegram.bot.token}")
    private String token;

    @Value("${telegram.bot.username}")
    private String username;

    @Autowired
    public Bot(AnswerCreator answerCreator) {
        this.answerCreator = answerCreator;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            execute(answerCreator.createAnswer(update));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }
}
