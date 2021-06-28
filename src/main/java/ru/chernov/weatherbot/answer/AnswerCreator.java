package ru.chernov.weatherbot.answer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.chernov.weatherbot.keyboard.KeyboardGenerator;
import ru.chernov.weatherbot.weather.WeatherManager;

/**
 * Создает ответ на update пользователя
 * Обрабатывает текст, текстовую команду или нажатие на кнопку
 *
 * @author Pavel Chernov
 */
@Component
public class AnswerCreator {

    public static final String BUTTONS_HEADER = "Что тебя интересует?";

    private final WeatherManager weatherManager;
    private final KeyboardGenerator keyboardGenerator;

    @Autowired
    public AnswerCreator(WeatherManager weatherManager,
                         KeyboardGenerator keyboardGenerator) {
        this.weatherManager = weatherManager;
        this.keyboardGenerator = keyboardGenerator;
    }

    /**
     * Настраивает SendMessage для ответа пользователю
     *
     * @param messageOut SendMessage для настройки
     * @return настроенный SendMessage
     */
    private static SendMessage configureMessageOut(SendMessage messageOut) {
        messageOut.setParseMode("markdown");
        return messageOut;
    }

    /**
     * Возвращает готовый ответ на update пользователя
     * Основной метод класса
     *
     * @param update пришедший от пользователя update
     * @return готовое ответное сообщение
     */
    public SendMessage createAnswer(Update update) {

        // если пришло сообщение
        if (update.hasMessage()) {
            Message messageIn = update.getMessage();
            // если пришла команда
            if (messageIn.getText().startsWith("/")) {
                return answerForCommand(messageIn);
            } else {
                return answerForText(messageIn);
            }
        }

        // если пришло нажатие на кнопку
        if (update.hasCallbackQuery()) {
            return answerForCallback(update.getCallbackQuery());
        }

        // неизвестный запрос
        SendMessage messageOut = new SendMessage();
        messageOut.setText(ErrorAnswer.UNKNOWN_REQUEST.getErrorMessage());
        return configureMessageOut(messageOut);
    }

    /**
     * Обрабатывает пришедшее сообщение и выдает ответ
     * Ожидает название города в тексте сообщения
     *
     * @param message сообщение, взятое из update пользователя
     * @return ответное сообщение с текстом и несколькими кнопками
     */
    private SendMessage answerForText(Message message) {
        String text = message.getText();
        SendMessage messageOut = new SendMessage();
        messageOut.setChatId(message.getChatId().toString());

        if (!StringUtils.containsOnly(text, " -" +
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ")) {
            messageOut.setText(ErrorAnswer.UNACCEPTABLE_SYMBOLS.getErrorMessage());
            return configureMessageOut(messageOut);
        }

        if (text.length() < 2 || text.length() > 30) {
            messageOut.setText(ErrorAnswer.CITY_NOT_FOUND.getErrorMessage());
            return configureMessageOut(messageOut);
        }

        // проверка на существование города
        if (weatherManager.checkCityExisting(text)) {
            // создание кнопок
            messageOut.setReplyMarkup(keyboardGenerator.getAllOptions(text));
            messageOut.setText(BUTTONS_HEADER);
        } else {
            messageOut.setText(ErrorAnswer.CITY_NOT_FOUND.getErrorMessage());
        }

        return configureMessageOut(messageOut);
    }

    /**
     * Обрабатывает пришедший callback запрос и выдает ответ
     *
     * @param callbackQuery нажатие на кнопку, взятое из update
     * @return ответное сообщение с прогнозом погоды
     */
    private SendMessage answerForCallback(CallbackQuery callbackQuery) {
        // парсинг города и кол-ва дней из данных кнопки
        String cityName = callbackQuery.getData().split("/")[0];
        int forecastDays = Integer.parseInt(callbackQuery.getData().split("/")[1]);

        // получение прогноза
        SendMessage messageOut = new SendMessage();
        messageOut.setText(weatherManager.getForecast(cityName, forecastDays));
        messageOut.setChatId(callbackQuery.getMessage().getChatId().toString());

        return configureMessageOut(messageOut);
    }

    /**
     * Обрабатывает пришедшую команду и выдает ответ
     *
     * @param message сообщение, содержащее в тексте команду
     * @return ответное сообщение с ответом на команду
     */
    private SendMessage answerForCommand(Message message) {
        SendMessage messageOut = new SendMessage();
        messageOut.setText(Command.handleCommand(message.getText()));
        messageOut.setChatId(message.getChatId().toString());

        return configureMessageOut(messageOut);
    }
}
