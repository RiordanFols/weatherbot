package ru.chernov.weatherbot.unit.service.handler;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.chernov.weatherbot.AbstractTest;
import ru.chernov.weatherbot.enums.BotCommand;
import ru.chernov.weatherbot.exception.message.UnknownCommandException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class CommandRequestHandlerTest extends AbstractTest {

    @Test
    void shouldProvideCommandsAnswer() {
        Update update;
        for (BotCommand command : BotCommand.ALL.values()) {
            update = new Update();
            update.setMessage(composeMessage(command.getCommandLine()));
            SendMessage sendMessage = commandRequestHandler.handle(update);
            assertEquals(command.getAnswer(), sendMessage.getText());
            assertNull(sendMessage.getReplyMarkup());
        }
    }


    @Test
    void shouldNotRecognizeCommand() {
        Update update = new Update();
        update.setMessage(composeMessage("/unknown_command_1435_*&@$%#@"));
        assertThrows(UnknownCommandException.class, () -> commandRequestHandler.handle(update));
    }

}
