package ru.chernov.weatherbot;

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
    void shouldGetCountryFromIso2() {
        Country[] countries = Country.values();
        for (var country : countries) {
            Assertions.assertEquals(Country.getByName(country.name()), country);
        }
    }
}
