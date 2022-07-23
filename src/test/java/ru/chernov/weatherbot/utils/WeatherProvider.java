package ru.chernov.weatherbot.utils;

import ru.chernov.weatherbot.dto.openweather.WeatherConditionResponse;
import ru.chernov.weatherbot.dto.openweather.forecast.ForecastCityResponse;
import ru.chernov.weatherbot.dto.openweather.forecast.ForecastDayResponse;
import ru.chernov.weatherbot.dto.openweather.forecast.ForecastDayTemperatureResponse;
import ru.chernov.weatherbot.dto.openweather.forecast.ForecastResponse;
import ru.chernov.weatherbot.dto.openweather.weather.CurrentWeatherIndicatorsResponse;
import ru.chernov.weatherbot.dto.openweather.weather.CurrentWeatherResponse;
import ru.chernov.weatherbot.dto.openweather.weather.CurrentWeatherSystemResponse;
import ru.chernov.weatherbot.dto.openweather.weather.CurrentWeatherWindResponse;
import ru.chernov.weatherbot.enums.Country;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.ZoneOffset.UTC;


public class WeatherProvider {

    private static final Integer TIMEZONE_SECONDS = 0;


    public static CurrentWeatherResponse composeCurrentWeather(String condition, Integer humidity, Integer pressure,
                                                               Integer temperature, Integer temperatureFeeling,
                                                               Country country, LocalDateTime sunriseAt,
                                                               LocalDateTime sunsetAt, Integer windDegree,
                                                               Integer windSpeed, String cityName) {
        WeatherConditionResponse conditionResponse = new WeatherConditionResponse();
        conditionResponse.setCondition(condition);

        CurrentWeatherIndicatorsResponse indicatorsResponse = new CurrentWeatherIndicatorsResponse();
        indicatorsResponse.setHumidity(humidity);
        indicatorsResponse.setPressure(pressure);
        indicatorsResponse.setTemperature(temperature);
        indicatorsResponse.setTemperatureFeeling(temperatureFeeling);

        CurrentWeatherSystemResponse systemResponse = new CurrentWeatherSystemResponse();
        systemResponse.setCountryCode(country.name());
        systemResponse.setSunriseEpochSeconds((int) sunriseAt.toEpochSecond(UTC));
        systemResponse.setSunsetEpochSeconds((int) sunsetAt.toEpochSecond(UTC));

        CurrentWeatherWindResponse windResponse = new CurrentWeatherWindResponse();
        windResponse.setDegree(windDegree);
        windResponse.setSpeed(windSpeed);

        CurrentWeatherResponse response = new CurrentWeatherResponse();
        response.setConditions(List.of(conditionResponse));
        response.setIndicators(indicatorsResponse);
        response.setSystem(systemResponse);
        response.setWind(windResponse);
        response.setCityName(cityName);
        response.setTimezoneSeconds(TIMEZONE_SECONDS);
        return response;
    }


    public static ForecastResponse composeForecast(String cityName, List<ForecastDayResponse> dayForecasts) {
        ForecastCityResponse cityResponse = new ForecastCityResponse();
        cityResponse.setCountryCode(Country.DE.name());
        cityResponse.setName(cityName);

        ForecastResponse response = new ForecastResponse();
        response.setCity(cityResponse);
        response.setDayForecasts(dayForecasts);
        return response;
    }


    public static ForecastDayResponse composeDayResponse(String weatherCondition,
                                                         Integer maxTemperature,
                                                         Integer minTemperature) {
        WeatherConditionResponse conditionResponse = new WeatherConditionResponse();
        conditionResponse.setCondition(weatherCondition);

        ForecastDayTemperatureResponse temperatureResponse = new ForecastDayTemperatureResponse();
        temperatureResponse.setMinTemperature(minTemperature);
        temperatureResponse.setMaxTemperature(maxTemperature);

        ForecastDayResponse dayResponse = new ForecastDayResponse();
        dayResponse.setConditions(List.of(conditionResponse));
        dayResponse.setTemperature(temperatureResponse);
        return dayResponse;
    }

}
