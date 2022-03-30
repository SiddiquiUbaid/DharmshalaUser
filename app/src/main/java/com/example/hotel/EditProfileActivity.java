package com.example.hotel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.Domain.User;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {
    ImageView imagePro;
    EditText name, email, phone;
    TextView saveProfile;

    String uid;
    Uri uri;
    String dpUrl;

    FirebaseDatabase db;
    DatabaseReference node;
    FirebaseStorage storage;
    StorageReference uploader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_profile);

        name = findViewById(R.id.pName);
        email = findViewById(R.id.pMail);
        phone = findViewById(R.id.pPhn);

        saveProfile = findViewById(R.id.saveProfile);


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


                name.setText(user.getName());
                phone.setText(user.getNumber());
                email.setText(user.getEmail());

                // fetching image from firebase
                Glide.with(getApplicationContext()).load(user.getDpUrl()).placeholder(R.drawable.personicon).into(imagePro);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        /* profile data fetching*/

        imagePro = findViewById(R.id.imagePro);
        imagePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(EditProfileActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1024, 1024)     //Final image resolution will be less than 1080 x 1080(Optional)
                        .start(1);



            }
        });


        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = name.getText().toString();
                String userPhone = phone.getText().toString();
                String userMail = email.getText().toString();

                uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                // creating object to get instance of whole database
                db = FirebaseDatabase.getInstance();

                // getting reference of a node from database
                node = db.getReference().child("Users").child(uid);
                User user =new User(userName,userMail,userPhone,dpUrl);


                if(uri != null){
                    // profile image upload
                    uploadImage();

                    node.setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(), "profile updated", Toast.LENGTH_SHORT).show();
                                }
                            });

                }
                else{
                    HashMap<String, Object> updates = new HashMap<>();
                    updates.put("name", user.getName());
                    updates.put("number", user.getNumber());
                    updates.put("email", user.getEmail());

                    node.updateChildren(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "profile updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }








            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            uri = data.getData();

            imagePro.setImageURI(uri);


        }
    }

    // UploadImage method
    public void uploadImage() {
        if (uri != null) {

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
                                    uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            dpUrl = uri.toString();
                                            node.child("dpUrl").setValue(dpUrl);

                                        }
                                    });


                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast
                                    .makeText(getApplicationContext(),
                                            "Image upload Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();


                        }
                    });

        }


    }


}