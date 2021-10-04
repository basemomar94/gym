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

    ProgressBar progressBar;
    Integer progressnum=30;
    TextView remaining;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progress);
        remaining =findViewById(R.id.remaningdays);
        updateprogress();
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

    void updateprogress (){
        progressBar.setMax(30);
        if (progressnum<=0){
            remaining.setText("Please subscribe!");

        }
        else { remaining.setText(progressnum +" days remaining");
            progressBar.setProgress(progressnum);}

    }


}