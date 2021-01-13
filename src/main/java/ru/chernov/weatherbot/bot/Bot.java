package ru.chernov.weatherbot.bot;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Основной класс
 * Обрабатывает пришедший update и отсылает ответ пользователю
 *
 * @author Pavel Chernov
 */
@Component
@Log4j2
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

    /**
     * Отсылает пользователю результат работы answerCreator
     *
     * @param update update от пользователя
     */
    @Override
    public void onUpdateReceived(Update update) {
        try {
            execute(answerCreator.createAnswer(update));
        } catch (RuntimeException | TelegramApiException e) {
            log.error(e.getClass() + "\nText: " + update.getMessage().getText() + "\nMessage: " + e.getMessage());
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
