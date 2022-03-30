package com.example.Fregments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.Domain.AlertDialog;
import com.example.Domain.User;
import com.example.hotel.FullProfileActivity;
import com.example.hotel.MainActivity;
import com.example.hotel.MyBookingsActivity;
import com.example.hotel.R;
import com.example.hotel.WebViewActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {


    ImageView profileImage, expandProfile;
    TextView userName, userMail;
    CardView logoutIcon, myBookingsIcon, termsIcons, aboutUsIcon, refundIcon;

    FirebaseDatabase db;
    DatabaseReference ref;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String uid;

    public ProfileFragment() {
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        userName = view.findViewById(R.id.userName);
        userMail = view.findViewById(R.id.userMail);
        profileImage = view.findViewById(R.id.imagePro);
        expandProfile = view.findViewById(R.id.expandProfile);

        logoutIcon = view.findViewById(R.id.cardViewLogOut);
        myBookingsIcon = view.findViewById(R.id.myBookingsIcon);
        termsIcons = view.findViewById(R.id.cardViewTnC);
        aboutUsIcon = view.findViewById(R.id.cardViewAboutUs);
        refundIcon = view.findViewById(R.id.cardViewRefund);





        uid = fAuth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance();
        ref = db.getReference().child("Users").child(uid);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                userName.setText("Hi " +user.getName()+ "!");
                userMail.setText(user.getEmail());

                // fetching image from firebase
                Glide.with(getContext()).load(user.getDpUrl()).placeholder(R.drawable.personicon).into(profileImage);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        expandProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), FullProfileActivity.class);
                startActivity(intent);


            }
        });

        logoutIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.show(getFragmentManager(), "alert dialog");

            }
        });

        myBookingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyBookingsActivity.class);
                startActivity(intent);

            }
        });

        termsIcons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("activityTitle", "Terms of Services");
                intent.putExtra("url", "https://armtechnical123.wixsite.com/dharamshalainfo/general");
                startActivity(intent);

            }
        });

        aboutUsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("activityTitle", "About Us");
                intent.putExtra("url", "https://armtechnical123.wixsite.com/dharamshalainfo");
                startActivity(intent);

            }
        });

        refundIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("activityTitle", "Payment Policy");
                intent.putExtra("url", "https://armtechnical123.wixsite.com/dharamshalainfo/copy-of-general");
                startActivity(intent);

            }
        });




        return view;


    }



}