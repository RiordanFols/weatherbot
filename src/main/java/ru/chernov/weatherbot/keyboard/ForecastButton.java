package ru.chernov.weatherbot.keyboard;

import lombok.Getter;
import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

/**
 * @author Pavel Chernov
 */
@Getter
public class ForecastButton extends InlineKeyboardButton {

    private final int forecastDays;

    public ForecastButton(@NonNull String text, int forecastDays) {
        super(text);
        this.forecastDays = forecastDays;
    }
}
