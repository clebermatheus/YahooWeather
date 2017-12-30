package com.github.clebermatheus.yahooweather.models

/**
 * Classe criada para tratar todos os elementos channel vindo da API.
 *
 * Created by clebermatheus on 9/24/17.
 */

data class Channel(
        val atmosphere: Atmosphere? = null,
        val astronomy: Astronomy? = null,
        val image: Image? = null,
        val item: Item? = null,
        val location: Location? = null,
        val title: String = "",
        val link: String = "",
        val language: String = "",
        val description: String = "",
        val lastBuildDate: String = "",
        val ttl: Int = 0,
        val units: Units? = null,
        val wind: Wind? = null
) {
    data class Atmosphere(
            val humidity: Int = 0,
            val visibility: Double = 0.0,
            val pressure: Double = 0.0,
            val rising: Int = 0
    )

    data class Astronomy(
            val sunrise: String = "",
            val sunset: String = ""
    )

    data class Image(
            val title: String = "",
            val link: String = "",
            val url: String = "",
            val width: Int = 0,
            val height: Int = 0
    )

    data class Location(
            val city: String = "",
            val region: String = "",
            val country: String = ""
    ) {
        override fun toString(): String = "$city/$region - $country"
    }

    data class Units(
            val distance: String,
            val pressure: String,
            val speed: String,
            val temperature: String
    )

    data class Wind(
            val chill: Int = 0,
            val direction: Int = 0,
            val speed: Double = 0.0
    )
}
