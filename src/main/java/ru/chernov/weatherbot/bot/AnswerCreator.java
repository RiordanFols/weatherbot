package ru.chernov.weatherbot.bot;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.chernov.weatherbot.command.Command;
import ru.chernov.weatherbot.keyboard.KeyboardGenerator;
import ru.chernov.weatherbot.weather.WeatherManager;

/**
 * @author Pavel Chernov
 */
@Component
public class AnswerCreator {

    private static final String CITY_NOT_FOUND = "Город не найден";
    private static final String BUTTONS_HEADER = "Что тебя интересует?";
    private static final String UNKNOWN_REQUEST = "Неизвестный запрос";
    private static final String UNACCEPTABLE_SYMBOLS = "Допустимы только латиница/кириллица, дефисы и пробелы";

    private final WeatherManager weatherManager;
    private final KeyboardGenerator keyboardGenerator;

    private SendMessage messageOut;

    @Autowired
    public AnswerCreator(WeatherManager weatherManager,
                         KeyboardGenerator keyboardGenerator) {
        this.weatherManager = weatherManager;
        this.keyboardGenerator = keyboardGenerator;
    }

    public SendMessage createAnswer(Update update) {

        messageOut = new SendMessage();
        messageOut.setParseMode("markdown");

        if (update.hasMessage()) {  // если пришло сообщение
            Message messageIn = update.getMessage();

            if (messageIn.getText().startsWith("/")) {  // если команда
                return answerForCommand(messageIn);
            } else {
                return answerForText(messageIn);
            }

        } else if (update.hasCallbackQuery()) {  // если пришло нажатие на кнопку
            return answerForCallback(update.getCallbackQuery());
        } else {  // неизвестный запрос
            messageOut.setText(UNKNOWN_REQUEST);
            return messageOut;
        }
    }

    private SendMessage answerForText(Message message) {
        String text = message.getText();

        messageOut.setChatId(message.getChatId().toString());

        if (!StringUtils.containsOnly(text, " -" +
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ")) {
            messageOut.setText(UNACCEPTABLE_SYMBOLS);
            return messageOut;
        }

        if (text.length() < 2 || text.length() > 30) {
            messageOut.setText(CITY_NOT_FOUND);
            return messageOut;
        }

        // проверка на существование города
        if (weatherManager.checkCityExisting(text)) {
            // создание кнопок
            messageOut.setReplyMarkup(keyboardGenerator.getAllOptions(text));
            messageOut.setText(BUTTONS_HEADER);
        } else {
            messageOut.setText(CITY_NOT_FOUND);
        }

        return messageOut;
    }

    private SendMessage answerForCallback(CallbackQuery callbackQuery) {
        // парсинг города и кол-ва дней из данных кнопки
        String cityName = callbackQuery.getData().split("/")[0];
        int forecastDays = Integer.parseInt(callbackQuery.getData().split("/")[1]);

        // получение прогноза
        messageOut.setText(weatherManager.getForecast(cityName, forecastDays));
        messageOut.setChatId(callbackQuery.getMessage().getChatId().toString());

        return messageOut;
    }

    private SendMessage answerForCommand(Message message) {
        // выдача информации по команде
        messageOut.setText(Command.handleCommand(message.getText()));
        messageOut.setChatId(message.getChatId().toString());

        return messageOut;
    }
}
