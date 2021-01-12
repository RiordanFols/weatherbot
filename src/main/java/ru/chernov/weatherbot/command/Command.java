package ru.chernov.weatherbot.command;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Pavel Chernov
 */
@Getter
public enum Command {
    DEFAULT("Я не знаю такой команды"),
    START("Привет, я бот погоды.\n" +
            "Я могу рассказать про погоду.\n" +
            "Также могу составить прогноз.\n" +
            "Просто введи название города."),
    HELP("Введи название города и я покажу тебе прогноз погоды");

    private final String answer;

    Command(String answer) {
        this.answer = answer;
    }

    public static String handleCommand(String command) {
        // /help -> HELP
        command = command.substring(1).toUpperCase();

        // получение списка имен всех команд
        List<String> commands = Arrays.stream(Command.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        if (commands.contains(command)) {
            return Command.valueOf(command).getAnswer();
        } else {
            return DEFAULT.getAnswer();
        }
    }

}
