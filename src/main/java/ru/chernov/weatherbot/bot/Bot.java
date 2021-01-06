package ru.chernov.weatherbot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.chernov.weatherbot.bot.commands.CommandHandler;
import ru.chernov.weatherbot.weather.WeatherReceiver;

/**
 * @author Pavel Chernov
 */
@Component
public final class Bot extends TelegramLongPollingBot {

    private final WeatherReceiver weatherReceiver;
    private final CommandHandler commandHandler;


    @Value("${telegram.bot.token}")
    private String token;

    @Value("${telegram.bot.username}")
    private String username;

    @Autowired
    public Bot(WeatherReceiver weatherReceiver, CommandHandler commandHandler) {
        this.weatherReceiver = weatherReceiver;
        this.commandHandler = commandHandler;
    }

    public SendMessage createAnswer(Update update) {
        Message messageIn = update.getMessage();
        String textIn = messageIn.getText();
        String chatId = messageIn.getChatId().toString();

        String textOut;
        if (textIn.startsWith("/")) {
            textOut = commandHandler.handle(textIn);
        } else {
            textOut = weatherReceiver.getWeather(textIn);
        }

        SendMessage messageOut = new SendMessage();
        messageOut.setParseMode("markdown");
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
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }
}
