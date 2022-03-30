package com.example.hotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.Adapter.HotelListAdapter;
import com.example.Adapter.MyBookingslListAdapter;
import com.example.Domain.BookingOrders;
import com.example.Domain.HotelData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class MyBookingsActivity extends AppCompatActivity implements MyBookingslListAdapter.OnHotelListener  {

    RecyclerView recyclerViewForMybookingsList;
    String  uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    BookingOrders orders;
    Date currentDate;
    ArrayList<String> listOfOrderUid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);



        recyclerViewForMybookingsList = findViewById(R.id.listrecycler);
        recyclerViewForMybookingsList.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));



        currentDate = new Date();



        ArrayList<BookingOrders> bookingOrdersList = new ArrayList<>();
        listOfOrderUid = new ArrayList<>();
        ArrayList<HotelData> dharamshalaList = new ArrayList<>();


        MyBookingslListAdapter bookingsListAdapter = new MyBookingslListAdapter(getApplicationContext(), bookingOrdersList,
                dharamshalaList, listOfOrderUid, this);
        recyclerViewForMybookingsList.setAdapter(bookingsListAdapter);






        FirebaseFirestore dbStore = FirebaseFirestore.getInstance();
        dbStore.collection("UsersData")
                .document(uid)
                .collection("bookingOrders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (QueryDocumentSnapshot document : task.getResult()) {

                            orders = document.toObject(BookingOrders.class);
                            bookingOrdersList.add(orders);

                            // getting uids of every doc
                            listOfOrderUid.add(orders.getHotelId());



                            //Toast.makeText(getApplicationContext(), "singleDh"+hotelUid, Toast.LENGTH_SHORT).show();
                            FirebaseFirestore mStore = FirebaseFirestore.getInstance();
                            mStore.collection("Dharamsalas")
                                    .document(orders.getHotelId())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            HotelData hotelData = task.getResult().toObject(HotelData.class);
                                            dharamshalaList.add(hotelData);
                                            bookingsListAdapter.notifyDataSetChanged();



                                            }
                                        });




                        }






                    }


                });











        long currentMillis = currentDate.getTime();
        //Toast.makeText(getContext(), "current miilis: "+ currentMillis, Toast.LENGTH_SHORT).show();



    }





    @Override
    public void onHotelClick(int position) {
        Intent intent = new Intent(getApplicationContext(), Booking.class);
        intent.putExtra("docUid", listOfOrderUid.get(position));
        startActivity(intent);

        Toast.makeText(getApplicationContext(), listOfOrderUid.get(position), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onWishListClick(int position) {

    }
}
