package com.example.hotel;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.denzcoskun.imageslider.constants.ScaleTypes; // important

import com.example.Adapter.EditRoomsActivity;
import com.example.Adapter.HotelDescriptionAdapter;
import com.example.Adapter.HotelOffersAdapter;
import com.example.Adapter.HotelQualityAdapter;
import com.example.Adapter.HotelSecurityAdapter;
import com.example.Adapter.ImageSliderAdapter;
import com.example.Domain.BookingOrders;
import com.example.Domain.DataClass;
import com.example.Domain.HotelData;
import com.example.Domain.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.SliderView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Booking<hotelImageSlider> extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener {
    TextView Showmore, buttonBookNow;
    TextView editCheckin, editCheckout, editRooms;
    TextView txtCheckin, txtCheckout, txtRooms, txtGuests, txtNights;
    private ProgressBar bookingProgress;
    String numOfRooms, numOfGuests, totalNights;
    int viewId;
    long daysOfCheckin = 1 ;
    long differenceInDays = 0 ;




    ImageView map;
    SliderView imgSlider;
    HotelData hotelData;
    HotelData singleHotel;
    ArrayList<HotelData> hotelImageListForSlider = new ArrayList<>();
    String editRoomsList, refAmount;
    String docUid;
    String refCheckinDate, refCheckoutDate;

    public static long totalAmount ;
    public  long defaultAmount = 0;



    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        Booking.totalAmount = totalAmount;
    }

    TextView hotelName, hotelRent;
    RecyclerView recyclerViewForPlaceDescriptionList, recyclerViewForPlaceOffers, recyclerViewForPlaceQualities, recyclerViewForPlaceSecurity ;


    ArrayList<String> roomImageListForSlider = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_page);

        txtRooms = findViewById(R.id.txtRooms);
        txtGuests = findViewById(R.id.txtGuests);
        txtCheckin = findViewById(R.id.txtCheckin);
        txtCheckout = findViewById(R.id.txtCheckout);
        txtNights = findViewById(R.id.txtNights);


        hotelName = findViewById(R.id.hotelName);
        hotelRent = findViewById(R.id.hotelRent);

        bookingProgress = findViewById(R.id.bookingProgress);




        recyclerViewForPlaceDescriptionList = findViewById(R.id.recylerViewForPlaceDescription);
        recyclerViewForPlaceDescriptionList.setLayoutManager(new LinearLayoutManager(Booking.this));

        recyclerViewForPlaceOffers = findViewById(R.id.recylerViewForPlaceOffers);
        recyclerViewForPlaceOffers.setLayoutManager(new GridLayoutManager(Booking.this, 2));

        recyclerViewForPlaceQualities = findViewById(R.id.recylerViewForPlaceQuality);
        recyclerViewForPlaceQualities.setLayoutManager(new GridLayoutManager(Booking.this, 2));

        recyclerViewForPlaceSecurity = findViewById(R.id.recylerViewForPlaceSecurity);
        recyclerViewForPlaceSecurity.setLayoutManager(new LinearLayoutManager(Booking.this));







        ImageSlider imgSlider = findViewById(R.id.imgSlider);

        // getting local data from editrooms activity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            docUid = bundle.getString("docUid");
        }
        else{
            docUid = "nothing";
        }

        editRoomsList = bundle.getString("editRoomsList");
        numOfRooms = bundle.getString("numOfRooms");
        numOfGuests = bundle.getString("numOfGuests");
        totalNights = bundle.getString("totalNights");
        refCheckinDate = bundle.getString("refCheckinDate");
        refCheckoutDate = bundle.getString("refCheckoutDate");
        millisOfCheckin = bundle.getLong("millisOfCheckin");
        millisOfCheckout = bundle.getLong("millisOfCheckout");












        ArrayList<SlideModel> imageList = new ArrayList<>();
        singleHotel = null;

        // getting hotel data from firebase
        FirebaseFirestore mstore = FirebaseFirestore.getInstance();
        mstore.collection("Dharamsalas")
                .document(docUid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.isSuccessful()){
                            hotelData = task.getResult().toObject(HotelData.class);
                            singleHotel = task.getResult().toObject(HotelData.class);
                            defaultAmount = Long.parseLong(singleHotel.getPlaceRent());
                            refAmount = singleHotel.getPlaceRent();

                            //setting hotel name and rent
                            hotelName.setText(hotelData.getPlaceName());


                            if( editRoomsList != null){
                                refAmount = editRoomsList;

                                hotelRent.setText(editRoomsList);
                                totalAmount = Long.parseLong(hotelRent.getText().toString());

                                txtGuests.setText(numOfGuests);
                                txtRooms.setText(numOfRooms);
                                txtNights.setText(totalNights);

                                txtCheckin.setText(refCheckinDate);
                                txtCheckout.setText(refCheckoutDate);

                            }
                            else {
                                hotelRent.setText(hotelData.getPlaceRent());

                            }



                            // adding hotel and cover images in the list
                            for(int i = 0; i <  hotelData.getHotelImages().size(); i++){

                                imageList.add(new SlideModel(hotelData.getHotelImages().get(i), ScaleTypes.FIT));
                            }

                            // adding hotel room images in the list
                            for(int i = 0; i <  hotelData.getHotelRoomImages().size(); i++){

                                imageList.add(new SlideModel(hotelData.getHotelRoomImages().get(i), ScaleTypes.FIT));
                            }

                            // setting image list on slider
                            imgSlider.setImageList(imageList, ScaleTypes.FIT);



                            //hotel description fetching
                            ArrayList<DataClass> placeDescriptionList = hotelData.getPlaceDescriptionList();
                            HotelDescriptionAdapter descriptionAdapter = new HotelDescriptionAdapter(placeDescriptionList);
                            recyclerViewForPlaceDescriptionList.setAdapter(descriptionAdapter);

                            //hotel offers fetching
                            ArrayList<DataClass> placeOffersList = hotelData.getPlaceOffersList();
                            HotelOffersAdapter offersAdapter = new HotelOffersAdapter(placeOffersList);
                            recyclerViewForPlaceOffers.setAdapter(offersAdapter);

                            // code for fetching quality list
                            ArrayList<String> placeQualityList = hotelData.getPlaceQualityList();
                            HotelQualityAdapter qualityAdapter = new HotelQualityAdapter(placeQualityList);
                            recyclerViewForPlaceQualities.setAdapter(qualityAdapter);

                            // code for fetching security list
                            ArrayList<String> placeSecurityList = hotelData.getPlaceSecurityList();
                            HotelSecurityAdapter securityAdapter = new HotelSecurityAdapter(placeSecurityList);
                            recyclerViewForPlaceSecurity.setAdapter(securityAdapter);


                        }
                        else{

                            Toast.makeText(getApplicationContext(), "Error getting data", Toast.LENGTH_SHORT).show();
                            Log.w("TAG", "Error getting data", task.getException());
                        }

                    }
                });




//show more description activity

        Showmore=findViewById(R.id.show_more);
        Showmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Booking.this, Description.class));

            }
        });



        buttonBookNow = findViewById(R.id.btnBookNow);
        buttonBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                confirmBooking();



            }
        });



        editCheckin = findViewById(R.id.editCheckin);
        editCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(Booking.this, Description.class));
                viewId = R.id.editCheckin;
                showCalendarDialog();



            }
        });

        editCheckout = findViewById(R.id.editCheckout);
        editCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(Booking.this, Description.class));
                viewId = R.id.editCheckout;
                showCalendarDialog();

            }
        });

        editRooms = findViewById(R.id.editRooms);
        editRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("singleHotel", singleHotel.getPlaceRent());
                Log.e("txtBookingAmount", hotelRent.getText().toString());

                Intent intent = new Intent(getApplicationContext(), EditRoomsActivity.class);
                intent.putExtra("docUid", docUid);

                intent.putExtra("defaultAmount", hotelRent.getText().toString());

                intent.putExtra("numOfGuests", txtGuests.getText().toString());
                intent.putExtra("numOfRooms", txtRooms.getText().toString());
                intent.putExtra("totalNights", txtNights.getText().toString());

                intent.putExtra("tempCheckinDate", dateSetin);
                intent.putExtra("tempCheckoutDate", dateSetout);

                intent.putExtra("millisOfCheckin", millisOfCheckin);
                intent.putExtra("millisOfCheckout", millisOfCheckout);


                startActivity(intent);


            }
        });

/*
       map=findViewById(R.id.map);
       map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Booking.this, MapsActivity.class));
            }
        }); */
    }




    private void showCalendarDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                );
        datePickerDialog.show();

    }




    String dateSetin =  "Today";
    String dateSetout = "Tomorrow";
    Long millisOfCheckin = System.currentTimeMillis();
    Long millisOfCheckout = System.currentTimeMillis() + 86400000;

    Boolean checkInUpdated = false;
    Boolean checkOutUpdated = false;


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        int mmonth = month+1;




        switch (viewId){
            case R.id.editCheckin:
                dateSetin = dayOfMonth+"-"+mmonth+ "-"+year;
                txtCheckin.setText(dateSetin);
                checkInUpdated = true;

                break;

            case R.id.editCheckout:
                dateSetout = dayOfMonth+"-"+mmonth+ "-"+year;
                txtCheckout.setText(dateSetout);
                checkOutUpdated = true;

                //Toast.makeText(getApplicationContext(), "diff days: "+differenceInDays, Toast.LENGTH_SHORT).show();
                break;

        }

        if(checkInUpdated && checkOutUpdated){
            updateTotalAmount();
        }
        else{
            //Toast.makeText(getApplicationContext(), ""+checkInUpdated+ " "+checkOutUpdated, Toast.LENGTH_SHORT).show();
        }


    }



    public long getDifferenceInDays(String d1, String d2){
      //  long differenceInDays = 0 ;


        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date1 = formatter.parse(d1);
            Date date2 = formatter.parse(d2);

            millisOfCheckin = date1.getTime();
            millisOfCheckout = date2.getTime();

            long differenceInTime = date2.getTime() - date1.getTime();
            differenceInDays = (differenceInTime / (1000*60*60*24)) % 365;


        } catch (ParseException e) {

            Toast.makeText(getApplicationContext(), "error: ", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }

        return differenceInDays;


    }


    public void updateTotalAmount(){

        daysOfCheckin = getDifferenceInDays(dateSetin, dateSetout);
        long referenceAccumulatedAmount =  Long.parseLong(refAmount);

        totalAmount = referenceAccumulatedAmount * daysOfCheckin;
        hotelRent.setText(""+totalAmount);
        txtNights.setText(daysOfCheckin+"N");

    }

    public void confirmBooking(){

        // starting progress
        bookingProgress.setVisibility(View.VISIBLE);
        buttonBookNow.setBackground(getResources().getDrawable(R.drawable.booknow_pressed));

        // profile data fetching
        FirebaseDatabase db;
        DatabaseReference node;
        FirebaseFirestore mstore;
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //final User[] user = new User[1];


        // creating object to get instance of whole database
        db = FirebaseDatabase.getInstance();
        mstore=FirebaseFirestore.getInstance();

        // getting reference of a node from database
        node = db.getReference().child("Users").child(uid);
        node.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String uname = user.getName();
                String uphone = user.getNumber();

                String in = txtCheckin.getText().toString();
                String out = txtCheckout.getText().toString();
                String amount = hotelRent.getText().toString();
                String guests = txtGuests.getText().toString();
                String rooms = txtRooms.getText().toString();
                String totalNights = txtNights.getText().toString();


                BookingOrders bookingOrders = new BookingOrders(uid, docUid, in, out, "atHotel",
                        amount, totalNights, rooms,
                        guests, uname, uphone, millisOfCheckin, millisOfCheckout);

                // updating at admin side
                mstore.collection("Dharamsalas")
                        .document(docUid)
                        .collection("bookingOrders")
                        .document(uid)
                        .set(bookingOrders)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                // end the progress
                                bookingProgress.setVisibility(View.GONE);
                                buttonBookNow.setBackground(getResources().getDrawable(R.drawable.booknow));

                                //open the booking confirmed activity
                                Toast.makeText(getApplicationContext(), "Booking Confirmed", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), BookingConfirmedActivity.class);
                                intent.putExtra("docUid", docUid);
                                startActivity(intent);

                                finish();






                            }


                        });

                // updating at user side
                mstore.collection("UsersData")
                        .document(uid)
                        .collection("bookingOrders")
                        .document(docUid)
                        .set(bookingOrders)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {


                            }


                        });



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

}