package ru.chernov.weatherbot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = {"/test.properties"})
class WeatherbotApplicationTests {

    @Test
    void contextLoads() {
    }

}
