package com.github.clebermatheus.yahooweather.models

/**
 * Classe criada para tratar todos os elementos channel vindo da API.
 *
 * Created by clebermatheus on 9/24/17.
 */

data class Channel(
        var atmosphere: Atmosphere? = null,
        var astronomy: Astronomy? = null,
        var image: Image? = null,
        var item: Item? = null,
        var location: Location? = null,
        var title: String = "",
        var link: String = "",
        var language: String = "",
        var description: String = "",
        var lastBuildDate: String = "",
        var ttl: Int = 0,
        var units: Units? = null,
        var wind: Wind? = null
) {
    data class Atmosphere(
            var humidity: Int = 0,
            var visibility: Double = 0.0,
            var pressure: Double = 0.0,
            var rising: Int = 0
    )

    data class Astronomy(
            var sunrise: String = "",
            var sunset: String = ""
    )

    data class Image(
            var title: String = "",
            var link: String = "",
            var url: String = "",
            var width: Int = 0,
            var height: Int = 0
    )

    data class Location(
            var city: String = "",
            var region: String = "",
            var country: String = ""
    ) {
        override fun toString(): String {return "$city/$region - $country" }
    }

    data class Units(
            var distance: String = "",
            var pressure: String = "",
            var speed: String = "",
            var temperature: String = ""
    )

    data class Wind(
            var chill: Int = 0,
            var direction: Int = 0,
            var speed: Double = 0.0
    )
}
