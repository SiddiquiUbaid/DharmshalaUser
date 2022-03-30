package com.example.Domain;

import java.lang.reflect.Array;

public class Dharamshala {

    String name,food,parking,others,realprice;
    String address,checkin,checkout;
    String imgUrl1,imgUrl2;
    double rating;


    public Dharamshala(String name, String food, String parking, String others, String realprice, String addrress, String checkin, String checkout, double rating) {
        this.name = name;
        this.food = food;
        this.parking = parking;
        this.others = others;
        this.realprice = realprice;
        this.address = addrress;
        this.checkin = checkin;
        this.checkout = checkout;
        this.rating = rating;

    }

    public String getImgUrl1() {
        return imgUrl1;
    }

    public void setImgUrl1(String imgUrl1) {
        this.imgUrl1 = imgUrl1;
    }

    public String getImgUrl2() {
        return imgUrl2;
    }

    public void setImgUrl2(String imgUrl2) {
        this.imgUrl2 = imgUrl2;
    }

    public Dharamshala() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getRealprice() {
        return realprice;
    }

    public void setRealprice(String realprice) {
        this.realprice = realprice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddrress(String addrress) {
        this.address = addrress;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }




}
