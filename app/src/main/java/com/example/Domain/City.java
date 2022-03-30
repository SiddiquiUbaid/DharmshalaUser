package com.example.Domain;

public class City {

    String cityName,imgurl;

    public City(String cityName, String imgurl) {
        this.cityName = cityName;
        this.imgurl = imgurl;
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
