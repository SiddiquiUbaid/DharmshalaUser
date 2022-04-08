package com.example.Adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.Domain.DataClass;
import com.example.Domain.HotelData;
import com.example.hotel.Booking;
import com.example.hotel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class EditRoomsActivity extends AppCompatActivity {

    private static EditText guests, rooms;
    ImageView subtractGuests, subtractRooms;
    ImageView addGuests, addRooms;
    TextView txtTotalRooms;
    TextView txtTotalGuests;
    static TextView txtTotalAmount;
    TextView btnApplyEditRooms;
    Integer  totalAmount;
    Integer defaultAmount;
    String docUid;
    HotelData hotelData;
    String numOfRooms, numOfGuests, totalNights;
    String tempCheckinDate, tempCheckoutDate;
    Long millisOfCheckin, millisOfCheckout;


    private static final Integer[] editRoomsList = new Integer[3];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_rooms);

        initializeTheFields();



        // getting hotel data from firebase
        FirebaseFirestore mstore = FirebaseFirestore.getInstance();
        mstore.collection("Dharamsalas")
                .document(docUid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.isSuccessful()){
                            hotelData = task.getResult().toObject(HotelData.class);


                        }
                        else{

                            Toast.makeText(getApplicationContext(), "Error getting data", Toast.LENGTH_SHORT).show();
                            Log.w("TAG", "Error getting data", task.getException());
                        }

                    }
                });






        subtractGuests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer value = Integer.parseInt(guests.getText().toString());
                if(value > 1) {
                    value = value - 1;
                    guests.setText(value.toString());
                    txtTotalGuests.setText(value.toString());

                    totalAmount -= (defaultAmount / 4 );
                    txtTotalAmount.setText(""+totalAmount);

                    editRoomsList[0] = value;
                }
                else{
                    Toast.makeText(getApplicationContext(), "minimum limit reached!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        subtractRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer value = Integer.parseInt(rooms.getText().toString());
                if(value > 1) {
                    value = value - 1;
                    rooms.setText(value.toString());
                    txtTotalRooms.setText(value.toString());

                    totalAmount -= defaultAmount;
                    txtTotalAmount.setText(""+totalAmount);

                   // totalAmount = defaultAmount - (defaultAmount * (value));

                    editRoomsList[1] = value;
                }
                else{
                    Toast.makeText(getApplicationContext(), "minimum limit reached!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        addGuests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer value = Integer.parseInt(guests.getText().toString());
                if(value < 99) {
                    value = value + 1;
                    guests.setText(value.toString());
                    txtTotalGuests.setText(value.toString());
                    totalAmount += (defaultAmount / 4 );
                    txtTotalAmount.setText(""+totalAmount);

                    editRoomsList[0] = value;
                }
                else{
                    Toast.makeText(getApplicationContext(), "max limit reached!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        addRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer value = Integer.parseInt(rooms.getText().toString());
                if(value < 99) {
                    value = value + 1;
                    rooms.setText(value.toString());
                    txtTotalRooms.setText(value.toString());
                    totalAmount += defaultAmount;
                    txtTotalAmount.setText(""+totalAmount);

                    editRoomsList[1] = value;
                }

                else{
                    Toast.makeText(getApplicationContext(), "max limit reached!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnApplyEditRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditRoomsActivity.this, Booking.class);
                intent.putExtra("docUid", docUid);
                intent.putExtra("editRoomsList", txtTotalAmount.getText().toString());

                intent.putExtra("numOfGuests", guests.getText().toString());
                intent.putExtra("numOfRooms", rooms.getText().toString());
                intent.putExtra("totalNights", totalNights);

                intent.putExtra("refCheckinDate", tempCheckinDate);
                intent.putExtra("refCheckoutDate", tempCheckoutDate);

                intent.putExtra("millisOfCheckin", millisOfCheckin);
                intent.putExtra("millisOfCheckout", millisOfCheckout);




                startActivity(intent);
                finishAffinity();
            }
        });






    }








    void initializeTheFields() {
        guests = findViewById(R.id.id_guestsInput);
        rooms = findViewById(R.id.id_roomsInput);

        subtractGuests = findViewById(R.id.id_guestsSubtract);
        subtractRooms = findViewById(R.id.id_roomsSubtract);

        addGuests = findViewById(R.id.id_guestsAdd);
        addRooms = findViewById(R.id.id_roomsAdd);

        btnApplyEditRooms = findViewById(R.id.btnApplyEditRooms);


        txtTotalAmount = findViewById(R.id.txtTotalAmount);
        txtTotalGuests = findViewById(R.id.txtTotalGuests);
        txtTotalRooms = findViewById(R.id.txtTotalRooms);




        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            defaultAmount = Integer.parseInt(bundle.getString("defaultAmount"));
            totalAmount = defaultAmount;
        }
        else{
            defaultAmount = 0;
        }

        docUid = bundle.getString("docUid");
        numOfRooms = bundle.getString("numOfRooms");
        numOfGuests = bundle.getString("numOfGuests");
        totalNights = bundle.getString("totalNights");
        tempCheckinDate = bundle.getString("tempCheckinDate");
        tempCheckoutDate = bundle.getString("tempCheckoutDate");

        millisOfCheckin = bundle.getLong("millisOfCheckin");
        millisOfCheckout = bundle.getLong("millisOfCheckout");

        guests.setText(numOfGuests);
        rooms.setText(numOfRooms);





        txtTotalAmount.setText(defaultAmount.toString());




    }

    public static Integer[] getNumberOfGuestsList() {
        editRoomsList[0] = Integer.parseInt(guests.getText().toString());
        editRoomsList[1] = Integer.parseInt(rooms.getText().toString());
        editRoomsList[2] = Integer.parseInt(txtTotalAmount.getText().toString());

        return editRoomsList;
    }
}