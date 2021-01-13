package ru.chernov.weatherbot.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.chernov.weatherbot.keyboard.ForecastButton;
import ru.chernov.weatherbot.keyboard.KeyboardGenerator;

/**
 * @author Pavel Chernov
 */
@SpringBootTest
@TestPropertySource(locations = {"/test.properties"})
public class KeyboardGeneratorTest {

    private final KeyboardGenerator keyboardGenerator;

    @Autowired
    public KeyboardGeneratorTest(KeyboardGenerator keyboardGenerator) {
        this.keyboardGenerator = keyboardGenerator;
    }

    @Test
    void shouldGenerateButtons() {
        InlineKeyboardMarkup keyboardMarkup = keyboardGenerator.getAllOptions("Москва");

        Assertions.assertNotNull(keyboardMarkup);
        Assertions.assertEquals(keyboardGenerator.getButtons().size(), keyboardMarkup.getKeyboard().size());

        for (int i = 0; i < keyboardMarkup.getKeyboard().size(); i++) {
            InlineKeyboardButton btn = keyboardMarkup.getKeyboard().get(i).get(0);

            Assertions.assertTrue(btn instanceof ForecastButton);
            Assertions.assertEquals(keyboardGenerator.getButtons().get(i), btn);
        }
    }

}
