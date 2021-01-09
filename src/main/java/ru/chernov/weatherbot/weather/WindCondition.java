package ru.chernov.weatherbot.weather;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Pavel Chernov
 */
@Getter
public enum WindCondition {
    NORTHERN("⬇"),
    NORTHEASTERN("↙"),
    EASTERN("⬅"),
    SOUTHEASTERN("↖"),
    SOUTHERN("⬆"),
    SOUTHWESTERN("↗"),
    WESTERN("➡"),
    NORTHWESTERN("↘");

    @Setter
    private int speed;
    @Setter
    private String emoji;

    WindCondition(String emoji) {
        this.emoji = emoji;
    }

    public static WindCondition getWind(double deg, int speed) {
        var windCondition = getWindDirection(deg);
        windCondition.setSpeed(speed);
        if (windCondition.getSpeed() == 0)
            windCondition.setEmoji("");

        return windCondition;
    }

    private static WindCondition getWindDirection(double deg) {
        if (deg < 0 || deg > 360)
            throw new IllegalArgumentException();

        if (deg <= 22.5)
            return NORTHERN;
        if (deg <= 67.5)
            return NORTHEASTERN;
        if (deg <= 112.5)
            return EASTERN;
        if (deg <= 157.5)
            return SOUTHEASTERN;
        if (deg <= 202.5)
            return SOUTHERN;
        if (deg <= 247.5)
            return SOUTHWESTERN;
        if (deg <= 292.5)
            return WESTERN;
        if (deg <= 337.5)
            return NORTHWESTERN;
        return NORTHERN;
    }
}
