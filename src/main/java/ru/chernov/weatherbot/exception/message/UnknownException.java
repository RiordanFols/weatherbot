package ru.chernov.weatherbot.exception.message;

public class UnknownException extends AbstractMessageException {

    public UnknownException() {
        super("Неизвестная ошибка");
    }

}
