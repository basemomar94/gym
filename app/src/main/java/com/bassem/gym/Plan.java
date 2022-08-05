package com.bassem.gym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.bassem.gym.databinding.ActivityPlanBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Plan extends AppCompatActivity {

    ActivityPlanBinding binding;
    FirebaseFirestore firebaseFirestore;
    private int month;
    private int day;
    private int total = 300;
    private int daysofsub = 30;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore = FirebaseFirestore.getInstance();
        userid = getIntent().getStringExtra("userid");

        binding.RadioMonth.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.one_m: {
                        daysofsub = 30;
                        month = 300;
                    }

                    break;
                    case R.id.two_m: {
                        month = 500;
                        daysofsub = 90;
                    }

                    break;
                    case R.id.three_m: {
                        daysofsub = 90;
                        month = 700;
                    }

                    break;
                }
                binding.money.setText(month + " EGP");
            }

        });
        binding.RadioDay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.three_d:
                        day = 0;
                        break;
                    case R.id.five_d:
                        day = 100;
                        break;
                    case R.id.six_d:
                        day = 200;
                        break;
                }
                total = month + day;
                binding.money.setText(total + " EGP");
            }
        });

    }

    public void subscribe(View view) {
        DocumentReference documentReference = firebaseFirestore.collection("users").document(userid);
        Map<String, Object> cost = new HashMap<>();
        cost.put("daysnumber", daysofsub);
        cost.put("money", total);

        documentReference.update(cost).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                gotoDashboard();

            }
        });

    }

    void gotoDashboard() {
        Intent intent = new Intent(Plan.this, Dashboard.class);
        startActivity(intent);
        finish();
    }

    public void testo(View view) {
        System.out.println(total);
        System.out.println(daysofsub);
    }
}