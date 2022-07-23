package ru.chernov.weatherbot.service.keyboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.chernov.weatherbot.config.property.ForecastProperties;
import ru.chernov.weatherbot.dto.internal.ForecastButtonDto;

import java.util.ArrayList;
import java.util.List;


@Component
public class KeyboardGenerator {
    private final ForecastProperties forecastProperties;
    private final ObjectMapper objectMapper;


    @Autowired
    public KeyboardGenerator(ForecastProperties forecastProperties, ObjectMapper objectMapper) {
        this.forecastProperties = forecastProperties;
        this.objectMapper = objectMapper;
    }


    public InlineKeyboardMarkup getForecastButtons(String cityName) throws JsonProcessingException {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (ForecastProperties.Button buttonProperties : forecastProperties.getButtons()) {
            ForecastButtonDto buttonDto = new ForecastButtonDto();
            buttonDto.setDays(buttonProperties.getDays());
            buttonDto.setCity(cityName);
            String serializedButtonDto = objectMapper.writeValueAsString(buttonDto);

            InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
            keyboardButton.setText(buttonProperties.getText());
            keyboardButton.setCallbackData(serializedButtonDto);

            List<InlineKeyboardButton> buttonRow = new ArrayList<>();
            buttonRow.add(keyboardButton);
            rowList.add(buttonRow);
        }

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        keyboardMarkup.setKeyboard(rowList);
        return keyboardMarkup;
    }

}
