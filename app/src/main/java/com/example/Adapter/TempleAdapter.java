package com.example.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Domain.City;
import com.example.Domain.Temples;
import com.example.hotel.R;
import com.example.hotel.StoryDisplay;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TempleAdapter extends RecyclerView.Adapter<TempleAdapter.ViewHolder> {
    Context context;
    List<Temples> templesList;
    public TempleAdapter(Context context, List<Temples> templesList) {
        this.context=context;
        this.templesList=templesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.singletemple_story,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        holder.textView.setText(templesList.get(position).getName());
        Glide.with(context).load(templesList.get(position).getImgUrl1()).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StoryDisplay.class);
                intent.putExtra("details",templesList.get(position));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return templesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.timage);
            textView=itemView.findViewById(R.id.tname);

        }
    }
}
