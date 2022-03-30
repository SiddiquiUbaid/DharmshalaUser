package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.Fregments.HomeFragment;
import com.example.Fregments.NotificationFragment;
import com.example.Fregments.ProfileFragment;
import com.example.Fregments.SearchFragment;
import com.example.Fregments.WishlistFragment;
import com.google.firebase.auth.FirebaseAuth;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class WishListActivity extends AppCompatActivity {


    HomeFragment homeFragment;
    WishlistFragment wishlistFragment;
    private long pressedTime;
    Fragment frg = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        wishlistFragment=new WishlistFragment();
        wishlistFragment.setRetainInstance(true);


        replace(wishlistFragment);

    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {

            super.onBackPressed();
            finish();

    }





}