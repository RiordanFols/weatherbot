package ru.chernov.weatherbot.exception;

/**
 * @author Pavel Chernov
 */
public class WeatherUnavailableException extends RuntimeException {

    public WeatherUnavailableException(String message) {
        super(message);
    }
}
