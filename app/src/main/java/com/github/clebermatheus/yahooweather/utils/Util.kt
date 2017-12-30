package com.github.clebermatheus.yahooweather.utils

import com.github.clebermatheus.yahooweather.utils.enums.WeatherCode

/**
 * Constantes e funções que são utilizadas pelo Aplicativo.
 *
 * Created by clebermatheus on 9/23/17.
 */

object Util {
    const val MAX_REQUESTS = 5
    const val PREFERENCES = "YahooWeatherPreferences"

    /*
     * Obtém o Weather Code a partir de um inteiro qualquer
     */
    fun Int.getWeatherCode():WeatherCode {return WeatherCode.setValue(this)}
}
