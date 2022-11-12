package com.example.hotel;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import com.example.Adapter.OrdersTabAdapter;
import com.google.android.material.tabs.TabLayout;

public class OrdersTabActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_orders_tab);

        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Upcoming"));
        tabLayout.addTab(tabLayout.newTab().setText("Ongoing"));
        tabLayout.addTab(tabLayout.newTab().setText("Completed"));
        tabLayout.addTab(tabLayout.newTab().setIcon( getDrawable(R.drawable.cancel_test_512) ));
        tabLayout.setTabGravity(TabLayout.GRAVITY_START);
        final OrdersTabAdapter adapter = new OrdersTabAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}