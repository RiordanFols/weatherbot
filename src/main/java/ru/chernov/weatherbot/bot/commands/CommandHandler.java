package ru.chernov.weatherbot.bot.commands;

import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * @author Pavel Chernov
 */
@Component
@Getter
public class CommandHandler {

    private final String missMessage = "Я не знаю такой команды";
    private final Command[] commands = Command.values();

    public String handle(String textIn) {
        if (textIn.equals("/help") || textIn.equals("/start")) {
            return Command.HelpCommand.helpInfo;
        }
        for (var command : commands) {
            if (textIn.toUpperCase().equals("/" + command.toString())) {
                return command.getAnswer();
            }
        }
        return missMessage;
    }
}
