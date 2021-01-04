package ru.chernov.weatherbot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.chernov.weatherbot.weather.WeatherReceiver;

/**
 * @author Pavel Chernov
 */
@Component
public class Bot extends TelegramLongPollingBot {

    private final WeatherReceiver weatherReceiver;

    @Value("${telegram.bot.token}")
    private String token;

    @Value("${telegram.bot.username}")
    private String username;

    @Autowired
    public Bot(WeatherReceiver weatherReceiver) {
        this.weatherReceiver = weatherReceiver;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public SendMessage createAnswer(Update update) {
        Message messageIn = update.getMessage();
        String textIn = messageIn.getText();
        String chatId = messageIn.getChatId().toString();

        String textOut = weatherReceiver.executeRequest(textIn);

        SendMessage messageOut = new SendMessage();
        messageOut.setText(textOut);
        messageOut.setChatId(chatId);

        return messageOut;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage answer = createAnswer(update);

            try {
                execute(answer);
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