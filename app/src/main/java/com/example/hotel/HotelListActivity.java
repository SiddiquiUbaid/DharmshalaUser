package com.example.hotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.Adapter.FeaturedAdapter;
import com.example.Adapter.HotelListAdapter;
import com.example.Domain.Dharamshala;
import com.example.Domain.Dharamshalas;
import com.example.Domain.HotelData;
import com.example.Fregments.HomeFragment;
import com.example.Fregments.WishlistFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HotelListActivity extends AppCompatActivity implements HotelListAdapter.OnHotelListener {

    private List<HotelData> dharamshalaList;
    ArrayList<String> listOfHotelUid;
    Boolean addToWishList = false;
    ImageView backimg, wishbook;
    private HotelListAdapter hotelListAdapter;
    private RecyclerView featureRecycler;
    private FirebaseFirestore mstore;
    HomeActivity homeActivity;
    HomeFragment homeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list);

        homeFragment=new HomeFragment();
        homeFragment.setRetainInstance(true);



        wishbook = findViewById(R.id.wishbook);
        wishbook.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
               Intent intent = new Intent(HotelListActivity.this, WishListActivity.class);
               startActivity(intent);
            }
        });


        backimg=findViewById(R.id.backimage);
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


mstore=FirebaseFirestore.getInstance();

        listOfHotelUid = new ArrayList<>();
        dharamshalaList=new ArrayList<HotelData>();
        featureRecycler=findViewById(R.id.listrecycler);
        hotelListAdapter=new HotelListAdapter(getApplicationContext(),dharamshalaList, listOfHotelUid, this);
        featureRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
        featureRecycler.setAdapter(hotelListAdapter);

        mstore.collection("Dharamsalas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {


                                HotelData dharamshala=document.toObject(HotelData.class);


                                dharamshalaList.add(dharamshala);

                                // getting uid's of all the docs in collection
                                listOfHotelUid.add(document.getId());

                                hotelListAdapter.notifyDataSetChanged();
                            }
                        } else
                        {
                            Toast.makeText(getApplicationContext(), "gg", Toast.LENGTH_SHORT).show();
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });





    }

    private void replace(Fragment fragment) {
        homeActivity = new HomeActivity();
        homeActivity.recreate();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onHotelClick(int position) {
        // sending docUid to Booking Activity
        Intent intent = new Intent(getApplicationContext(), Booking.class);
        intent.putExtra("docUid", listOfHotelUid.get(position));
        startActivity(intent);


    }

    @Override
    public void onWishListClick(int position) {
        // not implemented for this activity

    }

}