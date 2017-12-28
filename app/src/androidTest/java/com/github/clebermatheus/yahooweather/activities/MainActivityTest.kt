package com.github.clebermatheus.yahooweather.activities

import android.content.Context.MODE_PRIVATE
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.widget.TextView
import com.github.clebermatheus.yahooweather.R
import com.github.clebermatheus.yahooweather.models.ResultQuery
import com.github.clebermatheus.yahooweather.utils.Util
import com.google.gson.Gson
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Arquivo de testes criado exclusivamente para a MainActivity
 *
 * Created by clebermatheus on 9/25/17.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {
    @Rule
    var activity = ActivityTestRule(MainActivity::class.java)

    @Test
    @Throws(Exception::class)
    fun onCreate() {
        val sharedPreferences = activity.activity.getSharedPreferences(Util.PREFERENCES, MODE_PRIVATE)
        assertNotNull("SharedPreferences nula", sharedPreferences)
        val resultJSON = sharedPreferences.getString("resultJSON", "")
        assertFalse("Result vazio", resultJSON!!.isEmpty())
        exibeClimaAtual(resultJSON)
    }

    private fun exibeClimaAtual(jsonString: String) {
        val gson = Gson()
        val resultado: ResultQuery = gson.fromJson(jsonString, ResultQuery::class.java)
        assertNotNull("Elemento results não encontrado!", resultado.query!!.results)
        val channel = resultado.query!!.results!!.channel
        assertNotNull("channel não encontrado!", resultado.query!!.results!!.channel)
        assertNotNull("channel inválido!", channel)
        val item = channel!!.item
        assertNotNull("item inválido", item)
        val actionBar = activity.activity.supportActionBar
        assertNotNull("actionBar não inicializada", actionBar)
        val conditionToday = activity.activity.findViewById<TextView>(R.id.conditionToday)
        assertNotNull("conditionToday inválido", conditionToday)
        val condition = item!!.condition
        assertNotNull("condition do item inválido", condition)
        val location = channel.location
        assertNotNull("location não encontrado", location)
        conditionToday.text = "${condition!!.temp} º${channel.units!!.temperature} em ${location!!.city} com tempo ${condition.code}."
        assertFalse("conditionToday vazio", conditionToday.text.isEmpty())
        actionBar?.title = channel.title
    }
}