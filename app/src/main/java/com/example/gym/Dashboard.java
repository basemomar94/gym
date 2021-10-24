package com.example.gym;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.zxing.WriterException;

import java.util.Calendar;
import java.util.Date;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class Dashboard extends AppCompatActivity {


    public ProgressBar progressBar;
    public Integer progressnum = 25;
    TextView remaining, welcome, offer;
    Dialog dialog;
    ImageView Qr;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userID;
    Bitmap GeneratedQr;
    String days_string = "0";
    int days_number;
    Double days_Double;
    Integer days_int;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        firebaseAuth = FirebaseAuth.getInstance();
        Calendar timeofday = Calendar.getInstance();
       /*
       timeofday.set(Calendar.HOUR_OF_DAY,12);
       timeofday.set(Calendar.MINUTE,0);
       timeofday.set(Calendar.SECOND,0);
       timeofday.set(Calendar.AM_PM,Calendar.AM);
       Long currenttime = new Date().getTime();
       if ()*/

        firebaseFirestore = FirebaseFirestore.getInstance();
        welcome = findViewById(R.id.welcome);
        Qr = findViewById(R.id.Qr);
        offer = findViewById(R.id.offer);
        userID = firebaseAuth.getCurrentUser().getUid();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        days_number = sharedPreferences.getInt("days", Integer.parseInt("0"));

        DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                welcome.setText("Welcome " + value.getString("fname"));

                //  days_string=value.getString("daysnumber");
                days_Double = value.getDouble("daysnumber");

                System.out.println("days" + days_Double);
                updateprogress();


            }
        });




        progressBar = findViewById(R.id.progress);
        remaining = findViewById(R.id.remaningdays);

        final ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Home");

        Generate_Qr();
        get_message();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuedashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.about: {
                gotoabout();

                break;
            }

            case R.id.logout: {
                firebaseAuth.signOut();
                gotofirstScreen();

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



    //progress circule

    public void updateprogress() {

        progressBar.setMax(30);
        progressBar.setProgress(days_Double.intValue());
        if (days_Double != null) {
            days_int = days_Double.intValue();
            remaining.setText(days_int.toString() + " days");


        }
        if (days_int == 0) {
            progressBar.setVisibility(View.INVISIBLE);
            remaining.setText("Click here to renew your subscribtion");
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

    public void QronClick(View view) {

        dialog = new Dialog(Dashboard.this);
        dialog.setContentView(R.layout.qr);

        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.qr_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;


    }

    void gotofirstScreen() {
        Intent intent = new Intent(Dashboard.this, MainActivity.class);
        startActivity(intent);
        finish();

    }


    public void profilepic(View view) {
        Intent intent = new Intent(Dashboard.this, Profile.class);
        startActivity(intent);

    }

    //Generating Qr
    void Generate_Qr() {
        // Initializing the QR Encoder with your value to be encoded, type you required and Dimension
        QRGEncoder qrgEncoder = new QRGEncoder(userID, null, QRGContents.Type.TEXT, 1000);
        qrgEncoder.setColorBlack(Color.BLACK);
        qrgEncoder.setColorWhite(Color.WHITE);
        try {
            // Getting QR-Code as Bitmap
            Bitmap grBits = qrgEncoder.getBitmap();

            // Setting Bitmap to ImageView
            Qr.setImageBitmap(grBits);
            GeneratedQr = grBits;
        } catch (Exception e) {

        }

    }

    void get_message() {
        DocumentReference documentReference = firebaseFirestore.collection("message").document("message");
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                offer.setText(value.getString("message"));

            }
        });
    }
}