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

    private SendMessage messageOut;

    @Autowired
    public AnswerCreator(WeatherManager weatherManager,
                         KeyboardGenerator keyboardGenerator) {
        this.weatherManager = weatherManager;
        this.keyboardGenerator = keyboardGenerator;
    }

    /**
     * Возвращает готовый ответ на update пользователя
     * Основной метод класса
     *
     * @param update пришедший от пользователя update
     * @return готовое ответное сообщение
     */
    public SendMessage createAnswer(Update update) {
        messageOut = new SendMessage();
        messageOut.setParseMode("markdown");

        if (update.hasMessage()) {  // если пришло сообщение
            Message messageIn = update.getMessage();

            if (messageIn.getText().startsWith("/")) {  // если пришла команда
                return answerForCommand(messageIn);
            } else {
                return answerForText(messageIn);
            }

        } else if (update.hasCallbackQuery()) {  // если пришло нажатие на кнопку
            return answerForCallback(update.getCallbackQuery());
        } else {  // неизвестный запрос
            messageOut.setText(ErrorAnswer.UNKNOWN_REQUEST.getErrorMessage());
            return messageOut;
        }
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

        messageOut.setChatId(message.getChatId().toString());

        if (!StringUtils.containsOnly(text, " -" +
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ")) {
            messageOut.setText(ErrorAnswer.UNACCEPTABLE_SYMBOLS.getErrorMessage());
            return messageOut;
        }

        if (text.length() < 2 || text.length() > 30) {
            messageOut.setText(ErrorAnswer.CITY_NOT_FOUND.getErrorMessage());
            return messageOut;
        }

        // проверка на существование города
        if (weatherManager.checkCityExisting(text)) {
            // создание кнопок
            messageOut.setReplyMarkup(keyboardGenerator.getAllOptions(text));
            messageOut.setText(BUTTONS_HEADER);
        } else {
            messageOut.setText(ErrorAnswer.CITY_NOT_FOUND.getErrorMessage());
        }

        return messageOut;
    }

    /**
     * Обрабатывает пришедшее сообщение и выдает ответ
     *
     * @param callbackQuery нажатие на кнопку, взятое из update
     * @return ответное сообщение с прогнозом погоды
     */
    private SendMessage answerForCallback(CallbackQuery callbackQuery) {
        // парсинг города и кол-ва дней из данных кнопки
        String cityName = callbackQuery.getData().split("/")[0];
        int forecastDays = Integer.parseInt(callbackQuery.getData().split("/")[1]);

        // получение прогноза
        messageOut.setText(weatherManager.getForecast(cityName, forecastDays));
        messageOut.setChatId(callbackQuery.getMessage().getChatId().toString());

        return messageOut;
    }

    /**
     * Обрабатывает пришедшую команду и выдает ответ
     *
     * @param message сообщение, содержащее в тексте команду
     * @return ответное сообщение с ответом на команду
     */
    private SendMessage answerForCommand(Message message) {
        // выдача информации по команде
        messageOut.setText(Command.handleCommand(message.getText()));
        messageOut.setChatId(message.getChatId().toString());

        return messageOut;
    }
}
