package com.github.clebermatheus.yahooweather.utils.enums;

/**
 * Enum contendo todos os códigos utilizado na API.
 *
 * Created by clebermatheus on 9/24/17.
 */

public enum WeatherCode {
    TORNADO("tornado", 0),
    TROPICAL_STORM("Tempestade tropical", 1),
    HURRICANE("Furacão", 2),
    SEVERE_THUNDERSTORMS("Trovoadas Severas", 3),
    THUNDERSTORMS("Tempestades", 4),
    MIXED_RAIN_AND_SNOW("Chuva e neve misturados", 5),
    MIXED_RAIN_AND_SLEET("Chuva e granizo misturados", 6),
    MIXED_SNOW_AND_SLEET("Neve e granizo misturados", 7),
    FREEZING_DRIZZLE("Garoa gelada", 8),
    DRIZZLE("Garoa", 9),
    FREEZING_RAIN("Chuva congelante", 10),
    SHOWERS_11("Chuvas", 11),
    SHOWERS_12("Chuvas", 12),
    SNOW_FLURRIES("Flocos de neve", 13),
    LIGHT_SNOW_SHOWERS("Nevascas leves", 14),
    BLOWING_SNOW("Neve com vento", 15),
    SNOW("Neve", 16),
    HAIL("Granizo", 17),
    SLEET("Sleet", 18),
    DUST("Poeira", 19),
    FOGGY("Nebuloso", 20),
    HAZE("Neblina", 21),
    SMOKY("Esfumaçado", 22),
    BLUSTERY("Tempestuoso", 23),
    WINDY("Vento", 24),
    COLD("frio", 25),
    CLOUDY("nublado", 26),
    MOSTLY_CLOUDY_NIGHT("predominantemente nublado durante à noite", 27),
    MOSTLY_CLOUDY_DAY("predominantemente nublado durante o dia", 28),
    PARTLY_CLOUDY_NIGHT("parcialmente nublado durante à noite", 29),
    PARTLY_CLOUDY_DAY("parcialmente nublado durante o dia", 30),
    CLEAR_NIGHT("limpo durante à noite", 31),
    SUNNY("ensolarado", 32),
    FAIR_NIGHT("bom durante à noite", 33),
    FAIR_DAY("bom durante o dia", 34),
    MIXED_RAIN_AND_HAIL("chuva mista e granizo", 35),
    HOT("quente", 36),
    ISOLATED_THUNDERSTORMS("tempestades isoladas", 37),
    SCATTERED_THUNDERSTORMS_38("tempestades dispersas", 38),
    SCATTERED_THUNDERSTORMS_39("tempestades dispersas", 39),
    SCATTERED_SHOWERS("chuvas dispersas", 40),
    HEAVY_SNOW_41("neve pesada", 41),
    SCATTERED_SNOW_SHOWERS("nevadas dispersas", 42),
    HEAVY_SNOW_43("neve pesada", 43),
    PARTLY_CLOUDY("parcialmente nublado", 44),
    THUNDERSHOWERS("trovoadas", 45),
    SNOW_SHOWERS("chuvas de neve", 46),
    ISLOATED_THUNDERSHOWERS("trovoadas isoladas", 47),
    NOT_AVAILABE("não disponível", 32000);

    private final String stringValue;
    private final int intValue;

    WeatherCode(String toString, int value){
        stringValue = toString;
        intValue = value;
    }

    public int getValue(){return intValue;}
    public static WeatherCode setValue(int value){
        for(WeatherCode code: WeatherCode.values()){if(value == code.getValue()){return code;}}
        return NOT_AVAILABE;
    }

    @Override
    public String toString() {return stringValue;}
}
