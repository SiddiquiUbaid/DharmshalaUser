package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class SummeryActivity extends AppCompatActivity {
ImageView dharamshalaImage;
TextView priceText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summery);
        dharamshalaImage=findViewById(R.id.top_image);
        priceText=findViewById(R.id.price);


        Glide.with(getApplicationContext()).load("https://media-cdn.tripadvisor.com/media/photo-s/16/1a/ea/54/hotel-presidente-4s.jpg").into(dharamshalaImage);


    }
}