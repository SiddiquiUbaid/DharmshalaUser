package com.example.Domain;

import java.util.ArrayList;
import java.util.HashMap;

public class HotelData {

    String PlaceName;
    String PlaceRent;
    ArrayList<String> PlaceQualityList, PlaceSecurityList;
    ArrayList<String> HotelImages, HotelRoomImages;
    ArrayList<String> numberOfGuestsList;

    public ArrayList<DataClass> getPlaceDescriptionList() {
        return PlaceDescriptionList;
    }

    public void setPlaceDescriptionList(ArrayList<DataClass> placeDescriptionList) {
        PlaceDescriptionList = placeDescriptionList;
    }

    public ArrayList<DataClass> getPlaceOffersList() {
        return PlaceOffersList;
    }

    public void setPlaceOffersList(ArrayList<DataClass> placeOffersList) {
        PlaceOffersList = placeOffersList;
    }

    ArrayList<DataClass> PlaceDescriptionList, PlaceOffersList;

    public ArrayList<String> getNumberOfGuestsList() {
        return numberOfGuestsList;
    }

    public void setNumberOfGuestsList(ArrayList<String> numberOfGuestsList) {
        this.numberOfGuestsList = numberOfGuestsList;
    }

    public ArrayList<String> getHotelImages() {
        return HotelImages;
    }

    public void setHotelImages(ArrayList<String> hotelImages) {
        HotelImages = hotelImages;
    }

    public ArrayList<String> getHotelRoomImages() {
        return HotelRoomImages;
    }

    public void setHotelRoomImages(ArrayList<String> hotelRoomImages) {
        HotelRoomImages = hotelRoomImages;
    }

    public ArrayList<String> getPlaceQualityList() {
        return PlaceQualityList;
    }

    public void setPlaceQualityList(ArrayList<String> placeQualityList) {
        PlaceQualityList = placeQualityList;
    }

    public ArrayList<String> getPlaceSecurityList() {
        return PlaceSecurityList;
    }

    public void setPlaceSecurityList(ArrayList<String> placeSecurityList) {
        PlaceSecurityList = placeSecurityList;
    }

    public HotelData() {
    }

    public String getPlaceName() {
        return PlaceName;
    }

    public void setPlaceName(String placeName) {
        PlaceName = placeName;
    }

    public String getPlaceRent() {
        return PlaceRent;
    }

    public void setPlaceRent(String placeRent) {
        PlaceRent = placeRent;
    }
}