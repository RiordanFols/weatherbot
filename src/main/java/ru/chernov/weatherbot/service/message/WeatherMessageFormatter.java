package ru.chernov.weatherbot.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.chernov.weatherbot.dto.internal.WindDto;
import ru.chernov.weatherbot.dto.openweather.weather.CurrentWeatherIndicatorsResponse;
import ru.chernov.weatherbot.dto.openweather.weather.CurrentWeatherResponse;
import ru.chernov.weatherbot.dto.openweather.weather.CurrentWeatherSystemResponse;
import ru.chernov.weatherbot.dto.openweather.weather.CurrentWeatherWindResponse;
import ru.chernov.weatherbot.enums.Country;
import ru.chernov.weatherbot.enums.WeatherCondition;
import ru.chernov.weatherbot.service.wind.WindProvider;


@Component
public class WeatherMessageFormatter implements AbstractMessageFormatter<CurrentWeatherResponse> {

    private static final String WEATHER_PATTERN = """
            *%s* %s
            %s %s
            Температура: %s°C
            Ощущается как: %s°C
            Ветер: %s %s м/c
            Давление: %s мм рт.ст.
            Влажность: %s%%
            Рассвет: %s
            Закат: %s
            """;

    private final WindProvider windProvider;


    @Autowired
    public WeatherMessageFormatter(WindProvider windProvider) {
        this.windProvider = windProvider;
    }


    @Override
    public String formatMessage(CurrentWeatherResponse weatherResponse) {
        String providedWeatherCondition = weatherResponse.getConditions().get(0).getCondition().toUpperCase();
        WeatherCondition weatherCondition = WeatherCondition.valueOf(providedWeatherCondition);

        CurrentWeatherWindResponse providedWind = weatherResponse.getWind();
        WindDto wind = windProvider.getWind(providedWind.getDegree(), providedWind.getSpeed());

        CurrentWeatherIndicatorsResponse weatherMain = weatherResponse.getIndicators();
        CurrentWeatherSystemResponse weatherSystem = weatherResponse.getSystem();
        Integer timezoneSeconds = weatherResponse.getTimezoneSeconds();

        return String.format(WEATHER_PATTERN,
                weatherResponse.getCityName(),
                Country.getByName(weatherSystem.getCountryCode()).getEmoji(),
                weatherCondition.getDescription(),
                weatherCondition.getEmoji(),
                weatherMain.getTemperature(),
                weatherMain.getTemperatureFeeling(),
                wind.getDirection().getEmoji(),
                wind.getSpeed(),
                weatherMain.getPressure() * 3 / 4,
                weatherMain.getHumidity(),
                getZonedTime(weatherSystem.getSunriseEpochSeconds(), timezoneSeconds),
                getZonedTime(weatherSystem.getSunsetEpochSeconds(), timezoneSeconds)
        );
    }

}
