package com.github.clebermatheus.yahooweather.models

import android.location.Location
import com.github.clebermatheus.yahooweather.utils.enums.WeatherCode
import java.util.*

/**
 * Classe criada para tratar o item recebido da API.
 *
 * Created by clebermatheus on 9/24/17.
 */

data class Item(
        val condition: Condition? = null,
        val forecast: Array<Forecast?> = arrayOfNulls(1),
        val location: Location? = null,
        val title: String = "",
        val link: String = "",
        val pubDate: String = "",
        val description: String = "",
        val lat: Double = 0.0,
        val long: Double = 0.0,
        val guid: Guid? = null
) {
    data class Condition(
            val code: Int = 0,
            val date: String = "",
            val temp: Int = 0,
            val text: String = ""
    )

    data class Forecast(
            val code: WeatherCode = WeatherCode.NOT_AVAILABE,
            val date: String = "",
            val day: String = "",
            val high: Int = 0,
            val low: Int = 0,
            val text: String = ""
    )

    data class Guid(val isPermalink: Boolean = false)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Item

        if (condition != other.condition) return false
        if (!Arrays.equals(forecast, other.forecast)) return false
        if (location != other.location) return false
        if (title != other.title) return false
        if (link != other.link) return false
        if (pubDate != other.pubDate) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = condition?.hashCode() ?: 0
        result = 31 * result + Arrays.hashCode(forecast)
        result = 31 * result + (location?.hashCode() ?: 0)
        result = 31 * result + title.hashCode()
        result = 31 * result + link.hashCode()
        result = 31 * result + pubDate.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + lat.hashCode()
        result = 31 * result + long.hashCode()
        result = 31 * result + (guid?.hashCode() ?: 0)
        return result
    }
}
