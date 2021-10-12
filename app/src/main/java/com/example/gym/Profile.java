package com.example.gym;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Profile extends AppCompatActivity {

    TextView fname, lname, mail, mobile, height, weight, age;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        fname = findViewById(R.id.fname_pro);
        lname = findViewById(R.id.lname_prof);
        mail = findViewById(R.id.mail_prof);
        mobile = findViewById(R.id.mobile_prof);
        height = findViewById(R.id.height_prof);
        weight = findViewById(R.id.weight_prof);
        age = findViewById(R.id.age_prof);

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
                weight.setText("Weight : " + value.getString("weight"));
                mail.setText("Email : " + value.getString("mail"));
                height.setText("Height : " + value.getString("height"));
                mobile.setText("Phone number : " + value.getString("phone"));


            }
        });


    }
}