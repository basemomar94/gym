package com.example.gym;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.gym.databinding.ActivitySubscribtionInfoBinding;
import com.google.firebase.firestore.CollectionReference;
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
    Double daysnumber = 90.0;


    @Override
    protected void onStart() {
        super.onStart();

        System.out.println(userid);
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
        getting_data();
        updatelinearProgress();


    }

    void updatelinearProgress() {
        linearprogress.setProgress(progress);
        linearprogress.setMax(daysnumber.intValue());
        CircularprogessBar.setProgress(progress);
        CircularprogessBar.setMax(daysnumber.intValue());

    }

    void getting_data() {
        Intent intent = getIntent();
        progress = intent.getIntExtra("remaining", 0);
        userid = intent.getStringExtra("userid");
        try {
            DocumentReference documentReference = firebaseFirestore.collection("users").document(userid);
            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                    binding.fees.setText("Renewal fees " + value.getDouble("money").toString() + " EGP");
                    daysnumber = value.getDouble("daysnumber");

                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    public void test(View view) {
        System.out.println(progress);
    }
}