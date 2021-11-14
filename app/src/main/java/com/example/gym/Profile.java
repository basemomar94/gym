package com.example.gym;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Profile extends AppCompatActivity {

    TextView fname, lname, mail, mobile, height, weight, age;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userID;
    Date currentdate;
    String userbirthdate;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    ImageView profie_profile;

    @Override
    protected void onStart() {
        super.onStart();
        getprofilepic();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        fname = findViewById(R.id.fname_pro);
        lname = findViewById(R.id.lname_prof);
        mail = findViewById(R.id.mail_prof);
        mobile = findViewById(R.id.mobile_prof);
        height = findViewById(R.id.height_prof);
        weight = findViewById(R.id.weight_prof);
        age = findViewById(R.id.age_prof);
        getCurrentDate();
        System.out.println(currentdate);
        profie_profile = findViewById(R.id.profie_profile);

        //Get user id
        userID = firebaseAuth.getCurrentUser().getUid();
        // Fetching Data
        DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                fname.setText("First name : " + value.getString("fname"));
                lname.setText("Last name : " + value.getString("lname"));
                age.setText("Age : " + value.getString("age"));
                weight.setText("Weight : " + value.getDouble("weight").toString());
                mail.setText("Email : " + value.getString("mail"));
                height.setText("Height : " + value.getDouble("height").toString());
                mobile.setText("Phone number : " + value.getString("phone"));
                age.setText("Birth date : " + value.getString("age"));
                System.out.println(userbirthdate + "HBD");


            }
        });


    }

    private String getCurrentDate() {

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
        // currentdate=date


    }

    void getprofilepic() {
        try {
            StorageReference profile = storageReference.child("image/profile/" + userID);
            long MAXBYTE = 1024 * 1024;
            profile.getBytes(MAXBYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {

                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    profie_profile.setImageBitmap(bitmap);


                }
            });

        } catch (Exception e) {

        }
    }
}