package ru.chernov.weatherbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.chernov.weatherbot.config.property.ForecastProperties;
import ru.chernov.weatherbot.service.handler.RequestHandlerService;
import ru.chernov.weatherbot.service.handler.impl.CallbackRequestHandler;
import ru.chernov.weatherbot.service.handler.impl.CommandRequestHandler;
import ru.chernov.weatherbot.service.handler.impl.TextRequestHandler;
import ru.chernov.weatherbot.service.keyboard.KeyboardGenerator;
import ru.chernov.weatherbot.service.message.ForecastMessageFormatter;
import ru.chernov.weatherbot.service.message.WeatherMessageFormatter;
import ru.chernov.weatherbot.service.rest.OpenWeatherRestService;
import ru.chernov.weatherbot.service.wind.WindProvider;


@SpringBootTest
public class AbstractDependenciesTest {

    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected RequestHandlerService requestHandlerService;
    @Autowired
    protected ForecastProperties forecastProperties;
    @Autowired
    protected TextRequestHandler textRequestHandler;
    @Autowired
    protected CallbackRequestHandler callbackRequestHandler;
    @Autowired
    protected CommandRequestHandler commandRequestHandler;
    @Autowired
    protected KeyboardGenerator keyboardGenerator;
    @Autowired
    protected OpenWeatherRestService openWeatherRestService;
    @Autowired
    protected WeatherMessageFormatter weatherMessageFormatter;
    @Autowired
    protected ForecastMessageFormatter forecastMessageFormatter;
    @Autowired
    protected WindProvider windProvider;

}
