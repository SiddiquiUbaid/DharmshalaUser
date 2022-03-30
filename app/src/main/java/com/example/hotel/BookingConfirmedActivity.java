package com.example.hotel;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Adapter.HotelOffersAdapter;
import com.example.Domain.AlertDialog;
import com.example.Domain.BookingOrders;
import com.example.Domain.DataClass;
import com.example.Domain.HotelData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;

enum WindowSizeClass { COMPACT, MEDIUM, EXPANDED }

public class BookingConfirmedActivity extends AppCompatActivity {
    TextView greetUser, totalAmount, amountOnPayButton, hotelName, hotelAddress, roomsGuests,
            checkin, checkout, nights, userNameConfBook, policiesExpandedText, rulesnPoliciesExpandedText;

    ImageView closeActivity, hotelImage, imgPoliciesCollapse, imgRulesnPoliciesCollapse;
    LinearLayout hotelDirections, callHotel, modifyBooking, cancelBooking;
    CardView payNow, cancellationPolicy, hotelRules;
    RecyclerView recyclerViewForHotelAmenities;

    String docUid, uid;
    BookingOrders orders;
    ArrayList<BookingOrders> bookingOrdersList = new ArrayList<>();
    ArrayList<String> listOfOrderUid = new ArrayList<>();
    ArrayList<HotelData> dharamshalaList = new ArrayList<>();

    Boolean txtPoliciesExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmed);

        // Replace with a known container that you can safely add a View
        // to where it won't affect the layout and the view won't be
        // replaced.
        ViewGroup container = (ViewGroup) ( (ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);




        // Add a utility view to the container to hook into
        // View.onConfigurationChanged.
        // This is required for all activities, even those that don't
        // handle configuration changes.
        // We also can't use Activity.onConfigurationChanged, since there
        // are situations where that won't be called when the configuration
        // changes.
        // View.onConfigurationChanged is called in those scenarios.
        container.addView(new View(this) {
            @Override
            protected void onConfigurationChanged(Configuration newConfig) {
                super.onConfigurationChanged(newConfig);
                computeWindowSizeClasses();
            }
        });

        computeWindowSizeClasses();




        initializeFeilds();

        greetUser = findViewById(R.id.txtGreetUser);
        userNameConfBook = findViewById(R.id.userNameConfBook);
        totalAmount = findViewById(R.id.TotalAmountInBookingConfirmed);
        hotelName = findViewById(R.id.hotelNameConfBook);
        hotelAddress = findViewById(R.id.hotelAddrConfBook);
        roomsGuests = findViewById(R.id.roomsGuestsConfBook);
        checkin = findViewById(R.id.checkinNameConfBook);
        checkout = findViewById(R.id.checkoutNameConfBook);
        nights = findViewById(R.id.nightsNameConfBook);
        amountOnPayButton = findViewById(R.id.amountOnPayButton);
        policiesExpandedText = findViewById(R.id.txtPoliciesExpand);
        rulesnPoliciesExpandedText = findViewById(R.id.txtRulesnPoliciesExpand);

        closeActivity = findViewById(R.id.closeBookingConfirmed);
        hotelImage = findViewById(R.id.hotelImageConfBook);
        imgPoliciesCollapse = findViewById(R.id.imgPoliciesCollapse);
        imgRulesnPoliciesCollapse = findViewById(R.id.imgRulesnPoliciesCollapse);



        hotelDirections = findViewById(R.id.directionsNameConfBook);
        callHotel = findViewById(R.id.callHotelNameConfBook);
        modifyBooking = findViewById(R.id.modifyBookingNameConfBook);
        cancelBooking = findViewById(R.id.cancelBookingConfBook);

        payNow = findViewById(R.id.cardLayoutForPayConfBook);
        cancellationPolicy = findViewById(R.id.cancellationPolicyConfBook);
        hotelRules = findViewById(R.id.hotelRulesConfBook);

        recyclerViewForHotelAmenities = findViewById(R.id.recylerViewForAmenitiesConfBook);
        recyclerViewForHotelAmenities.setLayoutManager(new GridLayoutManager(BookingConfirmedActivity.this, 2));



        closeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        // updating value at owner side
        FirebaseFirestore dbStore = FirebaseFirestore.getInstance();
        dbStore.collection("UsersData")
                .document(uid)
                .collection("bookingOrders")
                .document(docUid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        orders = task.getResult().toObject(BookingOrders.class);






                        greetUser.setText("Hi "+orders.getUserName()+" !");
                        totalAmount.setText("₹"+orders.getPaymentAmount());
                        amountOnPayButton.setText("₹"+orders.getPaymentAmount());
                        roomsGuests.setText("Rooms "+orders.getRooms()+" X "+ "Guests "+orders.getGuests());
                        checkin.setText(orders.getCheckin());
                        checkout.setText(orders.getCheckout());
                        userNameConfBook.setText(orders.getUserName());
                        nights.setText(orders.getTotalNights()+"ights");

                        // after activity started, check if user already paid amount
                        String paymentStatus = orders.getPaymentType();
                        if(paymentStatus.equals("paid")){

                            amountOnPayButton.setText("Paid");
                            payNow.setBackgroundDrawable(getDrawable(R.drawable.booknow_pressed));
                            payNow.setOnClickListener(null);
                        }


                        //Toast.makeText(getApplicationContext(), "singleDh"+hotelUid, Toast.LENGTH_SHORT).show();
                        FirebaseFirestore mStore = FirebaseFirestore.getInstance();
                        mStore.collection("Dharamsalas")
                                .document(orders.getHotelId())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        HotelData hotelData = task.getResult().toObject(HotelData.class);
                                        dharamshalaList.add(hotelData);
                                        ArrayList<DataClass> placeOffersList = hotelData.getPlaceOffersList();
                                        HotelOffersAdapter offersAdapter = new HotelOffersAdapter(placeOffersList);
                                        recyclerViewForHotelAmenities.setAdapter(offersAdapter);
                                        offersAdapter.notifyDataSetChanged();


                                        hotelName.setText(hotelData.getPlaceName());
                                        hotelAddress.setText("2488, Aram Bagh , Paharaganj, Karol Bagh Tehsil, Delhi");
                                        Glide.with(getApplicationContext())
                                                .load(hotelData.getHotelImages().get(0))
                                                .into(hotelImage);




                                    }
                                });





                    }
                });




        // when user clicks on paynow button
        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // updating value at owner side
                FirebaseFirestore dbStore = FirebaseFirestore.getInstance();
                dbStore.collection("Dharamsalas")
                        .document(docUid)
                        .collection("bookingOrders")
                        .document(uid)
                        .update("paymentType", "paid")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                amountOnPayButton.setText("Paid");
                                payNow.setBackgroundDrawable(getDrawable(R.drawable.booknow_pressed));
                                
                                Toast.makeText(getApplicationContext(), "Amount paid successfully",
                                        Toast.LENGTH_SHORT).show();

                                payNow.setOnClickListener(null);
                            }
                        });

                // updating value at users side
                dbStore.collection("UsersData")
                        .document(uid)
                        .collection("bookingOrders")
                        .document(docUid)
                        .update("paymentType", "paid");


            }
        });


        // expanded txtview for Hotel cancellation policy

        cancellationPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtPoliciesExpanded){
                    txtPoliciesExpanded = true;
                }
                else{
                    txtPoliciesExpanded = false;
                }

                //txtPoliciesExpanded = true;

                if (txtPoliciesExpanded) {

                    policiesExpandedText.setText(R.string.cancellation_policy_expanded);
                    imgPoliciesCollapse.setImageDrawable(getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24));


                }
                else{

                    policiesExpandedText.setText("");
                    imgPoliciesCollapse.setImageDrawable(getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24));

                }

            }
        });

        // expanded txtview for Hotel Rules
        hotelRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtPoliciesExpanded){
                    txtPoliciesExpanded = true;
                }
                else{
                    txtPoliciesExpanded = false;
                }



                if (txtPoliciesExpanded) {

                    imgRulesnPoliciesCollapse.setImageDrawable(getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24));
                    rulesnPoliciesExpandedText.setText(R.string.rules_policy_expanded);


                }
                else{

                    imgRulesnPoliciesCollapse.setImageDrawable(getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24));
                    rulesnPoliciesExpandedText.setText("");

                }

            }
        });

        // when user clicks on cancel booking
        cancelBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(BookingConfirmedActivity.this);
                builder.setMessage("Are you sure to cancel the booking?");
                builder.setTitle("Cancel booking");

                builder.setCancelable(false);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // updating value at owner side
                        FirebaseFirestore dbStore = FirebaseFirestore.getInstance();
                        dbStore.collection("Dharamsalas")
                                .document(docUid)
                                .collection("bookingOrders")
                                .document(uid)
                                .update("paymentType", "cancelled")
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        amountOnPayButton.setText("Cancelled");
                                        payNow.setBackgroundDrawable(getDrawable(R.drawable.booknow_pressed));

                                        Toast.makeText(getApplicationContext(), "Booking cancelled", Toast.LENGTH_SHORT).show();

                                        payNow.setOnClickListener(null);
                                    }
                                });

                        // updating value at users side
                        dbStore.collection("UsersData")
                                .document(uid)
                                .collection("bookingOrders")
                                .document(docUid)
                                .update("paymentType", "cancelled");


                                         }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                });

                android.app.AlertDialog alertDialog = builder.create();
                alertDialog.show();


/*

 */


            }
        });






//




    }


    public void initializeFeilds() {
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            docUid = bundle.getString("docUid");

        } else {
            Toast.makeText(getApplicationContext(), "required data not found", Toast.LENGTH_SHORT).show();
        }

    }



    private void computeWindowSizeClasses() {
        WindowMetrics metrics = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            metrics = this.getWindowManager().getCurrentWindowMetrics();
        }


        float widthDp = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            widthDp = metrics.getBounds().width() /
                    getResources().getDisplayMetrics().density;
        }
        WindowSizeClass widthWindowSizeClass;

        if (widthDp < 600f) {
            widthWindowSizeClass = WindowSizeClass.COMPACT;
        } else if (widthDp < 840f) {
            widthWindowSizeClass = WindowSizeClass.MEDIUM;
        } else {
            widthWindowSizeClass = WindowSizeClass.EXPANDED;
        }

        float heightDp = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            heightDp = metrics.getBounds().height() /
                    getResources().getDisplayMetrics().density;
        }
        WindowSizeClass heightWindowSizeClass;

        if (heightDp < 480f) {
            heightWindowSizeClass = WindowSizeClass.COMPACT;
        } else if (heightDp < 900f) {
            heightWindowSizeClass = WindowSizeClass.MEDIUM;
        } else {
            heightWindowSizeClass = WindowSizeClass.EXPANDED;
        }

        // Use widthWindowSizeClass and heightWindowSizeClass
        //Toast.makeText(getApplicationContext(), ""+widthWindowSizeClass+" "+heightWindowSizeClass, Toast.LENGTH_SHORT).show();
    }


}

