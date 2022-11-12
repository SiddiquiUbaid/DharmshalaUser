package com.example.Fregments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.Adapter.HotelListAdapter;
import com.example.Domain.DataClass;
import com.example.Domain.HotelData;
import com.example.hotel.R;
import com.example.hotel.demo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    FirebaseFirestore mstore;
    static Context context;
    private HotelListAdapter hotelListAdapter;
    private RecyclerView featureRecycler;
    private static List<HotelData> dharamshalaList;
    static String result ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_notification, container, false);
        // Inflate the layout for this fragment

        dharamshalaList=new ArrayList<HotelData>();
        featureRecycler=view.findViewById(R.id.re);
        // hotelListAdapter=new HotelListAdapter(getApplicationContext(),dharamshalaList);
        featureRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        featureRecycler.setAdapter(hotelListAdapter);

        context = getContext();

        mstore= FirebaseFirestore.getInstance();
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


                        HotelData dharamshala=document.toObject(HotelData.class);
                        Toast.makeText(getContext(), "Hotel: "+dharamshala.getPlaceName(), Toast.LENGTH_SHORT).show();


                        dharamshalaList.add(dharamshala);
                        hotelListAdapter.notifyDataSetChanged();
                    }
                } else
                {
                    Toast.makeText(getContext(), "gg", Toast.LENGTH_SHORT).show();
                    Log.w("TAG", "Error getting documents.", task.getException());
                }
            }
        });

        return view;



    }
    public static void queryMethod(){
        Toast.makeText(context, ""+result, Toast.LENGTH_SHORT).show();
        Log.e("result", result);

    }


}
