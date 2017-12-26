package com.github.clebermatheus.yahooweather.models

import android.location.Location

import com.github.clebermatheus.yahooweather.utils.enums.WeatherCode

import org.json.JSONObject

import android.location.LocationManager.GPS_PROVIDER

/**
 * Classe criada para tratar o item recebido da API.
 *
 * Created by clebermatheus on 9/24/17.
 */

data class Item(private val json: JSONObject) {
    var condition: Condition? = null
        private set
    lateinit var forecast: Array<Forecast?>
        private set
    var location: Location? = null
        private set
    var title: String? = null
        private set
    var link: String? = null
        private set
    var pubDate: String? = null
        private set
    var description: String? = null
        private set

    init {
        try {
            this.condition = Condition(json.getJSONObject("condition"))
            val arrForecast = json.getJSONArray("forecast")
            this.forecast = arrayOfNulls(size = arrForecast.length())
            for (i in 0..arrForecast.length()) {
                this.forecast[i] = Forecast(arrForecast.getJSONObject(i))
            }
            this.location = Location(GPS_PROVIDER)
            this.location!!.latitude = json.getDouble("lat")
            this.location!!.longitude = json.getDouble("long")
            this.title = json.getString("title")
            this.link = json.getString("link")
            this.pubDate = json.getString("pubDate")
            this.description = json.getString("description")
        } catch (e: Exception) {e.printStackTrace() }
    }

    data class Condition(private val json: JSONObject) {
        var temp: Int = 0
            private set
        var date: String? = null
            private set
        var text: String? = null
            private set

        var code: WeatherCode? = null
            private set

        init {
            try {
                this.code = WeatherCode.setValue(json.getInt("code"))
                this.date = json.getString("date")
                this.temp = json.getInt("temp")
                this.text = json.getString("text")
            } catch (e: Exception) {e.printStackTrace()}
        }
    }

    data class Forecast(private val json: JSONObject) {
        var high: Int = 0
            private set
        var low: Int = 0
            private set
        var date: String? = null
            private set
        var day: String? = null
            private set
        var text: String? = null
            private set
        var code: WeatherCode? = null
            private set

        init {
            try {
                this.code = WeatherCode.setValue(json.getInt("code"))
                this.date = json.getString("date")
                this.day = json.getString("day")
                this.high = json.getInt("high")
                this.low = json.getInt("low")
                this.text = json.getString("text")
            } catch (e: Exception) {e.printStackTrace()}
        }
    }
}
