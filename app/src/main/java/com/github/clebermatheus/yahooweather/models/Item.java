package com.github.clebermatheus.yahooweather.models;

import android.location.Location;

import com.github.clebermatheus.yahooweather.utils.enums.WeatherCode;

import org.json.JSONArray;
import org.json.JSONObject;

import static android.location.LocationManager.GPS_PROVIDER;
import static com.github.clebermatheus.yahooweather.utils.enums.WeatherCode.setValue;

/**
 * Classe criada para tratar o item recebido da API.
 *
 * Created by clebermatheus on 9/24/17.
 */

public class Item {
    private Condition condition;
    private Forecast[] forecast;
    private Location location;
    private String title, link, pubDate, description;

    public Item(JSONObject json){
        try{
            this.condition = new Condition(json.getJSONObject("condition"));
            JSONArray arrForecast = json.getJSONArray("forecast");
            this.forecast = new Forecast[arrForecast.length()];
            for(int i=0; i<arrForecast.length(); i++){this.forecast[i] = new Forecast(arrForecast.getJSONObject(i));}
            this.location = new Location(GPS_PROVIDER);
            this.location.setLatitude(json.getDouble("lat"));
            this.location.setLongitude(json.getDouble("long"));
            this.title = json.getString("title");
            this.link = json.getString("link");
            this.pubDate = json.getString("pubDate");
            this.description = json.getString("description");
        }catch (Exception e){e.printStackTrace();}
    }

    public Condition getCondition() {return condition;}
    public Forecast[] getForecast() {return forecast;}
    public Location getLocation() {return location;}
    public String getTitle() {return title;}
    public String getLink() {return link;}
    public String getPubDate() {return pubDate;}
    public String getDescription() {return description;}

    public class Condition {
        private int temp;
        private String date, text;

        private WeatherCode code;

        public Condition(JSONObject json){
            try {
                this.code = setValue(json.getInt("code"));
                this.date = json.getString("date");
                this.temp = json.getInt("temp");
                this.text = json.getString("text");
            } catch (Exception e) {e.printStackTrace();}
        }

        public int getTemp() {return temp;}
        public String getDate() {return date;}
        public String getText() {return text;}
        public WeatherCode getCode() {return code;}
    }

    public class Forecast {
        private int high, low;
        private String date, day, text;
        private WeatherCode code;

        public Forecast(JSONObject json){
            try{
                this.code = setValue(json.getInt("code"));
                this.date = json.getString("date");
                this.day = json.getString("day");
                this.high = json.getInt("high");
                this.low = json.getInt("low");
                this.text = json.getString("text");
            }catch (Exception e){e.printStackTrace();}
        }

        public int getHigh() {return high;}
        public int getLow() {return low;}
        public String getDate() {return date;}
        public String getDay() {return day;}
        public String getText() {return text;}
        public WeatherCode getCode() {return code;}
    }
}
