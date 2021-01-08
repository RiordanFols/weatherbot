package ru.chernov.weatherbot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.chernov.weatherbot.command.CommandHandler;
import ru.chernov.weatherbot.keyboard.KeyboardGenerator;
import ru.chernov.weatherbot.weather.WeatherManager;

/**
 * @author Pavel Chernov
 */
@Component
public class Bot extends TelegramLongPollingBot {

    private final WeatherManager weatherManager;
    private final KeyboardGenerator keyboardGenerator;
    private final CommandHandler commandHandler;

    @Value("${telegram.bot.token}")
    private String token;

    @Value("${telegram.bot.username}")
    private String username;

    @Autowired
    public Bot(WeatherManager weatherManager,
               KeyboardGenerator keyboardGenerator,
               CommandHandler commandHandler) {
        this.weatherManager = weatherManager;
        this.keyboardGenerator = keyboardGenerator;
        this.commandHandler = commandHandler;
    }

    public SendMessage createAnswer(Update update) {

        SendMessage messageOut = new SendMessage();
        messageOut.setParseMode("markdown");
        String chatId = "";
        String textOut = "";

        if (update.hasMessage()) {  // если пришло сообщение
            Message messageIn = update.getMessage();
            String textIn = messageIn.getText();
            chatId = messageIn.getChatId().toString();

            if (textIn.startsWith("/")) {  // если команда
                textOut = commandHandler.handle(textIn);
            } else if (weatherManager.checkCityExisting(textIn)) {
                var inlineKeyboardMarkup = keyboardGenerator.getAllOptions(textIn);
                textOut = "Что вас интересует?";
                messageOut.setReplyMarkup(inlineKeyboardMarkup);
            } else {
                textOut = "Город не найден";
            }
        } else if (update.hasCallbackQuery()) {  // если пришло нажатие на кнопку
            chatId = update.getCallbackQuery().getMessage().getChatId().toString();
            String cityName = update.getCallbackQuery().getData().split("/")[0];
            int forecastDays = Integer.parseInt(update.getCallbackQuery().getData().split("/")[1]);
            textOut = weatherManager.getForecast(cityName, forecastDays);
        }
        messageOut.setText(textOut);
        messageOut.setChatId(chatId);
        return messageOut;
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage answer = createAnswer(update);
        try {
            execute(answer);
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
