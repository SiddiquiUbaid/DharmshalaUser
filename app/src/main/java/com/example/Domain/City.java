package com.example.Domain;

import java.util.ArrayList;

public class City {

    ArrayList<String> dharmshalas;
    String cityName,imgurl;

    public ArrayList<String> getDharmshalas() {
        return dharmshalas;
    }

    public void setDharmshalas(ArrayList<String> dharmshalas) {
        this.dharmshalas = dharmshalas;
    }

    public City(String cityName, String imgurl, ArrayList<String> dharmshalas) {
        this.cityName = cityName;
        this.imgurl = imgurl;
        this.dharmshalas = dharmshalas;
    }

    public City() {
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
