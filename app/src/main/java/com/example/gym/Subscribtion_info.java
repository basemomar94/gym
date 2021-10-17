package com.example.gym;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.ProgressBar;

public class Subscribtion_info extends AppCompatActivity {

    ProgressBar linearprogress;
    ProgressBar CircularprogessBar;
    Integer progress = 25;
    Integer progressMax = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribtion_info);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Plan Details");
        linearprogress = findViewById(R.id.linearprogressBar);
        CircularprogessBar = findViewById(R.id.CircularprogessBar);
        updatelinearProgress();


    }

    void updatelinearProgress() {
        linearprogress.setProgress(progress);
        linearprogress.setMax(progressMax);
        CircularprogessBar.setProgress(progress);
        CircularprogessBar.setMax(progressMax);

    }

}