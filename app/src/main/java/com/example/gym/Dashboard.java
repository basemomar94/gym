package com.example.gym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {


    public ProgressBar progressBar;
    public Integer progressnum = 25;
    TextView remaining;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);



        progressBar = findViewById(R.id.progress);
        remaining =findViewById(R.id.remaningdays);
        updateprogress();
        final ActionBar actionBar = getSupportActionBar();



        actionBar.setTitle("Home");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuedashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.aboutt: {
                gotoabout();
            }
            case R.id.notifi: {
                gotonotif();

            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void gotoabout() {
        Intent intentabout = new Intent(Dashboard.this, About.class);
        startActivity(intentabout);

    }

    public void gotonotif() {
        Intent intentabout = new Intent(Dashboard.this, Notifications.class);
        startActivity(intentabout);

    }

    public void test(View view) {


        if (progressnum<=30){
            progressnum=progressnum-1;
            updateprogress();

        }
        else {
            progressnum=0;
            progressnum=progressnum+1;
            updateprogress();
        }
       // progressnum=progressnum+10;
        //updateprogress();
    }

    public void updateprogress() {
        progressBar.setMax(30);
        if (progressnum <= 0) {
            remaining.setText("Please subscribe!");

        } else {
            remaining.setText(progressnum + " days remaining");
            progressBar.setProgress(progressnum);
        }

    }


    public void Training(View view) {
        Intent intent = new Intent(Dashboard.this, Training.class);
        startActivity(intent);
    }

    public void plandetails(View view) {
        gotoPlanDetails();
    }

    void gotoPlanDetails() {
        Intent intent = new Intent(Dashboard.this, Subscribtion_info.class);
        startActivity(intent);

    }
}