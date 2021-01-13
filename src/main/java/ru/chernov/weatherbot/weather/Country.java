package ru.chernov.weatherbot.weather;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Страна и ее флаг-эмодзи
 *
 * @author Pavel Chernov
 */
public enum Country {
    DEFAULT(""),
    // ISO2 коды стран и их флаги в виде эмодзи
    AF("\uD83C\uDDE6\uD83C\uDDEB"),
    AD("\uD83C\uDDE6\uD83C\uDDE9"),
    AE("\uD83C\uDDE6\uD83C\uDDEA"),
    AG("\uD83C\uDDE6\uD83C\uDDEC"),
    AL("\uD83C\uDDE6\uD83C\uDDF1"),
    AM("\uD83C\uDDE6\uD83C\uDDF2"),
    AO("\uD83C\uDDE6\uD83C\uDDF4"),
    AR("\uD83C\uDDE6\uD83C\uDDF7"),
    AT("\uD83C\uDDE6\uD83C\uDDF9"),
    AU("\uD83C\uDDE6\uD83C\uDDFA"),
    AZ("\uD83C\uDDE6\uD83C\uDDFF"),
    BA("\uD83C\uDDE7\uD83C\uDDE6"),
    BB("\uD83C\uDDE7\uD83C\uDDE7"),
    BD("\uD83C\uDDE7\uD83C\uDDE9"),
    BE("\uD83C\uDDE7\uD83C\uDDEA"),
    BF("\uD83C\uDDE7\uD83C\uDDEB"),
    BG("\uD83C\uDDE7\uD83C\uDDEC"),
    BH("\uD83C\uDDE7\uD83C\uDDED"),
    BI("\uD83C\uDDE7\uD83C\uDDEE"),
    BJ("\uD83C\uDDE7\uD83C\uDDEF"),
    BN("\uD83C\uDDE7\uD83C\uDDF3"),
    BO("\uD83C\uDDE7\uD83C\uDDF4"),
    BR("\uD83C\uDDE7\uD83C\uDDF7"),
    BS("\uD83C\uDDE7\uD83C\uDDF8"),
    BT("\uD83C\uDDE7\uD83C\uDDF9"),
    BW("\uD83C\uDDE7\uD83C\uDDFC"),
    BY("\uD83C\uDDE7\uD83C\uDDFE"),
    BZ("\uD83C\uDDE7\uD83C\uDDFF"),
    CA("\uD83C\uDDE8\uD83C\uDDE6"),
    CD("\uD83C\uDDE8\uD83C\uDDE9"),
    CF("\uD83C\uDDE8\uD83C\uDDEB"),
    CG("\uD83C\uDDE8\uD83C\uDDEC"),
    CH("\uD83C\uDDE8\uD83C\uDDED"),
    CI("\uD83C\uDDE8\uD83C\uDDEE"),
    CK("\uD83C\uDDE8\uD83C\uDDF0"),
    CL("\uD83C\uDDE8\uD83C\uDDF1"),
    CM("\uD83C\uDDE8\uD83C\uDDF2"),
    CN("\uD83C\uDDE8\uD83C\uDDF3"),
    CO("\uD83C\uDDE8\uD83C\uDDF4"),
    CR("\uD83C\uDDE8\uD83C\uDDF7"),
    CU("\uD83C\uDDE8\uD83C\uDDFA"),
    CV("\uD83C\uDDE8\uD83C\uDDFB"),
    CY("\uD83C\uDDE8\uD83C\uDDFE"),
    CZ("\uD83C\uDDE8\uD83C\uDDFF"),
    DE("\uD83C\uDDE9\uD83C\uDDEA"),
    DJ("\uD83C\uDDE9\uD83C\uDDEF"),
    DK("\uD83C\uDDE9\uD83C\uDDF0"),
    DM("\uD83C\uDDE9\uD83C\uDDF2"),
    DO("\uD83C\uDDE9\uD83C\uDDF4"),
    DZ("\uD83C\uDDE9\uD83C\uDDFF"),
    EC("\uD83C\uDDEA\uD83C\uDDE8"),
    EE("\uD83C\uDDEA\uD83C\uDDEA"),
    EG("\uD83C\uDDEA\uD83C\uDDEC"),
    ER("\uD83C\uDDEA\uD83C\uDDF7"),
    ES("\uD83C\uDDEA\uD83C\uDDF8"),
    ET("\uD83C\uDDEA\uD83C\uDDF9"),
    FI("\uD83C\uDDEB\uD83C\uDDEE"),
    FJ("\uD83C\uDDEB\uD83C\uDDEF"),
    FM("\uD83C\uDDEB\uD83C\uDDF2"),
    FO("\uD83C\uDDEB\uD83C\uDDF4"),
    FR("\uD83C\uDDEB\uD83C\uDDF7"),
    GA("\uD83C\uDDEC\uD83C\uDDE6"),
    GB("\uD83C\uDDEC\uD83C\uDDE7"),
    GD("\uD83C\uDDEC\uD83C\uDDE9"),
    GE("\uD83C\uDDEC\uD83C\uDDEA"),
    GH("\uD83C\uDDEC\uD83C\uDDED"),
    GM("\uD83C\uDDEC\uD83C\uDDF2"),
    GN("\uD83C\uDDEC\uD83C\uDDF3"),
    GQ("\uD83C\uDDEC\uD83C\uDDF6"),
    GR("\uD83C\uDDEC\uD83C\uDDF7"),
    GT("\uD83C\uDDEC\uD83C\uDDF9"),
    GW("\uD83C\uDDEC\uD83C\uDDFC"),
    GY("\uD83C\uDDEC\uD83C\uDDFE"),
    HN("\uD83C\uDDED\uD83C\uDDF3"),
    HR("\uD83C\uDDED\uD83C\uDDF7"),
    HT("\uD83C\uDDED\uD83C\uDDF9"),
    HU("\uD83C\uDDED\uD83C\uDDFA"),
    ID("\uD83C\uDDEE\uD83C\uDDE9"),
    IE("\uD83C\uDDEE\uD83C\uDDEA"),
    IL("\uD83C\uDDEE\uD83C\uDDF1"),
    IN("\uD83C\uDDEE\uD83C\uDDF3"),
    IQ("\uD83C\uDDEE\uD83C\uDDF6"),
    IR("\uD83C\uDDEE\uD83C\uDDF7"),
    IS("\uD83C\uDDEE\uD83C\uDDF8"),
    IT("\uD83C\uDDEE\uD83C\uDDF9"),
    JM("\uD83C\uDDEF\uD83C\uDDF2"),
    JO("\uD83C\uDDEF\uD83C\uDDF4"),
    JP("\uD83C\uDDEF\uD83C\uDDF5"),
    KE("\uD83C\uDDF0\uD83C\uDDEA"),
    KG("\uD83C\uDDF0\uD83C\uDDEC"),
    KH("\uD83C\uDDF0\uD83C\uDDED"),
    KI("\uD83C\uDDF0\uD83C\uDDEE"),
    KM("\uD83C\uDDF0\uD83C\uDDF2"),
    KN("\uD83C\uDDF0\uD83C\uDDF3"),
    KP("\uD83C\uDDF0\uD83C\uDDF5"),
    KR("\uD83C\uDDF0\uD83C\uDDF7"),
    KW("\uD83C\uDDF0\uD83C\uDDFC"),
    KZ("\uD83C\uDDF0\uD83C\uDDFF"),
    LA("\uD83C\uDDF1\uD83C\uDDE6"),
    LB("\uD83C\uDDF1\uD83C\uDDE7"),
    LC("\uD83C\uDDF1\uD83C\uDDE8"),
    LK("\uD83C\uDDF1\uD83C\uDDF0"),
    LR("\uD83C\uDDF1\uD83C\uDDF7"),
    LS("\uD83C\uDDF1\uD83C\uDDF8"),
    LT("\uD83C\uDDF1\uD83C\uDDF9"),
    LU("\uD83C\uDDF1\uD83C\uDDFA"),
    LV("\uD83C\uDDF1\uD83C\uDDFB"),
    LY("\uD83C\uDDF1\uD83C\uDDFE"),
    MA("\uD83C\uDDF2\uD83C\uDDE6"),
    MC("\uD83C\uDDF2\uD83C\uDDE8"),
    MD("\uD83C\uDDF2\uD83C\uDDE9"),
    ME("\uD83C\uDDF2\uD83C\uDDEA"),
    MG("\uD83C\uDDF2\uD83C\uDDEC"),
    MH("\uD83C\uDDF2\uD83C\uDDED"),
    MK("\uD83C\uDDF2\uD83C\uDDF0"),
    ML("\uD83C\uDDF2\uD83C\uDDF1"),
    MM("\uD83C\uDDF2\uD83C\uDDF2"),
    MN("\uD83C\uDDF2\uD83C\uDDF3"),
    MR("\uD83C\uDDF2\uD83C\uDDF7"),
    MT("\uD83C\uDDF2\uD83C\uDDF9"),
    MU("\uD83C\uDDF2\uD83C\uDDFA"),
    MV("\uD83C\uDDF2\uD83C\uDDFB"),
    MW("\uD83C\uDDF2\uD83C\uDDFC"),
    MX("\uD83C\uDDF2\uD83C\uDDFD"),
    MY("\uD83C\uDDF2\uD83C\uDDFE"),
    MZ("\uD83C\uDDF2\uD83C\uDDFF"),
    NA("\uD83C\uDDF3\uD83C\uDDE6"),
    NE("\uD83C\uDDF3\uD83C\uDDEA"),
    NG("\uD83C\uDDF3\uD83C\uDDEC"),
    NI("\uD83C\uDDF3\uD83C\uDDEE"),
    NL("\uD83C\uDDF3\uD83C\uDDF1"),
    NO("\uD83C\uDDF3\uD83C\uDDF4"),
    NP("\uD83C\uDDF3\uD83C\uDDF5"),
    NR("\uD83C\uDDF3\uD83C\uDDF7"),
    NU("\uD83C\uDDF3\uD83C\uDDFA"),
    NZ("\uD83C\uDDF3\uD83C\uDDFF"),
    OM("\uD83C\uDDF4\uD83C\uDDF2"),
    PA("\uD83C\uDDF5\uD83C\uDDE6"),
    PE("\uD83C\uDDF5\uD83C\uDDEA"),
    PG("\uD83C\uDDF5\uD83C\uDDEC"),
    PH("\uD83C\uDDF5\uD83C\uDDED"),
    PK("\uD83C\uDDF5\uD83C\uDDF0"),
    PL("\uD83C\uDDF5\uD83C\uDDF1"),
    PT("\uD83C\uDDF5\uD83C\uDDF9"),
    PW("\uD83C\uDDF5\uD83C\uDDFC"),
    PY("\uD83C\uDDF5\uD83C\uDDFE"),
    QA("\uD83C\uDDF6\uD83C\uDDE6"),
    RO("\uD83C\uDDF7\uD83C\uDDF4"),
    RS("\uD83C\uDDF7\uD83C\uDDF8"),
    RU("\uD83C\uDDF7\uD83C\uDDFA"),
    RW("\uD83C\uDDF7\uD83C\uDDFC"),
    SA("\uD83C\uDDF8\uD83C\uDDE6"),
    SB("\uD83C\uDDF8\uD83C\uDDE7"),
    SC("\uD83C\uDDF8\uD83C\uDDE8"),
    SD("\uD83C\uDDF8\uD83C\uDDE9"),
    SE("\uD83C\uDDF8\uD83C\uDDEA"),
    SG("\uD83C\uDDF8\uD83C\uDDEC"),
    SI("\uD83C\uDDF8\uD83C\uDDEE"),
    SK("\uD83C\uDDF8\uD83C\uDDF0"),
    SL("\uD83C\uDDF8\uD83C\uDDF1"),
    SM("\uD83C\uDDF8\uD83C\uDDF2"),
    SN("\uD83C\uDDF8\uD83C\uDDF3"),
    SO("\uD83C\uDDF8\uD83C\uDDF4"),
    SR("\uD83C\uDDF8\uD83C\uDDF7"),
    SS("\uD83C\uDDF8\uD83C\uDDF8"),
    ST("\uD83C\uDDF8\uD83C\uDDF9"),
    SV("\uD83C\uDDF8\uD83C\uDDFB"),
    SY("\uD83C\uDDF8\uD83C\uDDFE"),
    SZ("\uD83C\uDDF8\uD83C\uDDFF"),
    TD("\uD83C\uDDF9\uD83C\uDDE9"),
    TG("\uD83C\uDDF9\uD83C\uDDEC"),
    TH("\uD83C\uDDF9\uD83C\uDDED"),
    TJ("\uD83C\uDDF9\uD83C\uDDEF"),
    TK("\uD83C\uDDF9\uD83C\uDDF0"),
    TL("\uD83C\uDDF9\uD83C\uDDF1"),
    TM("\uD83C\uDDF9\uD83C\uDDF2"),
    TN("\uD83C\uDDF9\uD83C\uDDF3"),
    TO("\uD83C\uDDF9\uD83C\uDDF4"),
    TR("\uD83C\uDDF9\uD83C\uDDF7"),
    TT("\uD83C\uDDF9\uD83C\uDDF9"),
    TV("\uD83C\uDDF9\uD83C\uDDFB"),
    TZ("\uD83C\uDDF9\uD83C\uDDFF"),
    UA("\uD83C\uDDFA\uD83C\uDDE6"),
    UG("\uD83C\uDDFA\uD83C\uDDEC"),
    US("\uD83C\uDDFA\uD83C\uDDF8"),
    UY("\uD83C\uDDFA\uD83C\uDDFE"),
    UZ("\uD83C\uDDFA\uD83C\uDDFF"),
    VC("\uD83C\uDDFB\uD83C\uDDE8"),
    VE("\uD83C\uDDFB\uD83C\uDDEA"),
    VN("\uD83C\uDDFB\uD83C\uDDF3"),
    VU("\uD83C\uDDFB\uD83C\uDDFA"),
    WS("\uD83C\uDDFC\uD83C\uDDF8"),
    YE("\uD83C\uDDFE\uD83C\uDDEA"),
    ZA("\uD83C\uDDFF\uD83C\uDDE6"),
    ZM("\uD83C\uDDFF\uD83C\uDDF2"),
    ZW("\uD83C\uDDFF\uD83C\uDDFC");

    @Getter
    private final String emoji;

    Country(String emoji) {
        this.emoji = emoji;
    }

    /**
     * Возвращает enum по Iso2 коду страны
     *
     * @param name имя страны в Iso2 коде
     * @return готовый enum
     */
    public static Country getByName(String name) {
        // получение списка имен всех стран
        List<String> countries = Arrays.stream(Country.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        if (countries.contains(name)) {
            return Country.valueOf(name.toUpperCase());
        } else {
            // выдача дефолтной страны без флага
            return Country.DEFAULT;
        }
    }
}
