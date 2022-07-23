package ru.chernov.weatherbot.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Getter
public enum BotCommand {
    START("/start", "Привет, я бот погоды.\nВведи название города, погода в котором тебя интересует.\n"),
    HELP("/help", "Введи название города и я покажу тебе прогноз погоды");

    public static final Map<String, BotCommand> ALL = Arrays.stream(values())
            .collect(Collectors.toMap(
                    BotCommand::getCommandLine,
                    Function.identity()
            ));

    private final String commandLine;
    private final String answer;


    BotCommand(String commandLine, String answer) {
        this.commandLine = commandLine;
        this.answer = answer;
    }


    public String getCommandLine() {
        return commandLine;
    }


    public String getAnswer() {
        return answer;
    }

}
