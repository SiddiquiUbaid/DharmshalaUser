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

public class HomeActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    private MeowBottomNavigation bottomNavigationBar ;
    HomeFragment homeFragment;
    private long pressedTime;
    Fragment frg = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth= FirebaseAuth.getInstance();
        homeFragment=new HomeFragment();
       homeFragment.setRetainInstance(true);






        bottomNavigationBar = findViewById(R.id.bnv_Main);
        bottomNavigationBar.add(new MeowBottomNavigation.Model(1,R.drawable.home));
        bottomNavigationBar.add(new MeowBottomNavigation.Model(2,R.drawable.search));
        bottomNavigationBar.add(new MeowBottomNavigation.Model(3,R.drawable.notification));
        bottomNavigationBar.add(new MeowBottomNavigation.Model(4,R.drawable.book));
        bottomNavigationBar.add(new MeowBottomNavigation.Model(5,R.drawable.person));

        bottomNavigationBar.show(1,true);
        replace(homeFragment);
        bottomNavigationBar.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case 1:
                        replace( homeFragment);
                        break;

                    case 2:
                        replace(new SearchFragment());
                        break;

                    case 3:
                        replace(new NotificationFragment());
                        break;

                    case 4:
                        replace(new WishlistFragment());
                        break;

                    case 5:
                        replace((new ProfileFragment()));
                        break;

                }
                return null;
            }
        });





    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }




}