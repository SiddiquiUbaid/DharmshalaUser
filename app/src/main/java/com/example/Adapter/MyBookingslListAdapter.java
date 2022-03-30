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
import com.example.Domain.BookingOrders;
import com.example.Domain.HotelData;
import com.example.hotel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MyBookingslListAdapter extends RecyclerView.Adapter<MyBookingslListAdapter.ViewHolder> {
    Context context;
    List<HotelData> dharamshalaList;
    List<BookingOrders> bookingsList;
    ArrayList<String> listOfHotelUid;
    String selectedHotelUid;
    Boolean addToWishList = false;

    private FirebaseFirestore mstore;
    String uid;
    FirebaseAuth fAuth;


    private OnHotelListener mOnHotelListener;

    public MyBookingslListAdapter(Context applicationContext,List<BookingOrders> bookingsList,
                                  List<HotelData> dharamshalaList, ArrayList<String> listOfHotelUid,
                                  OnHotelListener OnHotelListener) {
        this.context=applicationContext;
        this.dharamshalaList=dharamshalaList;
        this.bookingsList = bookingsList;
        this.listOfHotelUid = listOfHotelUid;
        this.mOnHotelListener = OnHotelListener;


    }



    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull   ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_mybookings,parent,false);
        return new ViewHolder(view, mOnHotelListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.name.setText(dharamshalaList.get(position).getPlaceName());
        holder.addrs.setText("₹"+bookingsList.get(position).getPaymentAmount());
        holder.guests.setText(bookingsList.get(position).getGuests() +" Guest");
        holder.checkindate.setText(bookingsList.get(position).getCheckin()+ " to "
                + bookingsList.get(position).getCheckout());


        Glide.with(context).load(dharamshalaList.get(position).getHotelImages().get(0)).into(holder.firstImage);

        holder.firstImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mOnHotelListener.onHotelClick(position);

            }
        });

        mstore=FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        uid = fAuth.getUid();


      /*  holder.addWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             //   mOnHotelListener.onWishListClick(position);

                if(!addToWishList){

                    selectedHotelUid = listOfHotelUid.get(position);
                    mstore.collection("UsersData")
                            .document(uid)
                            .update("wishList", FieldValue.arrayUnion(selectedHotelUid))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Drawable drawable = context.getResources().getDrawable(R.drawable.book);
                                    holder.addWishList.setImageDrawable(drawable);

                                    Drawable drawable2 = context.getResources().getDrawable(R.drawable.cross);
                                    holder.removeWishList.setImageDrawable(drawable2);

                                    Toast.makeText(view.getContext(),
                                            "saved: " + dharamshalaList.get(position).getPlaceName(),
                                            Toast.LENGTH_SHORT).show();

                                }
                            });

                }

            }

        });

        holder.removeWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   mOnHotelListener.onWishListClick(position);
                if(!addToWishList){


                    selectedHotelUid = listOfHotelUid.get(position);
                    mstore.collection("UsersData")
                            .document(uid)
                            .update("wishList", FieldValue.arrayRemove(selectedHotelUid))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Drawable drawable = context.getResources().getDrawable(R.drawable.add_bookmark_icon);
                                    holder.addWishList.setImageDrawable(drawable);

                                    Drawable drawable2 = context.getResources().getDrawable(R.drawable.custombtn);
                                    holder.removeWishList.setImageDrawable(drawable2);

                                    Toast.makeText(view.getContext(),
                                            "removed: " + dharamshalaList.get(position).getPlaceName(),
                                            Toast.LENGTH_SHORT).show();

                                }
                            });


                }



            }
        });

       */




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
        ImageView firstImage;
        TextView name,addrs,checkindate, guests;
        OnHotelListener onHotelListener;

        public ViewHolder(@NonNull View itemView, OnHotelListener mOnHotelListener) {
            super(itemView);
            firstImage=itemView.findViewById(R.id.firstimage);
            addrs=itemView.findViewById(R.id.hoteladdress);
            name=itemView.findViewById(R.id.hotelname);
            checkindate = itemView.findViewById(R.id.checkinDuration);
            guests = itemView.findViewById(R.id.guests);
        }

        @Override
        public void onClick(View view) {

            // invoking item click listener
//            onHotelListener.onWishListClick(getAdapterPosition());
//            onHotelListener.onHotelClick(getAdapterPosition());


        }


    }

    public interface OnHotelListener{
        void onHotelClick(int position);
        void onWishListClick(int position);
    }
}
