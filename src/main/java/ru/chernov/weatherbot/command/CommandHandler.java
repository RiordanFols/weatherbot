package ru.chernov.weatherbot.command;

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
        for (var command : commands) {
            if (textIn.toUpperCase().equals("/" + command.toString())) {
                return command.getAnswer();
            }
        }
        return missMessage;
    }
}
