package ru.chernov.weatherbot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Pavel Chernov
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastDto {

}
