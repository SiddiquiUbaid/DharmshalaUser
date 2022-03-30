package com.example.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Domain.HotelData;
import com.example.Fregments.WishlistFragment;
import com.example.hotel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {
    Context context;
    List<HotelData> dharamshalaList;
    //Boolean addToWishList = false;

    ArrayList<String> listOfHotelUid;
    String selectedHotelUid;

    private FirebaseFirestore mstore;
    String uid;
    FirebaseAuth fAuth;


    private OnHotelListener mOnHotelListener;

    public WishListAdapter(Context applicationContext, List<HotelData> dharamshalaList, ArrayList<String> listOfHotelUid,
                           OnHotelListener OnHotelListener ) {
        this.context=applicationContext;
        this.dharamshalaList=dharamshalaList;
        this.listOfHotelUid = listOfHotelUid;
        this.mOnHotelListener = OnHotelListener;

    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull   ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_wishlist,parent,false);
        return new ViewHolder(view, mOnHotelListener);
    }



    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.name.setText(dharamshalaList.get(position).getPlaceName());
        holder.addrs.setText(dharamshalaList.get(position).getPlaceRent());
        Glide.with(context).load(dharamshalaList.get(position).getHotelImages().get(0)).into(holder.drmimg);

        holder.drmimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mOnHotelListener.onHotelClick(position);

            }
        });


        mstore=FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        uid = fAuth.getUid();

        holder.removeWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   mOnHotelListener.onWishListClick(position);


                    selectedHotelUid = listOfHotelUid.get(position);
                    mstore.collection("UsersData")
                            .document(uid)
                            .update("wishList", FieldValue.arrayRemove(selectedHotelUid))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(view.getContext(),
                                            "removed: " + dharamshalaList.get(position).getPlaceName(),
                                            Toast.LENGTH_SHORT).show();

                                    removeItem(position);

                                }


                            });



            }




        });
        //removeItem(position);






/*
        String s=Double.toString((double) dharamshalaList.get(position).getRating());
        holder.rating.setText(s);
if(dharamshalaList.get(position).getRating()>3){
     holder.rating.setBackgroundColor(Color.parseColor("#7CFC00"));

}
else if (dharamshalaList.get(position).getRating()<=3 && dharamshalaList.get(position).getRating()>=2){
    holder.rating.setBackgroundColor(Color.parseColor("#FFFF00"));
}else{holder.rating.setBackgroundColor(Color.parseColor("#FF0000"));

}
*/



    }


    @Override
    public int getItemCount() {
        return dharamshalaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView drmimg, addWishList, removeWishList;
        TextView name,addrs,rating;
        OnHotelListener onHotelListener;

        public ViewHolder(@NonNull View itemView, OnHotelListener mOnHotelListener) {
            super(itemView);
            drmimg=itemView.findViewById(R.id.firstimage);
            addWishList = itemView.findViewById(R.id.addWishList);
            removeWishList = itemView.findViewById(R.id.removeWishList);

            rating=itemView.findViewById(R.id.rating);
            addrs=itemView.findViewById(R.id.hoteladdress);
            name=itemView.findViewById(R.id.hotelname);
        }

        @Override
        public void onClick(View view) {

            // invoking item click listener
//            onHotelListener.onWishListClick(getAdapterPosition());
            onHotelListener.onHotelClick(getAdapterPosition());


        }


    }
    public void removeItem(int position){
        dharamshalaList.remove(position);
        notifyItemRemoved(position);
        // notifyItemRangeChanged(position, WishListAdapter.this.dharamshalaList.size());
        notifyDataSetChanged();

    }

    public interface OnHotelListener{
        void onHotelClick(int position);
        void onWishListClick(int position);
    }
}
