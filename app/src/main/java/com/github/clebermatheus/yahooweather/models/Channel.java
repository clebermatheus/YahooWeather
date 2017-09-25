package com.github.clebermatheus.yahooweather.models;

import org.json.JSONObject;

/**
 * Classe criada para tratar todos os elementos channel vindo da API.
 *
 * Created by clebermatheus on 9/24/17.
 */

public class Channel {
    private Atmosphere atmosphere;
    private Astronomy astronomy;
    private Image image;
    private Item item;
    private Location location;
    private String title, link, language, description, lastBuildDate;
    private int ttl;
    private Units units;
    private Wind wind;

    public Channel(JSONObject json){
        try{
            this.atmosphere = new Atmosphere(json.getJSONObject("atmosphere"));
            this.astronomy = new Astronomy(json.getJSONObject("astronomy"));
            this.image = new Image(json.getJSONObject("image"));
            this.item = new Item(json.getJSONObject("item"));
            this.location = new Location(json.getJSONObject("location"));
            this.title = json.getString("title");
            this.link = json.getString("link");
            this.language = json.getString("language");
            this.description = json.getString("description");
            this.lastBuildDate = json.getString("lastBuildDate");
            this.ttl = json.getInt("ttl");
            this.units = new Units(json.getJSONObject("units"));
            this.wind = new Wind(json.getJSONObject("wind"));
        }catch (Exception e){e.printStackTrace();}
    }

    public Atmosphere getAtmosphere() {return atmosphere;}
    public Astronomy getAstronomy() {return astronomy;}
    public Image getImage() {return image;}
    public Item getItem() {return item;}
    public Location getLocation() {return location;}
    public String getTitle() {return title;}
    public String getLink() {return link;}
    public String getLanguage() {return language;}
    public String getDescription() {return description;}
    public String getLastBuildDate() {return lastBuildDate;}
    public int getTtl() {return ttl;}
    public Units getUnits() {return units;}
    public Wind getWind() {return wind;}

    public class Atmosphere{
        private int humidity, visibility, pressure, rising;

        public Atmosphere(JSONObject json){
            try{
                this.humidity = json.getInt("humidity");
                this.visibility = json.getInt("visibility");
                this.pressure = json.getInt("pressure");
                this.rising = json.getInt("rising");
            }catch (Exception e){e.printStackTrace();}
        }

        public int getHumidity() {return humidity;}
        public int getVisibility() {return visibility;}
        public int getPressure() {return pressure;}
        public int getRising() {return rising;}
    }
    public class Astronomy{
        private String sunrise, sunset;

        public Astronomy(JSONObject json){
            try{
                this.sunrise = json.getString("sunrise");
                this.sunset = json.getString("sunset");
            }catch (Exception e){e.printStackTrace();}
        }

        public String getSunrise() {return sunrise;}
        public String getSunset() {return sunset;}
    }
    public class Location{
        private String city, region, country;

        public Location(JSONObject json){
            try{
                this.city = json.getString("city");
                this.country = json.getString("country");
                this.region = json.getString("region");
            }catch (Exception e){e.printStackTrace();}
        }

        public String getCity() {return city;}
        public String getRegion() {return region;}
        public String getCountry() {return country;}

        @Override
        public String toString() {return city+"/"+region+" - "+country;}
    }
    public class Units{
        private String distance, pressure, speed, temperature;

        public Units(JSONObject json){
            try{
                this.temperature = json.getString("temperature");
                this.distance = json.getString("distance");
                this.pressure = json.getString("pressure");
                this.speed = json.getString("speed");
            }catch (Exception e){e.printStackTrace();}
        }

        public String getDistance() {return distance;}
        public String getPressure() {return pressure;}
        public String getSpeed() {return speed;}
        public String getTemperature() {return temperature;}
    }
    public class Wind{
        private int chill, direction, speed;

        public Wind(JSONObject json){
            try{
                this.chill = json.getInt("chill");
                this.direction = json.getInt("direction");
                this.speed = json.getInt("speed");
            }catch (Exception e){e.printStackTrace();}
        }

        public int getChill() {return chill;}
        public int getDirection() {return direction;}
        public int getSpeed() {return speed;}
    }
}
