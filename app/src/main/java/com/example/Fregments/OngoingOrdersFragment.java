package com.example.Fregments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Adapter.MyBookingslListAdapter;
import com.example.Domain.BookingOrders;
import com.example.Domain.HotelData;
import com.example.hotel.Booking;
import com.example.hotel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class OngoingOrdersFragment extends Fragment implements MyBookingslListAdapter.OnHotelListener  {

    RecyclerView recyclerViewForMybookingsList;
    String  uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public static ArrayList<BookingOrders> ongoingOrdersList = new ArrayList<>();
    public static ArrayList<String> listOfOrderUid = new ArrayList<>();
    public static ArrayList<HotelData> dharamshalaList = new ArrayList<>();




    public OngoingOrdersFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_my_bookings, container, false);
        View viewForEmpty = inflater.inflate(R.layout.empty_list_placeholder, container, false);



        recyclerViewForMybookingsList = view.findViewById(R.id.listrecycler);
        recyclerViewForMybookingsList.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));



        MyBookingslListAdapter bookingsListAdapter = new MyBookingslListAdapter(getContext(), ongoingOrdersList,
                dharamshalaList, listOfOrderUid, this);
        recyclerViewForMybookingsList.setAdapter(bookingsListAdapter);
        bookingsListAdapter.notifyDataSetChanged();






        // Inflate the layout according to condition
        if(ongoingOrdersList.isEmpty()){
            return viewForEmpty;
        }
        else {
            return view;
        }

    }





    @Override
    public void onHotelClick(int position) {
        Intent intent = new Intent(getContext(), Booking.class);
        intent.putExtra("docUid", listOfOrderUid.get(position));
        startActivity(intent);

        //Toast.makeText(getContext(), listOfUpcomingOrderUid.get(position), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onWishListClick(int position) {

    }
}
