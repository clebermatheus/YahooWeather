package com.github.clebermatheus.yahooweather.activities

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.github.clebermatheus.yahooweather.R
import com.github.clebermatheus.yahooweather.models.Channel
import com.github.clebermatheus.yahooweather.utils.Util.PREFERENCES
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        val resultJSON = sharedPreferences.getString("resultJSON", "")
        Log.i(TAG, resultJSON)
        exibeClimaAtual(resultJSON)
    }

    private fun exibeClimaAtual(jsonString: String) {
        try {
            val obj = JSONObject(jsonString)
            val results = obj.getJSONObject("query").getJSONObject("results")
            val channel = Channel(results.getJSONObject("channel"))
            val item = channel.item
            val actionBar = supportActionBar
            val conditionToday = findViewById<TextView>(R.id.conditionToday)
            val condition = item!!.condition
            val location = channel.location
            conditionToday.text = "${condition!!.temp} º${channel.units!!.temperature} em ${location!!.city} com tempo ${condition.code}."
            if (actionBar != null) {
                actionBar.title = channel.title
            }
        } catch (e: Exception) { e.printStackTrace() }
    }

    companion object {
        private val TAG = "MainActivity"
    }
}
