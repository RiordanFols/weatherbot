package ru.chernov.weatherbot;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.test.context.TestPropertySource;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.chernov.weatherbot.dto.internal.ForecastButtonDto;


@TestPropertySource(locations = {"/test.properties"})
public class AbstractTest extends AbstractDependenciesTest {

    protected static final String TEST_CITY_NAME = "Берлин";
    protected static final String TEST_HYPHEN_CITY_NAME = "Санкт-Петербург";
    protected static final String TEST_SPACE_CITY_NAME = "Новый Уренгой";
    protected static final Chat TEST_FAKE_CHAT = new Chat(100L, "Some type");


    protected Message composeMessage(String text) {
        Message message = new Message();
        message.setChat(TEST_FAKE_CHAT);
        message.setText(text);
        return message;
    }


    protected String composeCallbackData(Integer days) throws JsonProcessingException {
        ForecastButtonDto forecastButtonDto = new ForecastButtonDto();
        forecastButtonDto.setCity(TEST_CITY_NAME);
        forecastButtonDto.setDays(days);
        return objectMapper.writeValueAsString(forecastButtonDto);
    }

}
