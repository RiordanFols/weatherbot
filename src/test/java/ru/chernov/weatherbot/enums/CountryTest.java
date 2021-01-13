package ru.chernov.weatherbot.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.chernov.weatherbot.weather.Country;

/**
 * @author Pavel Chernov
 */
@SpringBootTest
@TestPropertySource(locations = {"/test.properties"})
public class CountryTest {

    @Test
    void shouldGetAllCountriesFromIso2() {
        Country[] countries = Country.values();
        for (var country : countries) {
            Assertions.assertEquals(country, Country.getByName(country.name()));
        }
    }

    @Test
    void shouldGetDefaultFromWrongIso2() {
        String countryName = "ZZZ";
        Assertions.assertEquals(Country.DEFAULT, Country.getByName(countryName));
    }
}
