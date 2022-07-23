package ru.chernov.weatherbot.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.chernov.weatherbot.dto.openweather.forecast.ForecastCityResponse;
import ru.chernov.weatherbot.dto.openweather.forecast.ForecastDayResponse;
import ru.chernov.weatherbot.dto.openweather.forecast.ForecastDayTemperatureResponse;
import ru.chernov.weatherbot.dto.openweather.forecast.ForecastResponse;
import ru.chernov.weatherbot.enums.Country;
import ru.chernov.weatherbot.enums.WeatherCondition;
import ru.chernov.weatherbot.service.time.TimeProvider;

import java.time.LocalDate;


@Component
public class ForecastMessageFormatter implements AbstractMessageFormatter<ForecastResponse> {

    private static final String FORECAST_HEADER_PATTERN = "*%s* %s";
    private static final String FORECAST_DAY_PATTERN = "\n%d %s: %s %s°C макс. / %s°C мин.";
    private static final String[] MONTHS = {"янв", "фев", "мар", "апр", "мая", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"};

    private final TimeProvider timeProvider;


    @Autowired
    public ForecastMessageFormatter(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }


    @Override
    public String formatMessage(ForecastResponse forecastResponse) {
        ForecastCityResponse cityResponse = forecastResponse.getCity();

        String flagEmoji = Country.getByName(cityResponse.getCountryCode()).getEmoji();
        StringBuilder result = new StringBuilder(String.format(FORECAST_HEADER_PATTERN, cityResponse.getName(), flagEmoji));

        LocalDate now = timeProvider.today();
        for (int i = 0; i < forecastResponse.getDayForecasts().size(); i++) {
            ForecastDayResponse dayForecast = forecastResponse.getDayForecasts().get(i);
            LocalDate forecastOn = now.plusDays(i);
            WeatherCondition weatherCondition = WeatherCondition.valueOf(dayForecast.getConditions().get(0).getCondition().toUpperCase());
            ForecastDayTemperatureResponse temperatureResponse = dayForecast.getTemperature();
            result.append(String.format(FORECAST_DAY_PATTERN,
                    forecastOn.getDayOfMonth(),
                    MONTHS[forecastOn.getMonthValue() - 1],
                    weatherCondition.getEmoji(),
                    temperatureResponse.getMaxTemperature(),
                    temperatureResponse.getMinTemperature()
            ));
        }
        return result.toString();
    }

}
