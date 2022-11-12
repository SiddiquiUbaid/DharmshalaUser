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
import android.widget.TextView;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ExpandCityActivity extends AppCompatActivity implements HotelListAdapter.OnHotelListener {

    private List<HotelData> dharamshalaList;
    ArrayList<String> listOfHotelUid;
    String cityName;

    ImageView backimg;
    private HotelListAdapter hotelListAdapter;
    private RecyclerView featureRecycler;
    private FirebaseFirestore mstore;


    TextView cityNameInExpandCity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_city);

        initializeFields();
        dharamshalaList=new ArrayList<>();



        mstore=FirebaseFirestore.getInstance();





        for(String id: listOfHotelUid){

            mstore.collection("Dharamsalas")
                    .document(id)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            HotelData dharamshala=task.getResult().toObject(HotelData.class);
                            dharamshalaList.add(dharamshala);
                            Toast.makeText(getApplicationContext(), ""+id, Toast.LENGTH_SHORT).show();

                            hotelListAdapter.notifyDataSetChanged();

                        }
                    });



            featureRecycler=findViewById(R.id.listrecycler);
            hotelListAdapter=new HotelListAdapter(getApplicationContext(),dharamshalaList, listOfHotelUid, this);
            featureRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
            featureRecycler.setAdapter(hotelListAdapter);




        }




        backimg=findViewById(R.id.backimage);
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

    public void initializeFields(){



        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            listOfHotelUid = new ArrayList<>();
            listOfHotelUid = bundle.getStringArrayList("listOfHotelId");

            cityName = bundle.getString("cityName");
            cityNameInExpandCity = findViewById(R.id.cityNameInExpandCity);
            cityNameInExpandCity.setText("Dharamshalas in "+ cityName);



        }
        else{
            Toast.makeText(getApplicationContext(), "Null value", Toast.LENGTH_SHORT).show();
        }
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