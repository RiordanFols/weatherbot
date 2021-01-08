package ru.chernov.weatherbot.weather;

import org.springframework.stereotype.Component;
import ru.chernov.weatherbot.dto.ForecastDto;
import ru.chernov.weatherbot.dto.WeatherDto;

/**
 * @author Pavel Chernov
 */
@Component
public class MessageManager {

    public String weatherInfo(WeatherDto dto) {

        var weather = new Weather(dto);
        return "*" + weather.getCityName() + "*" + "\n" +
                weather.getCondition().getRu() + " " + weather.getCondition().getEmoji() + "\n" +
                "Температура: " + weather.getTemp() + "\n" +
                "Ощущается как: " + weather.getTempFeelsLike() + "\n" +
                "Давление: " + weather.getPressure() + "\n" +
                "Влажность: " + weather.getHumidity() + "\n" +
                "Рассвет: " + weather.getSunriseTime() + "\n" +
                "Закат: " + weather.getSunsetTime();
    }

    public String forecastInfo(ForecastDto dto) {
        var forecast = new Forecast(dto);
        StringBuilder answer = new StringBuilder("*" + forecast.getCityName() + "*");
        for (var dayForecast : forecast.getForecasts()) {
            String[] months = {"янв", "фев", "мар", "апр", "мая", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"};
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
