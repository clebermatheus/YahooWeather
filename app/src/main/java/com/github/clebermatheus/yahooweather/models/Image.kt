package com.github.clebermatheus.yahooweather.models

import org.json.JSONObject

/**
 * Classe criada para tratar os dados dos elementos image da API.
 *
 * Created by clebermatheus on 9/24/17.
 */

data class Image(private val json: JSONObject) {
    var title: String? = null
        private set
    var link: String? = null
        private set
    var url: String? = null
        private set
    var width: Int = 0
        private set
    var height: Int = 0
        private set

    init {
        try {
            this.title = json.getString("title")
            this.link = json.getString("link")
            this.url = json.getString("url")
            this.width = json.getInt("width")
            this.height = json.getInt("height")
        } catch (e: Exception) {e.printStackTrace() }
    }
}
