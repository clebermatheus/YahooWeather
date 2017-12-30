package com.github.clebermatheus.yahooweather.activities

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.github.clebermatheus.yahooweather.R
import com.github.clebermatheus.yahooweather.models.Channel
import com.github.clebermatheus.yahooweather.models.Item
import com.github.clebermatheus.yahooweather.models.ResultQuery
import com.github.clebermatheus.yahooweather.utils.Util.PREFERENCES
import com.github.clebermatheus.yahooweather.utils.Util.getWeatherCode
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        val resultJSON = sharedPreferences.getString("resultJSON", "")
        Log.i(TAG, resultJSON)
        exibeClimaAtual(resultJSON)
    }

    private fun exibeClimaAtual(json: String){
        val actionBar = supportActionBar
        val gson = Gson()
        val resultado: ResultQuery = gson.fromJson(json, ResultQuery::class.java)
        Log.d(TAG, resultado.toString())
        val channel: Channel = resultado.query!!.results!!.channel ?: Channel()
        val conditionToday = findViewById<TextView>(R.id.conditionToday)

        // Condition Today
        val (code, _, temp, _) = channel.item?.condition ?: Item.Condition()
        val city = channel.location?.city ?: ""
        val temperature = channel.units?.temperature ?: ""
        conditionToday.text = "${temp}º${temperature} em ${city} com tempo ${code.getWeatherCode()}."

        if (actionBar != null) {
            actionBar.title = channel.title
        }else{}
    }

    companion object {
        private val TAG = "MainActivity"
    }
}
