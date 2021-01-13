package ru.chernov.weatherbot.weather;

import lombok.Getter;

/**
 * Возможные состояния погоды по api.openweather и эмодзи к ним
 *
 * @author Pavel Chernov
 */
@Getter
public enum WeatherCondition {
    THUNDERSTORM("Гроза", "\uD83C\uDF29"),
    DRIZZLE("Небольшой дождь", "\uD83C\uDF27"),
    RAIN("Дождь", "\uD83C\uDF27"),
    SNOW("Снег", "\uD83C\uDF28"),
    MIST("Небольшой туман", "\uD83C\uDF2B"),
    SMOKE("Загрязнение воздуха", "\uD83C\uDF2B"),
    HAZE("Дымка", "\uD83C\uDF2B"),
    DUST("Пылевая буря", "\uD83C\uDF2B"),
    FOG("Туман", "\uD83C\uDF2B"),
    SAND("Песчаная буря", "\uD83C\uDF2B"),
    ASH("Вулканный пепел", "\uD83C\uDF0B"),
    SQUALL("Шквал", "\uD83D\uDCA8"),
    TORNADO("Торнадо", "\uD83C\uDF2A"),
    CLEAR("Ясно", "\u2600"),
    CLOUDS("Облачно", "\u2601");

    private final String ru;
    private final String emoji;

    WeatherCondition(String ru, String emoji) {
        this.ru = ru;
        this.emoji = emoji;
    }

}
