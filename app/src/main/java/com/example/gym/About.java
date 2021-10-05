package com.example.gym;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class About extends AppCompatActivity {
    ActionBar actionBar = getSupportActionBar();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}