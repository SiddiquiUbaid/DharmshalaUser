package com.example.hotel;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.denzcoskun.imageslider.constants.ScaleTypes; // important

import com.example.Adapter.HotelDescriptionAdapter;
import com.example.Adapter.HotelOffersAdapter;
import com.example.Adapter.HotelQualityAdapter;
import com.example.Adapter.HotelSecurityAdapter;
import com.example.Adapter.ImageSliderAdapter;
import com.example.Domain.DataClass;
import com.example.Domain.HotelData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.SliderView;
import java.util.ArrayList;
import java.util.Calendar;


public class CheckoutDatePicker extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener {

    TextView editCheckin, editCheckout, editRooms;
    TextView txtCheckin, txtCheckout, txtRooms, txtGuests;
    String dateSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_page);

        txtRooms = findViewById(R.id.txtRooms);
        txtGuests = findViewById(R.id.txtGuests);
        txtCheckin = findViewById(R.id.txtCheckin);
        txtCheckout = findViewById(R.id.txtCheckout);



        editCheckout = findViewById(R.id.editCheckout);
        editCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startActivity(new Intent(Booking.this, Description.class));
                showCalendarDialog();

            }
        });


    }

    public void showCalendarDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();

    }


    public String getCheckoutDate() {
        return dateSet;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        int mmonth = month+1;
        String dateSet = ""+dayOfMonth+"/"+mmonth+ "/"+year;
        txtCheckout.setText(dateSet);



    }
}