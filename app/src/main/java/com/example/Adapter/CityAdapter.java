package com.example.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Domain.City;
import com.example.hotel.R;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.Viewholder> {
    Context context;
    List<City> mCityList;

    public CityAdapter(Context applicationContext, List<City> mCityList) {
        this.context=applicationContext;
        this.mCityList=mCityList;


    }

    @NonNull

    @Override
    public Viewholder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_city_view,parent,false);
        return new Viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull  CityAdapter.Viewholder holder, int position) {
        holder.cityname.setText(mCityList.get(position).getCityName());
        Glide.with(context).load(mCityList.get(position).getImgurl()).into(holder.cityimg);

    }

    @Override
    public int getItemCount() {

        return mCityList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView cityimg;
        TextView cityname;

        public Viewholder(@NonNull  View itemView) {
            super(itemView);
cityimg=itemView.findViewById(R.id.city_image);
cityname=itemView.findViewById(R.id.cityname);
        }
    }
}
