package ru.chernov.weatherbot.unit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.chernov.weatherbot.bot.MessageFormatter;

/**
 * @author Pavel Chernov
 */
@SpringBootTest
@TestPropertySource(locations = {"/test.properties"})
public class MessageFormatterTest {

    private final MessageFormatter messageFormatter;

    @Autowired
    public MessageFormatterTest(MessageFormatter messageFormatter) {
        this.messageFormatter = messageFormatter;
    }

    @Test
    void shouldGetWeatherInfo() {

    }

    @Test
    void shouldGetForecastInfo() {

    }
}
