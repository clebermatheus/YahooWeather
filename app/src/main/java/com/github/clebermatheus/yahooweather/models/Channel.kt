package com.github.clebermatheus.yahooweather.models

import org.json.JSONObject

/**
 * Classe criada para tratar todos os elementos channel vindo da API.
 *
 * Created by clebermatheus on 9/24/17.
 */

data class Channel(private val json: JSONObject) {
    var atmosphere: Atmosphere? = null
        private set
    var astronomy: Astronomy? = null
        private set
    var image: Image? = null
        private set
    var item: Item? = null
        private set
    var location: Location? = null
        private set
    var title: String? = null
        private set
    var link: String? = null
        private set
    var language: String? = null
        private set
    var description: String? = null
        private set
    var lastBuildDate: String? = null
        private set
    var ttl: Int = 0
        private set
    var units: Units? = null
        private set
    var wind: Wind? = null
        private set

    init {
        try {
            this.atmosphere = Atmosphere(json.getJSONObject("atmosphere"))
            this.astronomy = Astronomy(json.getJSONObject("astronomy"))
            this.image = Image(json.getJSONObject("image"))
            this.item = Item(json.getJSONObject("item"))
            this.location = Location(json.getJSONObject("location"))
            this.title = json.getString("title")
            this.link = json.getString("link")
            this.language = json.getString("language")
            this.description = json.getString("description")
            this.lastBuildDate = json.getString("lastBuildDate")
            this.ttl = json.getInt("ttl")
            this.units = Units(json.getJSONObject("units"))
            this.wind = Wind(json.getJSONObject("wind"))
        } catch (e: Exception) { e.printStackTrace() }
    }

    data class Atmosphere(private val json: JSONObject) {
        var humidity: Int = 0
            private set
        var visibility: Int = 0
            private set
        var pressure: Int = 0
            private set
        var rising: Int = 0
            private set

        init {
            try {
                this.humidity = json.getInt("humidity")
                this.visibility = json.getInt("visibility")
                this.pressure = json.getInt("pressure")
                this.rising = json.getInt("rising")
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    data class Astronomy(private val json: JSONObject) {
        var sunrise: String? = null
            private set
        var sunset: String? = null
            private set

        init {
            try {
                this.sunrise = json.getString("sunrise")
                this.sunset = json.getString("sunset")
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    data class Location(private val json: JSONObject) {
        var city: String? = null
            private set
        var region: String? = null
            private set
        var country: String? = null
            private set

        init {
            try {
                this.city = json.getString("city")
                this.country = json.getString("country")
                this.region = json.getString("region")
            } catch (e: Exception) { e.printStackTrace() }
        }

        override fun toString(): String {
            return "$city/$region - $country"
        }
    }

    data class Units(private val json: JSONObject) {
        var distance: String? = null
            private set
        var pressure: String? = null
            private set
        var speed: String? = null
            private set
        var temperature: String? = null
            private set

        init {
            try {
                this.temperature = json.getString("temperature")
                this.distance = json.getString("distance")
                this.pressure = json.getString("pressure")
                this.speed = json.getString("speed")
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    data class Wind(private val json: JSONObject) {
        var chill: Int = 0
            private set
        var direction: Int = 0
            private set
        var speed: Int = 0
            private set

        init {
            try {
                this.chill = json.getInt("chill")
                this.direction = json.getInt("direction")
                this.speed = json.getInt("speed")
            } catch (e: Exception) {e.printStackTrace() }
        }
    }
}
