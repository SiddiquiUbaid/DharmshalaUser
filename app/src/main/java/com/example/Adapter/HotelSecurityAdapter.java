package com.example.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.Domain.DrawablesClass;
import com.example.hotel.R;

import java.util.ArrayList;

public class HotelSecurityAdapter extends RecyclerView.Adapter<HotelSecurityAdapter.ViewHolder> {

    private ArrayList<String> localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.textViewPlaceSecurity);
            imageView = (ImageView) view.findViewById(R.id.imageViewPlaceSecurity);

        }


        public TextView getTextView() {
            return textView;
        }
        public ImageView getImageView() {
            return imageView;
        }

    }

    public HotelSecurityAdapter(ArrayList<String> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.hotel_security, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.getTextView().setText(localDataSet.get(position));

        viewHolder.getImageView().setImageResource(DrawablesClass
                .getDrawable(localDataSet.get(position)));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
