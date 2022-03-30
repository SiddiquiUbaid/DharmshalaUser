package com.example.Fregments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.Adapter.HotelListAdapter;
import com.example.Adapter.WishListAdapter;
import com.example.Domain.HotelData;
import com.example.Domain.User;
import com.example.hotel.Booking;
import com.example.hotel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class WishlistFragment extends Fragment implements  WishListAdapter.OnHotelListener {

    private List<HotelData> dharamshalaList;
    ArrayList<String> wishlistUidOfHotel, dharamshalaUid;
    ImageView backimg, removeWishList;
    private WishListAdapter hotelListAdapter;
    private RecyclerView featureRecycler;
    private FirebaseFirestore mstore;

    String uid;
    FirebaseAuth fAuth;


    public WishlistFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        removeWishList = view.findViewById(R.id.removeWishList);
       // backimg=view.findViewById(R.id.backimage);

/*        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

 */







        fAuth = FirebaseAuth.getInstance();
        uid = fAuth.getUid();
        mstore=FirebaseFirestore.getInstance();


        wishlistUidOfHotel = new ArrayList<>();
        dharamshalaUid = new ArrayList<>();
        dharamshalaList=new ArrayList<HotelData>();
        featureRecycler=view.findViewById(R.id.listrecycler);
        hotelListAdapter=new WishListAdapter(getContext(),dharamshalaList, dharamshalaUid, this);
        featureRecycler.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        featureRecycler.setAdapter(hotelListAdapter);

        // getting wishlist of user from firestore
        mstore.collection("UsersData")
                .document(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            DocumentSnapshot document = task.getResult();
                            User user = document.toObject(User.class);

                            wishlistUidOfHotel = (ArrayList<String>) user.getWishList();


                        // finding dharamshalas according to wishListUid
                        for(String wishItem :wishlistUidOfHotel) {

                            mstore.collection("Dharamsalas")
                                    .document(wishItem)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                DocumentSnapshot document = task.getResult();

                                                HotelData dharamshala = document.toObject(HotelData.class);
                                                dharamshalaList.add(dharamshala);

                                                // storing doc Uid's to use it for clickListener
                                                dharamshalaUid.add(document.getId());
                                                Log.e("dharamshalas", document.getId());

                                                hotelListAdapter.notifyDataSetChanged();



                                        }
                                    });





                        }


                    }
                });


        return view;
    }

    @Override
    public void onHotelClick(int position) {
        // sending docUid to Booking Activity
        Intent intent = new Intent(getContext(), Booking.class);
        intent.putExtra("docUid", dharamshalaUid.get(position));
        startActivity(intent);

    }

    @Override
    public void onWishListClick(int position) {
        // unimplemented for this fragment

    }
}