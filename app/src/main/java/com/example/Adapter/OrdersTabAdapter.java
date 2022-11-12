package com.example.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.Fregments.CancelledOrdersFragment;
import com.example.Fregments.CompletedOrdersFragment;
import com.example.Fregments.OngoingOrdersFragment;
import com.example.Fregments.UpcomingOrdersFragment;

public class OrdersTabAdapter extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;
    public OrdersTabAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }
    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                UpcomingOrdersFragment upcomingOrdersList = new UpcomingOrdersFragment();
                fragment = upcomingOrdersList;
                break;
            case 1:
                OngoingOrdersFragment ongoingOrdersList = new OngoingOrdersFragment();
                fragment = ongoingOrdersList;
                break;
            case 2:
                CompletedOrdersFragment completedOrdersList = new CompletedOrdersFragment();
                fragment = completedOrdersList;
                break;
            case 3:
                CancelledOrdersFragment cancelledOrdersList = new CancelledOrdersFragment();
                fragment = cancelledOrdersList;
                break;
            default:
                fragment =  null;
        }
        return  fragment;
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}