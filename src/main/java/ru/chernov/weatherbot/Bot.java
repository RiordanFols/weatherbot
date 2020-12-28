package ru.chernov.weatherbot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author Pavel Chernov
 */
@Component
public class Bot extends TelegramLongPollingBot {

    private final String token;
    private final String username;

    public Bot(@Value("${telegram.bot.token}") String token,
               @Value("${telegram.bot.username}") String username) {
        this.token = token;
        this.username = username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message messageIn = update.getMessage();
            String textIn = messageIn.getText();
            String chatId = messageIn.getChatId().toString();

            SendMessage messageOut = new SendMessage();
            messageOut.setText(textIn);
            messageOut.setChatId(chatId);

            try {
                execute(messageOut);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }
}
