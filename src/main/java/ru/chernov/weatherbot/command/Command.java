package ru.chernov.weatherbot.command;

import lombok.Getter;

/**
 * @author Pavel Chernov
 */
@Getter
public enum Command {
    START("Привет, я бот погоды.\n" +
            "Я могу рассказать про погоду.\n" +
            "Также могу составить прогноз.\n" +
            "Просто введи название города."),
    HELP("Введи название города и я покажу тебе прогноз погоды");

    private final String answer;

    Command(String answer) {
        this.answer = answer;
    }

}
