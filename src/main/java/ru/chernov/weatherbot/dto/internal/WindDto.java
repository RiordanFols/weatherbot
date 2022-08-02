package ru.chernov.weatherbot.dto.internal;

import lombok.Getter;
import lombok.Setter;
import ru.chernov.weatherbot.enums.WindDirection;


@Getter
@Setter
public class WindDto {

    private WindDirection direction;
    private Integer speed;

}
