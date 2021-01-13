package ru.chernov.weatherbot.keyboard;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

/**
 * Кнопка InlineKeyboardButton с добавленией кол-ва дней длительности прогноза
 * @author Pavel Chernov
 */
@Getter
public class ForecastButton extends InlineKeyboardButton {

    private final int forecastDays;

    public ForecastButton(String text, int forecastDays) {
        super(text);
        this.forecastDays = forecastDays;
    }
}
