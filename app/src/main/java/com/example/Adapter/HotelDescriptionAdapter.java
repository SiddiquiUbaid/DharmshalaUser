package com.example.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Domain.DataClass;
import com.example.hotel.R;;

import java.util.ArrayList;
import java.util.Locale;

public class HotelDescriptionAdapter extends RecyclerView.Adapter<HotelDescriptionAdapter.ViewHolder> {

    private ArrayList<DataClass> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewHeading;
        private final TextView textViewDescription;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textViewDescription = (TextView) view.findViewById(R.id.textViewPlaceDescription);
            textViewHeading = (TextView) view.findViewById(R.id.textViewHeading);
        }


        public TextView getTextViewHeading() {
            return textViewHeading;
        }

        public TextView getTextViewDescription() {
            return textViewDescription;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public HotelDescriptionAdapter(ArrayList<DataClass> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.hotel_description, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.getTextViewHeading().setText(localDataSet.get(position).getHeading());
        viewHolder.getTextViewDescription().setText(localDataSet.get(position).getDescription());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
