package ru.chernov.weatherbot.service.time;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static java.time.ZoneOffset.UTC;


/**
 * Удобно мокать текущее время
 */
@Component
public class TimeProvider {

    public LocalDate today() {
        return LocalDate.now(UTC);
    }

}
