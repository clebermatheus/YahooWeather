package com.github.clebermatheus.yahooweather.models;

import org.json.JSONObject;

/**
 * Classe criada para tratar os dados dos elementos image da API.
 *
 * Created by clebermatheus on 9/24/17.
 */

public class Image {
    private String title, link, url;
    private int width, height;

    public Image(JSONObject json){
        try{
            this.title = json.getString("title");
            this.link = json.getString("link");
            this.url = json.getString("url");
            this.width = json.getInt("width");
            this.height = json.getInt("height");
        }catch (Exception e){e.printStackTrace();}
    }

    public String getTitle() {return title;}
    public String getLink() {return link;}
    public String getUrl() {return url;}
    public int getWidth() {return width;}
    public int getHeight() {return height;}
}
