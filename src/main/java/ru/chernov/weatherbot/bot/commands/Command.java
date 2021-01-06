package ru.chernov.weatherbot.bot.commands;

import lombok.Getter;

/**
 * @author Pavel Chernov
 */
@Getter
public enum Command {
    FORECAST1("Введи название города для прогноза", "прогноз на завтра"),
    FORECAST3("Введи название города для прогноза", "прогноз на 3 дня"),
    FORECAST7("Введи название города для прогноза", "прогноз на 7 дней"),
    FORECAST14("Введи название города для прогноза", "прогноз на 14 дней");

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
