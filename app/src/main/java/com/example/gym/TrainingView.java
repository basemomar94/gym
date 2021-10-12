package com.example.gym;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

public class TrainingView extends AppCompatActivity {


    private int[] Biceps = new int[]{R.drawable.bi, R.drawable.curl, R.drawable.curl2};
    private int[] Triceps = new int[]{R.drawable.tri1, R.drawable.tri2};
    private int[] Leg = new int[]{R.drawable.leg1, R.drawable.leg2, R.drawable.leg3, R.drawable.leg4, R.drawable.leg4};
    private int[] currentImages;
    private String trainingNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainingview);
        checkListID();
        pageviewAdapter();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


    }

    void checkListID() {
        Intent intent = getIntent();
        trainingNum = intent.getStringExtra("Biceps");

        switch (trainingNum) {
            case "1":
                currentImages = Biceps;
                break;
            case "2":
                currentImages = Triceps;
                break;
            case "3":
                currentImages = Leg;
                break;
            default:
                currentImages = Biceps;

        }

    }

    void pageviewAdapter() {
        ViewPager viewPager = findViewById(R.id.viewpager);
        ViewPageAdapter adapter = new ViewPageAdapter(currentImages, this);
        viewPager.setAdapter(adapter);

    }


}