package ru.chernov.weatherbot.exception.message;

public class UnknownCommandException extends AbstractMessageException {

    public UnknownCommandException() {
        super("Я не знаю такой команды");
    }

}
