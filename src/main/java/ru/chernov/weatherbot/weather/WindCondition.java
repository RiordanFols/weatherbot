package ru.chernov.weatherbot.weather;

import lombok.Getter;
import lombok.Setter;

/**
 * Возможные направления ветра с делением 45 градусов
 * Также эмодзи к ним и скорость ветра
 *
 * @author Pavel Chernov
 */
@Getter
public enum WindCondition {
    N("⬇"),
    NE("↙"),
    E("⬅"),
    SE("↖"),
    S("⬆"),
    SW("↗"),
    W("➡"),
    NW("↘");

    @Setter
    private int speed;
    @Setter
    private String emoji;

    WindCondition(String emoji) {
        this.emoji = emoji;
    }

    /**
     * Получение объекта енама по углу и скорости ветра
     *
     * @param deg   градус угла от севера по часовой стрелке
     * @param speed скорость ветра в м/с
     * @return WindCondition
     */
    public static WindCondition getWind(double deg, int speed) {
        var windCondition = getWindDirection(deg);
        windCondition.setSpeed(speed);
        if (windCondition.getSpeed() == 0)
            windCondition.setEmoji("");

        return windCondition;
    }

    /**
     * Определяет направление ветра по градусу
     *
     * @param deg градус угла от севера по часовой стрелке
     * @return WindCondition
     */
    private static WindCondition getWindDirection(double deg) {
        if (deg < 0 || deg > 360)
            throw new IllegalArgumentException("The angle can be from 0 to 360 degrees");

        if (deg <= 22.5)
            return N;
        if (deg <= 67.5)
            return NE;
        if (deg <= 112.5)
            return E;
        if (deg <= 157.5)
            return SE;
        if (deg <= 202.5)
            return S;
        if (deg <= 247.5)
            return SW;
        if (deg <= 292.5)
            return W;
        if (deg <= 337.5)
            return NW;
        return N;
    }
}
