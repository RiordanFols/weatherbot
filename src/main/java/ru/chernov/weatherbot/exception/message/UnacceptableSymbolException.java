package ru.chernov.weatherbot.exception.message;

public class UnacceptableSymbolException extends AbstractMessageException {

    public UnacceptableSymbolException() {
        super("Недопустимый символ. Допустимы только латиница/кириллица, дефисы и пробелы");
    }

}
