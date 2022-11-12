package com.example.Fregments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Adapter.CityAdapter;
import com.example.Adapter.FeaturedAdapter;
import com.example.Adapter.TempleAdapter;
import com.example.Domain.BookingOrders;
import com.example.Domain.City;
import com.example.Domain.Dharamshalas;
import com.example.Domain.HotelData;
import com.example.Domain.Temples;
import com.example.hotel.Booking;
import com.example.hotel.BookingConfirmedActivity;
import com.example.hotel.HotelListActivity;
import com.example.hotel.R;
import com.example.hotel.currentLocation;
import com.example.hotel.demo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class HomeFragment extends Fragment implements FeaturedAdapter.OnNoteListener {

    private FirebaseFirestore mstore;

    ArrayList<String> listOfHotelUid;
    String docUid;


    //city view
    private List<City> mCityList;
    private CityAdapter mCityAdapter;
    private RecyclerView mCityrecycler;
    //Featured View
    private ArrayList<HotelData> dharamshalasList;
    private FeaturedAdapter featuredAdapter;
    private RecyclerView featureRecycler;

//nearby temples
    private List<Temples> templesList;
    private TempleAdapter templeAdapter;
    private RecyclerView templeRecycler;
    TextView featured;

    //gps
    ImageView gps;

    // hotel list
    LinearLayout expandHotelList;

    public HomeFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_home, container, false);
        mstore=FirebaseFirestore.getInstance();

        // filter orders of myBookings
        filterOrders();

        //gps current location
        gps = view.findViewById(R.id.gps);
        gps.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       Intent intent = new Intent(getActivity(), currentLocation.class);
                                       startActivity(intent);
                                   }
                               }
        );



        featured=view.findViewById(R.id.featured);
        featured.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), demo.class);
                startActivity(intent);
            }
        });
        templesList=new ArrayList<Temples>();
        templeRecycler= view.findViewById(R.id.templerecycler);
        templeAdapter=new TempleAdapter(getContext(),templesList);
        templeRecycler.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        templeRecycler.setAdapter(templeAdapter);
        mstore.collection("Temples")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {


                                Temples category=document.toObject(Temples.class);


                                templesList.add(category);
                                templeAdapter.notifyDataSetChanged();
                            }
                        } else
                        {
                            Toast.makeText(getContext(), "gg", Toast.LENGTH_SHORT).show();
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                 });





        // explore nearby code
        mCityList=new ArrayList<City>();
        mCityrecycler= view.findViewById(R.id.explore_nearby_recycler);
        mCityAdapter=new CityAdapter(getContext(),mCityList);
        mCityrecycler.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        mCityrecycler.setAdapter(mCityAdapter);

        mstore.collection("City")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {


                                City category=document.toObject(City.class);


                                mCityList.add(category);
                                mCityAdapter.notifyDataSetChanged();
                            }
                        } else
                        {
                            Toast.makeText(getContext(), "gg", Toast.LENGTH_SHORT).show();
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });//explore nearby code ends



        dharamshalasList= new ArrayList<HotelData>();
        listOfHotelUid = new ArrayList<>();

        featureRecycler=view.findViewById(R.id.featured_recycler);
        featuredAdapter=new FeaturedAdapter(getContext(),dharamshalasList, this);
        featureRecycler.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        featureRecycler.setAdapter(featuredAdapter);

        mstore.collection("Dharamsalas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {


                                HotelData dharamshala=document.toObject(HotelData.class);
                                dharamshalasList.add(dharamshala);

                                // getting uid's of all the docs in collection
                                listOfHotelUid.add(document.getId());

                                featuredAdapter.notifyDataSetChanged();
                            }


                        } else
                        {
                            Toast.makeText(getContext(), "gg", Toast.LENGTH_SHORT).show();
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });//explore nearby code ends


        expandHotelList = view.findViewById(R.id.expandHotelList);
        expandHotelList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HotelListActivity.class);
                startActivity(intent);
            }
        });


        return  view;

    }



    public void filterOrders(){



        String  uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ArrayList<BookingOrders> upcomingOrdersList = new ArrayList<>();
        ArrayList<BookingOrders> ongoingOrdersList = new ArrayList<>();
        ArrayList<BookingOrders> completedOrdersList = new ArrayList<>();
        ArrayList<BookingOrders> cancelledOrdersList = new ArrayList<>();

        ArrayList<String> upcomingOrdersUidList = new ArrayList<>();
        ArrayList<String> ongoingOrdersUidList = new ArrayList<>();
        ArrayList<String> completedOrdersUidList = new ArrayList<>();
        ArrayList<String> cancelledOrdersUidList = new ArrayList<>();

        ArrayList<HotelData> upcomingDharamshalaList = new ArrayList<>();
        ArrayList<HotelData> ongoingDharamshalaList = new ArrayList<>();
        ArrayList<HotelData> completedDharamshalaList = new ArrayList<>();
        ArrayList<HotelData> cancelledDharamshalaList = new ArrayList<>();




        FirebaseFirestore dbStore = FirebaseFirestore.getInstance();
        dbStore.collection("UsersData")
                .document(uid)
                .collection("bookingOrders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                        for (QueryDocumentSnapshot document : task.getResult()) {

                            BookingOrders orders = document.toObject(BookingOrders.class);


                            Long checkoutMillis = orders.getMillisCheckout();
                            Long checkinMillis = orders.getMillisCheckin();
                            Long currentMillis = System.currentTimeMillis();
                            String bookingStatus = orders.getPaymentType();

                            // check if booking not cancelled to proceed to filtering.
                            if( !bookingStatus.equals("cancelled") ) {

                                if(currentMillis < checkinMillis){
                                    upcomingOrdersList.add(orders);
                                    UpcomingOrdersFragment.upcomingOrdersList = upcomingOrdersList;

                                    upcomingOrdersUidList.add(document.getId());
                                    UpcomingOrdersFragment.listOfOrderUid = upcomingOrdersUidList;

                                    // getting related dharamshala details
                                    dbStore.collection("Dharamsalas")
                                            .document(orders.getHotelId())
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    HotelData hotelData = task.getResult().toObject(HotelData.class);
                                                    upcomingDharamshalaList.add(hotelData);
                                                    UpcomingOrdersFragment.dharamshalaList = upcomingDharamshalaList;



                                                }
                                            });



                                }

                                if( (currentMillis > checkinMillis)  && (currentMillis < checkoutMillis)){
                                    ongoingOrdersList.add(orders);
                                    OngoingOrdersFragment.ongoingOrdersList = ongoingOrdersList;

                                    ongoingOrdersUidList.add(document.getId());
                                    OngoingOrdersFragment.listOfOrderUid = ongoingOrdersUidList;

                                    // getting related dharamshala details
                                    dbStore.collection("Dharamsalas")
                                            .document(orders.getHotelId())
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    HotelData hotelData = task.getResult().toObject(HotelData.class);
                                                    ongoingDharamshalaList.add(hotelData);
                                                    OngoingOrdersFragment.dharamshalaList = ongoingDharamshalaList;



                                                }
                                            });


                                }


                                if(currentMillis > checkoutMillis){
                                    completedOrdersList.add(orders);
                                    CompletedOrdersFragment.completedOrdersList = completedOrdersList;

                                    completedOrdersUidList.add(document.getId());
                                    CompletedOrdersFragment.listOfOrderUid = completedOrdersUidList;

                                    // getting related dharamshala details
                                    dbStore.collection("Dharamsalas")
                                            .document(orders.getHotelId())
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    HotelData hotelData = task.getResult().toObject(HotelData.class);
                                                    completedDharamshalaList.add(hotelData);
                                                    CompletedOrdersFragment.dharamshalaList = completedDharamshalaList;



                                                }
                                            });



                                }

                            }
                            else{

                                cancelledOrdersList.add(orders);
                                CancelledOrdersFragment.cancelledOrdersList = cancelledOrdersList;

                                cancelledOrdersUidList.add(document.getId());
                                CancelledOrdersFragment.listOfOrderUid = cancelledOrdersUidList;

                                // getting related dharamshala details
                                dbStore.collection("Dharamsalas")
                                        .document(orders.getHotelId())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                HotelData hotelData = task.getResult().toObject(HotelData.class);
                                                cancelledDharamshalaList.add(hotelData);
                                                CancelledOrdersFragment.dharamshalaList = cancelledDharamshalaList;



                                            }
                                        });



                            }









                        }






                    }
                });









    }











    // method for featured recView's item click listener
    @Override
    public void onNoteClick(int position) {
        Boolean alreadyBooked;
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        FirebaseFirestore dbStore = FirebaseFirestore.getInstance();
        dbStore.collection("UsersData")
                .document(uid)
                .collection("bookingOrders")
                .document(listOfHotelUid.get(position))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        BookingOrders orders = document.toObject(BookingOrders.class);


                        Long currentMillis = System.currentTimeMillis();

                        // if hotel already booked and check-out date not passed yet, open  BookingConfirmed Activity with docUid
                        if(document.exists()){

                            Long checkoutMillis = orders.getMillisCheckout();

                            if((checkoutMillis > currentMillis)){
                                Intent intent = new Intent(getContext(), BookingConfirmedActivity.class);
                                intent.putExtra("docUid", listOfHotelUid.get(position));
                                startActivity(intent);

                                //Toast.makeText(getContext(), "already booked", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                //  if not booked, open Booking Activity
                                Intent intent = new Intent(getContext(), Booking.class);
                                intent.putExtra("docUid", listOfHotelUid.get(position));
                                startActivity(intent);
                            }

                        }

                        else{

                            //  if not booked, open Booking Activity
                            Intent intent = new Intent(getContext(), Booking.class);
                            intent.putExtra("docUid", listOfHotelUid.get(position));
                            startActivity(intent);
                        }

                    }
                });






    }
}