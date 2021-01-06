package ru.chernov.weatherbot.bot.commands;

import lombok.Getter;

/**
 * @author Pavel Chernov
 */
@Getter
public enum Command {
    FORECAST1("Введи название города для прогноза", "прогноз погоды на завтра"),
    FORECAST3("Введи название города для прогноза", "прогноз погоды на 3 дня"),
    FORECAST5("Введи название города для прогноза", "прогноз погоды на 5 дней"),
    FORECAST7("Введи название города для прогноза", "прогноз погоды на 7 дней");

    private final String answer;
    private final String description;

    Command(String answer, String description) {
        this.answer = answer;
        this.description = description;
        HelpCommand.helpInfo += "/" + this.name().toLowerCase() + " - " + this.description + "\n";
    }

    public static class HelpCommand {
        public static String helpInfo = "Я бот погоды, я могу рассказать про погоду в любом городе, а также составить" +
                " прогноз. Просто введи название города, чтобы посмотреть погоду или используй команды:\n";

    }
}
