package com.example.Domain;

import com.example.hotel.R;

public class DrawablesClass {

    public static int getDrawable(String heading){

        int drawableValue = 0;

        switch (heading){
            case "Pool":
                drawableValue =  R.drawable.pool;
                break;

            case "Hot Tub":
                drawableValue =  R.drawable.hot_tub;
                break;

            case "Patio":
                drawableValue =  R.drawable.patio;
                break;

            case "BBQ Grill":
                drawableValue =  R.drawable.bbq_grill;
                break;

            case "Fire Pit":
                drawableValue =  R.drawable.fire_pit;
                break;

            case "Pool Table":
                drawableValue =  R.drawable.pool_table;
                break;

            case "Indoor Fireplace":
                drawableValue =  R.drawable.indoor_fireplace;
                break;

            case "Outdoor Dining Area":
                drawableValue =  R.drawable.outdoor_dining_area;
                break;

            case "Exercise Area":
                drawableValue =  R.drawable.exercise;
                break;

            case "WiFi":
                drawableValue =  R.drawable.wifi;
                break;

            case "TV":
                drawableValue =  R.drawable.tv;
                break;

            case "Kitchen":
                drawableValue =  R.drawable.kitchen;
                break;

            case "Washing Machine":
                drawableValue =  R.drawable.washing_machine;
                break;

            case "Free Parking on Premises":
                drawableValue =  R.drawable.free_parking;
                break;

            case "Paid Parking on Premises":
                drawableValue =  R.drawable.paid_parking;
                break;

            case "Air Conditioning":
                drawableValue =  R.drawable.air_conditioner;
                break;

            case "Dedicated Workspace":
                drawableValue =  R.drawable.dedicated_workspace;
                break;

            case "Outdoor Shower":
                drawableValue =  R.drawable.outdoor_shower;
                break;

            case "Smoke Alarm":
                drawableValue =  R.drawable.smoke_alarm;
                break;

            case "First Aid Kit":
                drawableValue =  R.drawable.first_aid_kit;
                break;

            case "Carbon Monoxide Alarm":
                drawableValue =  R.drawable.carbon_monoxide_alarm;
                break;

            case "Lock on Bedroom Door":
                drawableValue =  R.drawable.lock_on_bedroom_door;
                break;

            case "Fire Extinguisher":
                drawableValue =  R.drawable.fire_extinguisher;
                break;

            case "Stylish":
                drawableValue =  R.drawable.ic_stylish;
                break;

            case "Unique":
                drawableValue =  R.drawable.ic_unique;
                break;

            case "Upscale":
                drawableValue =  R.drawable.ic_upscale;
                break;

            case "Central":
                drawableValue =  R.drawable.ic_central;
                break;

            case "Charming":
                drawableValue =  R.drawable.ic_heart;
                break;

            case "Hip":
                drawableValue =  R.drawable.ic_hip;
                break;

            case "Security Camera(s)":
                drawableValue =  R.drawable.ic_baseline_videocam_24;
                break;

            case "Weapons":
                drawableValue =  R.drawable.ic_baseline_construction_24;
                break;

            case "Dangerous Animals":
                drawableValue =  R.drawable.ic_baseline_pets_24;
                break;
        }

        return drawableValue;
    }


}
