package ru.chernov.weatherbot.unit.service.wind;

import org.junit.jupiter.api.Test;
import ru.chernov.weatherbot.AbstractTest;
import ru.chernov.weatherbot.dto.internal.WindDto;
import ru.chernov.weatherbot.enums.WindDirection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class WindProviderTest extends AbstractTest {

    @Test
    void shouldNotAcceptWrongDegree() {
        assertThrows(IllegalArgumentException.class, () -> windProvider.getWind(1000, 5));
    }


    @Test
    void shouldProvidePositiveSpeedWind() {
        Integer speed = 3;
        WindDto wind = windProvider.getWind(250, speed);
        assertEquals(WindDirection.WEST, wind.getDirection());
        assertEquals(speed, wind.getSpeed());
    }


    @Test
    void shouldProvideZeroWind() {
        Integer speed = 0;
        WindDto wind = windProvider.getWind(0, speed);
        assertEquals(WindDirection.DEFAULT, wind.getDirection());
        assertEquals(speed, wind.getSpeed());
    }

}
