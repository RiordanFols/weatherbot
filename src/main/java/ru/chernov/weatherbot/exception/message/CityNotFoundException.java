package ru.chernov.weatherbot.exception.message;

public class CityNotFoundException extends AbstractMessageException {

    public CityNotFoundException() {
        super("Город не найден");
    }

}
