package ru.chernov.weatherbot.command;

import lombok.Getter;

/**
 * @author Pavel Chernov
 */
@Getter
public enum Command {
    START("Я бот погоды, я могу рассказать про погоду в любом городе," +
            " а также составить прогноз. Просто введи название города"),
    HELP("Введи название города и я покажу тебе прогноз погоды");

    private final String answer;

    Command(String answer) {
        this.answer = answer;
    }

}
