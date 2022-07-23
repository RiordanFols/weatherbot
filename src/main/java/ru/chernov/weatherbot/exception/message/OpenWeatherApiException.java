package ru.chernov.weatherbot.exception.message;

public class OpenWeatherApiException extends AbstractMessageException {

    public OpenWeatherApiException() {
        super("Ошибка от Open Weather API");
    }

}
