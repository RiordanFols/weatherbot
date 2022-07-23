package ru.chernov.weatherbot.dto.internal;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class ForecastButtonDto implements Serializable {

    private Integer days;
    private String city;

}
