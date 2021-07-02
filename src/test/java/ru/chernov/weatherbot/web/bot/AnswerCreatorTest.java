package ru.chernov.weatherbot.web.bot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.chernov.weatherbot.answer.AnswerCreator;
import ru.chernov.weatherbot.answer.ErrorAnswer;
import ru.chernov.weatherbot.answer.Command;

/**
 * @author Pavel Chernov
 */
@SpringBootTest
@TestPropertySource(locations = {"/test.properties"})
public class AnswerCreatorTest {

    private final Update testUpdate = new Update();
    private final Message testMessage = new Message();
    private final CallbackQuery testCQ = new CallbackQuery();

    private final AnswerCreator answerCreator;

    @Autowired
    public AnswerCreatorTest(AnswerCreator answerCreator) {
        this.answerCreator = answerCreator;

        // фейк-чат
        Chat chat = new Chat();
        chat.setId(100L);

        testMessage.setChat(chat);
        testMessage.setText(null);

        var cqMessage = new Message();
        cqMessage.setChat(chat);
        cqMessage.setText(null);
        testCQ.setMessage(cqMessage);
        testCQ.setData(null);
    }

    @BeforeEach
    void createUpdate() {
        testMessage.setText(null); // пустое сообщение
        testCQ.setData(null); // пустой callback

        testUpdate.setCallbackQuery(null); // callback отсутствует
        testUpdate.setMessage(null); // сообщение отсутствует
    }

    @Test
    void shouldAnswerForCommand() {
        testMessage.setText("/start");
        testUpdate.setMessage(testMessage);

        SendMessage messageOut = answerCreator.createAnswer(testUpdate);

        Assertions.assertEquals(Command.START.getAnswer(), messageOut.getText());
        Assertions.assertNull(messageOut.getReplyMarkup()); // has no buttons
    }

    @Test
    void shouldAnswerForWrongCommand() {
        testMessage.setText("/notARealCommand");
        testUpdate.setMessage(testMessage);

        SendMessage messageOut = answerCreator.createAnswer(testUpdate);

        Assertions.assertEquals(Command.DEFAULT.getAnswer(), messageOut.getText());
        Assertions.assertNull(messageOut.getReplyMarkup()); // has no buttons
    }

    @Test
    void shouldAnswerToCityName() {
        testMessage.setText("Moscow");
        testUpdate.setMessage(testMessage);

        SendMessage messageOut = answerCreator.createAnswer(testUpdate);

        Assertions.assertEquals(AnswerCreator.BUTTONS_HEADER, messageOut.getText());
        Assertions.assertNotNull(messageOut.getReplyMarkup()); // has buttons
    }

    @Test
    void shouldAnswerToWrongCityName() {
        testMessage.setText("notRealCityName");
        testUpdate.setMessage(testMessage);

        SendMessage messageOut = answerCreator.createAnswer(testUpdate);

        Assertions.assertEquals(ErrorAnswer.CITY_NOT_FOUND.getErrorMessage(), messageOut.getText());
        Assertions.assertNull(messageOut.getReplyMarkup()); // has no buttons
    }

    @Test
    void shouldAnswerToUnacceptableSymbols() {
        testMessage.setText("e22&^%$!!#@*()_+-=0");
        testUpdate.setMessage(testMessage);

        SendMessage messageOut = answerCreator.createAnswer(testUpdate);

        Assertions.assertEquals(ErrorAnswer.UNACCEPTABLE_SYMBOLS.getErrorMessage(), messageOut.getText());
        Assertions.assertNull(messageOut.getReplyMarkup()); // has no buttons
    }

    @Test
    void shouldAnswerToTooLongMessage() {
        testMessage.setText("TooooooooooooooooooooooooooooooLoooooooooooooooooooooooooooooong");
        testUpdate.setMessage(testMessage);

        SendMessage messageOut = answerCreator.createAnswer(testUpdate);

        Assertions.assertEquals(ErrorAnswer.CITY_NOT_FOUND.getErrorMessage(), messageOut.getText());
        Assertions.assertNull(messageOut.getReplyMarkup()); // has no buttons
    }

    @Test
    void shouldAnswerToWeatherRequest() {
        testCQ.setData("Moscow/0");
        testUpdate.setCallbackQuery(testCQ);

        SendMessage messageOut = answerCreator.createAnswer(testUpdate);

        Assertions.assertNotNull(messageOut.getText());
        Assertions.assertNull(messageOut.getReplyMarkup()); // has no buttons
    }

    @Test
    void shouldAnswerToForecastRequest() {
        testCQ.setData("Сидней/3");
        testUpdate.setCallbackQuery(testCQ);

        SendMessage messageOut = answerCreator.createAnswer(testUpdate);

        Assertions.assertNotNull(messageOut.getText());
        Assertions.assertNull(messageOut.getReplyMarkup()); // has no buttons
    }
}
