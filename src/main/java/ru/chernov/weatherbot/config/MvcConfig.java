package ru.chernov.weatherbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


/**
 * Конфиг необходим для создания RestTemplate
 * С помощью RestTemplate осуществляются все Rest запросы в приложении
 *
 * @author Pavel Chernov
 */
@Configuration
public class MvcConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
