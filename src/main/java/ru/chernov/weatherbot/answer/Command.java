package ru.chernov.weatherbot.answer;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Хранит все команды и занимается их обработкой
 *
 * @author Pavel Chernov
 */
@Getter
public enum Command {
    DEFAULT("Я не знаю такой команды"),
    START("Привет, я бот погоды.\nВведи название города, погода в котором тебя интересует.\n"),
    HELP("Введи название города и я покажу тебе прогноз погоды");

    private final String answer;

    Command(String answer) {
        this.answer = answer;
    }

    /**
     * Обрабатывает команду и выдает ответ
     * Если команды не существует, выдает информацию об этом
     *
     * @param command команда в формате "/command"
     * @return ответ на команду
     */
    public static String handleCommand(String command) {
        // /help -> HELP
        command = command.substring(1).toUpperCase();

        // получение списка имен всех команд
        List<String> commands = Arrays.stream(Command.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        // если команда существует
        if (commands.contains(command)) {
            // выдаем ответ на команду
            return Command.valueOf(command).getAnswer();
        } else {
            // возвращаем ответ об отсутствии команды
            return DEFAULT.getAnswer();
        }
    }

}
