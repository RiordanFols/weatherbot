package ru.chernov.weatherbot.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.chernov.weatherbot.command.Command;

/**
 * @author Pavel Chernov
 */
@SpringBootTest
@TestPropertySource(locations = {"/test.properties"})
public class CommandTest {

    @Test
    void shouldAnswerToAllCommands() {
        Command[] commands = Command.values();
        for (var command : commands) {
            String commandIn = "/" + command.name().toLowerCase();
            Assertions.assertEquals(command.getAnswer(), Command.handleCommand(commandIn));
        }
    }

    @Test
    void shouldAnswerToWrongCommand() {
        Assertions.assertEquals(Command.DEFAULT.getAnswer(), Command.handleCommand("/tra123ta123"));
    }
}
