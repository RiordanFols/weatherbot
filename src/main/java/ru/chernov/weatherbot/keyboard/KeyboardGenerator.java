package ru.chernov.weatherbot.keyboard;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Chernov
 */
@Getter
@Component
public class KeyboardGenerator {

    private final List<ForecastButton> buttons = new ArrayList<>();

    public KeyboardGenerator() {
        buttons.add(new ForecastButton("Погода сейчас", 0));
        buttons.add(new ForecastButton("Прогноз на неделю", 7));
        buttons.add(new ForecastButton("Прогноз на 2 недели", 14));
    }

    public InlineKeyboardMarkup getAllOptions(String cityName) {
        var keyboardMarkup = new InlineKeyboardMarkup();  // объект разметки клавиатуры

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();  // список рядов кнопок
        for (ForecastButton btn : buttons) {
            List<InlineKeyboardButton> buttonRow = new ArrayList<>();  // ряд кнопок

            btn.setCallbackData(cityName + "/" + btn.getForecastDays());
            buttonRow.add(btn);  // добавление кнопки в ряд

            rowList.add(buttonRow);  // добавление ряда в список рядов
        }
        keyboardMarkup.setKeyboard(rowList);  // установка кнопок в разметку

        return keyboardMarkup;
    }
}
