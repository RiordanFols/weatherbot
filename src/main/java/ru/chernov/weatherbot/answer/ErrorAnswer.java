package ru.chernov.weatherbot.answer;

import lombok.Getter;

/**
 * Набор ошибок, полученных в ходе анализа пользовательского запроса
 *
 * @author Pavel Chernov
 */
public enum ErrorAnswer {
    CITY_NOT_FOUND("Город не найден"),
    UNKNOWN_REQUEST("Неизвестный запрос"),
    UNACCEPTABLE_SYMBOLS("Допустимы только латиница/кириллица, дефисы и пробелы");

    @Getter
    private final String errorMessage;

    ErrorAnswer(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
