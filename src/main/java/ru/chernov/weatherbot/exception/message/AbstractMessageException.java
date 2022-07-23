package ru.chernov.weatherbot.exception.message;

import lombok.Getter;


@Getter
public abstract class AbstractMessageException extends RuntimeException {

    private final String errorMessage;


    protected AbstractMessageException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
