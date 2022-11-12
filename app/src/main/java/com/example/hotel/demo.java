package com.example.hotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.Adapter.HotelListAdapter;
import com.example.Domain.Dharamshala;
import com.example.Domain.HotelData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class demo extends AppCompatActivity {
FirebaseFirestore mstore;
static Context context;
    private HotelListAdapter hotelListAdapter;
    private RecyclerView featureRecycler;
    private static List<HotelData> dharamshalaList;
    static String result ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        dharamshalaList=new ArrayList<HotelData>();
        featureRecycler=findViewById(R.id.re);
       // hotelListAdapter=new HotelListAdapter(getApplicationContext(),dharamshalaList);
        featureRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
        featureRecycler.setAdapter(hotelListAdapter);

        context = getApplicationContext();

mstore=FirebaseFirestore.getInstance();
        queryMethod();


        mstore.collection("Dharamsalas")
                .document("F1uuon80hSd9ry6pzWWBY4D4SvR")
                .collection("bookingOrders")
                .whereEqualTo("userName","Himanshu Sharma")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        result = task.getResult().toString();



                        HotelData dharamshala=document.toObject(HotelData.class);


                        dharamshalaList.add(dharamshala);
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
    public static void queryMethod(){
        Toast.makeText(context, ""+result, Toast.LENGTH_SHORT).show();
        Log.e("result", result);

    }


}