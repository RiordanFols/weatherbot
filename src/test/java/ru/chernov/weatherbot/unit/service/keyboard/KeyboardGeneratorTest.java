package ru.chernov.weatherbot.unit.service.keyboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.chernov.weatherbot.AbstractTest;
import ru.chernov.weatherbot.config.property.ForecastProperties;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class KeyboardGeneratorTest extends AbstractTest {

    @Test
    void shouldProvideButtons() throws JsonProcessingException {
        InlineKeyboardMarkup keyboard = keyboardGenerator.getForecastButtons(TEST_CITY_NAME);
        List<List<InlineKeyboardButton>> keyboardMatrix = keyboard.getKeyboard();
        assertEquals(forecastProperties.getButtons().size(), keyboardMatrix.size());
        for (int i = 0; i < forecastProperties.getButtons().size(); i++) {
            List<InlineKeyboardButton> keyboardLine = keyboardMatrix.get(i);
            ForecastProperties.Button buttonProperty = forecastProperties.getButtons().get(i);

            assertEquals(1, keyboardLine.size());

            InlineKeyboardButton keyboardButton = keyboardLine.get(0);
            assertEquals(buttonProperty.getText(), keyboardButton.getText());
            assertNotNull(keyboardButton.getCallbackData());
        }
    }

}
