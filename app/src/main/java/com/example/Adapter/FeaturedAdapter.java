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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Domain.Dharamshala;
import com.example.Domain.Dharamshalas;
import com.example.Domain.HotelData;
import com.example.Fregments.HomeFragment;
import com.example.hotel.Booking;
import com.example.hotel.R;
import com.example.hotel.StoryDisplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.example.Domain.HotelData;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.ViewHolder> {

    ArrayList<HotelData> dharamshalasList;
    Context context;

    private OnNoteListener mOnNoteListener;

    public FeaturedAdapter(Context applicationContext, ArrayList<HotelData> dharamshalasList, OnNoteListener onNoteListener) {

        this.context=applicationContext;
        this.dharamshalasList=dharamshalasList;
        this.mOnNoteListener = onNoteListener;
    }


    @NonNull


    @Override
    public ViewHolder onCreateViewHolder(@NonNull   ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_dharamshala_view,parent,false);
        return new ViewHolder(view, mOnNoteListener);
    }


    @Override
    // int position is just warning, working fine.
    public void onBindViewHolder(@NonNull   FeaturedAdapter.ViewHolder holder, int position) {
        holder.name.setText(dharamshalasList.get(position).getPlaceName());
       holder.addrs.setText("rent: "+dharamshalasList.get(position).getPlaceRent());
        Glide.with(context).load(dharamshalasList.get(position).getHotelImages().get(0)).into(holder.drmimg);

        holder.drmimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mOnNoteListener.onNoteClick(position);

            }
        });



    }


    @Override
    public int getItemCount() {
        return dharamshalasList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView drmimg;
        TextView name,addrs;
        OnNoteListener onNoteListener;

        public ViewHolder(@NonNull   View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            
            drmimg=itemView.findViewById(R.id.dharamshala_image);
            addrs=itemView.findViewById(R.id.dharamshala_adderess);
            name=itemView.findViewById(R.id.dharamshala_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            // invoking item click listener
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }



    public interface OnNoteListener{
        void onNoteClick(int position);

    }
}
