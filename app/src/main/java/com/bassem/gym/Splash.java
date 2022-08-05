package com.bassem.gym;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.bassem.gym.databinding.ActivitySplashBinding;
import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity {

    private final int splash_duration = 1;
    ActivitySplashBinding binding;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAuth = FirebaseAuth.getInstance();
                mAuth.getCurrentUser();


                if (mAuth.getCurrentUser() != null) {
                    gotoDashboard();

                } else {
                    startActivity(new Intent(Splash.this, Login.class));
                    finish();
                }


            }
        }, splash_duration * 800);


    }

    private void gotoDashboard() {
        startActivity(new Intent(Splash.this, Dashboard.class));
        finish();
    }
}