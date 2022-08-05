package com.bassem.gym;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.bassem.gym.databinding.ActivitySubscribtionInfoBinding;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Subscribtion_info extends AppCompatActivity {

    ProgressBar linearprogress;
    ProgressBar CircularprogessBar;
    int progress;
    int progressMax = 30;
    String userid;
    FirebaseFirestore firebaseFirestore;
    ActivitySubscribtionInfoBinding binding;
    int daysnumber;
    Double remaing;
    String firebase_date;


    @Override
    protected void onStart() {
        super.onStart();
        getting_data();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubscribtionInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore = FirebaseFirestore.getInstance();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Plan Details");
        linearprogress = findViewById(R.id.linearprogressBar);
        CircularprogessBar = findViewById(R.id.CircularprogessBar);

        updatelinearProgress();


    }

    void updatelinearProgress() {
        progress = getIntent().getIntExtra("remaining", 100);
        daysnumber = getIntent().getIntExtra("days", 1);

        System.out.println(progress);
        System.out.println(daysnumber);
        linearprogress.setProgress(progress);
        linearprogress.setMax(daysnumber);
        CircularprogessBar.setProgress(progress);
        CircularprogessBar.setMax(daysnumber);
        binding.daysDetails.setText(progress + "/" + daysnumber);

    }

    void getting_data() {
        Intent intent = getIntent();


        System.out.println(progress + "remain");
        userid = intent.getStringExtra("userid");
        try {
            DocumentReference documentReference = firebaseFirestore.collection("users").document(userid);
            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                    binding.fees.setText("Renewal fees " + value.getDouble("money").toString() + " EGP");

                    System.out.println();


                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    public void test(View view) {

    }

    public void check(View view) {
        getting_data();
    }
}