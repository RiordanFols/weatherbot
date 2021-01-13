package ru.chernov.weatherbot.bot;

import org.springframework.stereotype.Component;
import ru.chernov.weatherbot.dto.ForecastDto;
import ru.chernov.weatherbot.dto.WeatherDto;
import ru.chernov.weatherbot.weather.Forecast;
import ru.chernov.weatherbot.weather.Weather;

/**
 * Формирует сообщение для ответа пользователю
 * Работает с уже созданными dto от api.openweather
 *
 * @author Pavel Chernov
 */
@Component
public class MessageFormatter {

    public static final String weatherPattern = "*%s* %s\n%s %s\nТемпература: %s\nОщущается как: %s\n" +
            "Ветер: %s %s м/c\nДавление: %s\nВлажность: %s\nРассвет: %s\nЗакат: %s";

    public static final String[] months = {
            "янв", "фев", "мар", "апр", "мая", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"};

    /**
     * Подготавливает сообщение о текущей погоде
     * Использует паттерн weatherPattern
     *
     * @param dto сформированный WeatherDto
     * @return состояние погоды в виде строки
     */
    public String weatherInfo(WeatherDto dto) {
        if (dto == null || dto.getStatus() != 200)
            throw new IllegalArgumentException("Bad dto");

        var weather = new Weather(dto);
        var weatherCondition = weather.getWeatherCondition();
        var windCondition = weather.getWindCondition();

        return String.format(weatherPattern,
                weather.getCityName(),
                weather.getCountry().getEmoji(),
                weatherCondition.getRu(),
                weatherCondition.getEmoji(),
                weather.getTemp(),
                weather.getTempFeelsLike(),
                windCondition.getEmoji(),
                windCondition.getSpeed(),
                weather.getPressure(),
                weather.getHumidity(),
                weather.getSunriseTime(),
                weather.getSunsetTime());
    }

    /**
     * Подготавливает сообщение о прогнозе погоды
     *
     * @param dto сформированный ForecastDto
     * @return прогноз погоды в виде строки
     */
    public String forecastInfo(ForecastDto dto) {
        if (dto == null || dto.getStatus() != 200)
            throw new IllegalArgumentException("Bad dto");

        var forecast = new Forecast(dto);
        var emoji = forecast.getCountry().getEmoji();

        StringBuilder answer = new StringBuilder("*" + forecast.getCityName() + "* " + emoji);
        for (var dayForecast : forecast.getForecasts()) {
            String day = String.valueOf(dayForecast.getLocalDate().getDayOfMonth());
            String month = months[dayForecast.getLocalDate().getMonthValue() - 1];

            answer.append("\n")
                    .append(day).append(" ")
                    .append(month).append(": ")
                    .append(dayForecast.getCondition().getRu())
                    .append(dayForecast.getCondition().getEmoji())
                    .append(" ").append(dayForecast.getDayTemp())
                    .append(" / ").append(dayForecast.getNightTemp());
        }
        return answer.toString();
    }
}
