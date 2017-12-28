package com.github.clebermatheus.yahooweather.models

/**
 * Classe auxiliar para armazenar os resultados obtidos do YQL
 * Created by clebermatheus on 28/12/17.
 */
data class ResultQuery(val query: Query? = null){
    data class Query(
            val count: Int = 0,
            val created: String = "",
            val lang: String = "",
            val results: Results? = null
    ){
        data class Results(val channel: Channel? = null)
    }
}