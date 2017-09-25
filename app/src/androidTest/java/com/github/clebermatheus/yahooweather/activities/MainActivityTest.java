package com.github.clebermatheus.yahooweather.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.ActionBar;
import android.widget.TextView;

import com.github.clebermatheus.yahooweather.R;
import com.github.clebermatheus.yahooweather.models.Channel;
import com.github.clebermatheus.yahooweather.models.Item;

import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.content.Context.MODE_PRIVATE;
import static com.github.clebermatheus.yahooweather.utils.Util.PREFERENCES;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Arquivo de testes criado exclusivamente para a MainActivity
 *
 * Created by clebermatheus on 9/25/17.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activity = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void onCreate() throws Exception {
        SharedPreferences sharedPreferences = activity.getActivity().getSharedPreferences
                (PREFERENCES, MODE_PRIVATE);
        assertNotNull("SharedPreferences nula", sharedPreferences);
        String resultJSON = sharedPreferences.getString("resultJSON", "");
        assertFalse("Result vazio", resultJSON.isEmpty());
        exibeClimaAtual(resultJSON);
    }

    private void exibeClimaAtual(String jsonString) {
        try{
            JSONObject obj = new JSONObject(jsonString);
            assertNotNull("JSON inválido", obj);
            JSONObject results = obj.getJSONObject("query").getJSONObject("results");
            assertNotNull("Elemento results não eonctrado", results);
            Channel channel = new Channel(results.getJSONObject("channel"));
            assertNotNull("channel não encontrado!", results.getJSONObject("channel"));
            assertNotNull("channel inválido!", channel);
            Item item = channel.getItem();
            assertNotNull("item inválido", item);
            ActionBar actionBar = activity.getActivity().getSupportActionBar();
            assertNotNull("actionBar não inicializada", actionBar);
            TextView conditionToday = (TextView) activity.getActivity().findViewById(R.id
                    .conditionToday);
            assertNotNull("conditionToaday inálido", conditionToday);
            Item.Condition condition = item.getCondition();
            assertNotNull("condition do item inválido", condition);
            Channel.Location location = channel.getLocation();
            assertNotNull("location não encontrado", location);
            conditionToday.setText(condition.getTemp()+" º"+channel.getUnits().getTemperature()+" em "+
                    location.getCity()+" com tempo "+condition.getCode()+".");
            assertFalse("conditionToday vazio", conditionToday.getText().length() == 0);
            if (actionBar != null) {actionBar.setTitle(channel.getTitle());}
        }catch(Exception e){e.printStackTrace();}
    }
}