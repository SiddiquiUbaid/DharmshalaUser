package com.example.hotel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.Domain.User;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FullProfileActivity extends AppCompatActivity {

    ImageView imagePro;
    TextView name, email, phone, editProfile;

    String uid;
    Uri uri;
    String downloadUrl;



    FirebaseDatabase db;
    DatabaseReference node;
    FirebaseStorage storage;
    StorageReference uploader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_full_profile);

        name = findViewById(R.id.pName);
        email = findViewById(R.id.pMail);
        phone = findViewById(R.id.pPhn);

        editProfile = findViewById(R.id.editProfile);


        // profile data fetching
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


        // creating object to get instance of whole database
        db = FirebaseDatabase.getInstance();

        // getting reference of a node from database
        node = db.getReference().child("Users").child(uid);
        node.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);


                name.setText("NAME :  " +user.getName());
                phone.setText("PH-NO :  " +user.getNumber());
                email.setText("EMAIL :  " +user.getEmail());


                // fetching image from firebase
                Glide.with(getApplicationContext()).load(user.getDpUrl()).placeholder(R.drawable.personicon).into(imagePro);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        /* profile data fetching*/

        imagePro = findViewById(R.id.imagePro);


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            uri = data.getData();

            uploadImage(); // ubaid code
            imagePro.setImageURI(uri);


        }
    }

    // UploadImage method
    public void uploadImage() {
        if (uri != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(getApplicationContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            storage = FirebaseStorage.getInstance();
            uploader = storage.getReference().child(uid);


            // adding listeners on upload
            // or failure of image
            uploader.putFile(uri)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot) {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();

                                    uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            node.child("dpUrl").setValue(uri.toString());


                                        }
                                    });

                                    Toast
                                            .makeText(getApplicationContext(),
                                                    "Image Uploaded !!",
                                                    Toast.LENGTH_SHORT)
                                            .show();

                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(getApplicationContext(),
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();


                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int) progress + "%");
                                }
                            });
        }

    }



}