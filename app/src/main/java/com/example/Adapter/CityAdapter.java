package com.example.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Domain.City;
import com.example.hotel.ExpandCityActivity;
import com.example.hotel.HomeActivity;
import com.example.hotel.R;

import java.util.ArrayList;
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

        holder.cityimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> list = mCityList.get(position).getDharmshalas();
                Toast.makeText(context, "arrayLength: "+ list.size() + "item: "+ list.get(0), Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(context, ExpandCityActivity.class);
                intent.putExtra("cityName", mCityList.get(position).getCityName());
                intent.putStringArrayListExtra("listOfHotelId", list);
                context.startActivity(intent);



            }
        });

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
