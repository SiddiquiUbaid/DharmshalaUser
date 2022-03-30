package com.example.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.Domain.DataClass;
import com.example.Domain.DrawablesClass;
import com.example.hotel.R;

import java.util.ArrayList;

public class HotelOffersAdapter extends RecyclerView.Adapter<HotelOffersAdapter.ViewHolder> {

    private ArrayList<DataClass> localDataSet;
    int gloabalVariableForViewItemPosition;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        ImageView imageView;
        //private final TextView textViewDescription;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.txtOffers);
            imageView = view.findViewById(R.id.drawableImage);
            // textViewDescription = (TextView) view.findViewById(R.id.textViewPlaceDescription);
        }





            public TextView getTextView() {
            return textView;
        }

        public ImageView getImageView() {
            return imageView;
        }

      /*  public TextView getTextViewDescription() {
            return textViewDescription;
        } */


    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public HotelOffersAdapter(ArrayList<DataClass> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.hotel_offers, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element


        viewHolder.getImageView().setImageResource(DrawablesClass
                .getDrawable(localDataSet.get(position).getHeading() ));

        viewHolder.getTextView().setText(localDataSet.get(position).getHeading());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }



}






