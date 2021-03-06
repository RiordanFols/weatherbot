package ru.chernov.weatherbot.weather;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.chernov.weatherbot.dto.ForecastDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс прогноза погоды, трансформируемый из ForecastDto
 *
 * @author Pavel Chernov
 */
@Getter
public class Forecast {

    private final String cityName;
    private final Country country;
    private final int days;

    private final List<DayForecast> forecasts = new ArrayList<>();

    public Forecast(ForecastDto dto) {
        if (dto == null || dto.getStatus() != 200)
            throw new IllegalArgumentException("Bad dto");

        this.country = Country.getByName(dto.getCountryName());
        this.cityName = dto.getCityName();
        this.days = dto.getDays();

        for (int i = 0; i < days; i++) {
            forecasts.add(new DayForecast(
                    WeatherCondition.valueOf(dto.getCondition().get(i).toUpperCase()),
                    String.format("%.0f°C", dto.getDayTemp().get(i)),
                    String.format("%.0f°C", dto.getNightTemp().get(i)),
                    LocalDate.now().plusDays(i)
            ));
        }
    }

    /**
     * Прогноз на 1 день
     *
     * @author Pavel Chernov
     */
    @Getter
    @EqualsAndHashCode
    public static class DayForecast {
        private final WeatherCondition condition;
        private final String dayTemp;
        private final String nightTemp;
        private final LocalDate localDate;

        public DayForecast(WeatherCondition condition, String dayTemp,
                           String nightTemp, LocalDate localDate) {
            this.condition = condition;
            this.dayTemp = dayTemp;
            this.nightTemp = nightTemp;
            this.localDate = localDate;
        }
    }
}
