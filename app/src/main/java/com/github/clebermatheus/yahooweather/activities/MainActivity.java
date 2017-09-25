package com.github.clebermatheus.yahooweather.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.github.clebermatheus.yahooweather.R;
import com.github.clebermatheus.yahooweather.models.Channel;
import com.github.clebermatheus.yahooweather.models.Item;

import org.json.JSONObject;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.N;
import static android.text.Html.FROM_HTML_OPTION_USE_CSS_COLORS;
import static android.text.Html.fromHtml;
import static com.github.clebermatheus.yahooweather.utils.Util.PREFERENCES;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        String resultJSON = sharedPreferences.getString("resultJSON", "");
        Log.i(TAG, resultJSON);
        exibeClimaAtual(resultJSON);
    }

    public void exibeClimaAtual(String jsonString){
        try{
            JSONObject obj = new JSONObject(jsonString);
            JSONObject results = obj.getJSONObject("query").getJSONObject("results");
            Channel channel = new Channel(results.getJSONObject("channel"));
            Item item = channel.getItem();
            ActionBar actionBar = getSupportActionBar();
            TextView conditionToday = (TextView) findViewById(R.id.conditionToday);
            Item.Condition condition = item.getCondition();
            Channel.Location location = channel.getLocation();
            conditionToday.setText(condition.getTemp()+" º"+channel.getUnits().getTemperature()+" em "+
                    location.getCity()+" com tempo "+condition.getCode()+".");
            if (actionBar != null) {actionBar.setTitle(channel.getTitle());}
        }catch(Exception e){e.printStackTrace();}
    }
}
