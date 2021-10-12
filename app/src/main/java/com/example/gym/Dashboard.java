package com.example.gym;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
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

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class Dashboard extends AppCompatActivity {


    public ProgressBar progressBar;
    public Integer progressnum = 25;
    TextView remaining, welcome;
    Dialog dialog;
    ImageView Qr;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userID;
    Bitmap GeneratedQr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        welcome = findViewById(R.id.welcome);
        Qr = findViewById(R.id.Qr);
        userID = firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                welcome.setText("Welcome " + value.getString("fname"));
            }
        });


        progressBar = findViewById(R.id.progress);
        remaining = findViewById(R.id.remaningdays);
        updateprogress();
        final ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Home");
        Generate_Qr();


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
            case R.id.notifi: {
                gotonotif();
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

    public void test(View view) {


        if (progressnum <= 30) {
            progressnum = progressnum - 1;
            updateprogress();

        } else {
            progressnum = 0;
            progressnum = progressnum + 1;
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

    void Generate_Qr() {
        // Initializing the QR Encoder with your value to be encoded, type you required and Dimension
        QRGEncoder qrgEncoder = new QRGEncoder(userID, null, QRGContents.Type.TEXT, 500);
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
}