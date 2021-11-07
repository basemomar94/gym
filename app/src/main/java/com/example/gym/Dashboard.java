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
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.WriterException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class Dashboard extends AppCompatActivity {


    public ProgressBar progressBar;
    public Integer progressnum = 25;
    TextView remaining, welcome, offer, date;
    Dialog dialog;
    ImageView Qr, Profile_photo;
    Bitmap Qr_Bitmap;
    String registrationdate;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    String userID;
    Bitmap GeneratedQr;
    String days_string = "0";
    int days_number;
    Double days_Double;
    Integer days_int;
    int hour;
    int min;

    @Override
    protected void onStart() {
        super.onStart();
        downloadprofile();
        update_days();

        //  update_days();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        firebaseAuth = FirebaseAuth.getInstance();
        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR);
        min = calendar.get(Calendar.MINUTE);
        System.out.println(hour + "hour");
        Profile_photo = findViewById(R.id.profile_photo);
        date = findViewById(R.id.todaydate);
        String today_Date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        date.setText(today_Date);


//
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
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
                registrationdate = value.getString("date");

                System.out.println("days" + days_Double);



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


    //progress circle

  /*  public void updateprogress() {

        try {

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

        } catch (Exception e) {

        }


    }*/


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


    //Qr Expanding Dialogue

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
            Qr_Bitmap = grBits;

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

    /*void upadate_days() {
        try {
            if (hour == 3 && min == 35) {
                days_Double--;
                DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
                Map<String, Object> day = new HashMap<>();
                day.put("daysnumber", days_Double);
                documentReference.set(day).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        remaining.setText(days_Double.toString());


                    }
                });
            }

        } catch (Exception e) {

            remaining.setText("Error");
        }

    }*/

    void downloadprofile() {

        try {
            StorageReference profile = storageReference.child("image/profile/" + userID);
            long MAXBYTES = 1024 * 1024;
            profile.getBytes(MAXBYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    Profile_photo.setImageBitmap(bitmap);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Dashboard.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        } catch (Exception e) {

        }


    }


    public void teststuff(View view) {
        update_days();
        downloadprofile();
    }

    void update_days() {
        DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String sub = value.getString("date");
                Double daysofsub = value.getDouble("daysnumber");
                System.out.println(daysofsub + "fire");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date sub_date = simpleDateFormat.parse(sub);
                    String today_Date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    Date today_date = simpleDateFormat.parse(today_Date);
                    long remaing = Math.abs(today_date.getTime() - sub_date.getTime());
                    int diffenrence = (int) TimeUnit.DAYS.convert(remaing, TimeUnit.MILLISECONDS);
                    System.out.println(daysofsub);
                    System.out.println(diffenrence);
                    int actual_remaining = (int) (daysofsub - diffenrence);
                    progressBar.setMax(days_Double.intValue());
                    System.out.println(actual_remaining);

                    progressBar.setProgress(actual_remaining);
                    if (actual_remaining > 0) {
                        remaining.setText(actual_remaining + " days");
                    } else {
                        remaining.setText("renew your subscribtion");
                    }


                    System.out.println(diffenrence);
                } catch (Exception e) {
                    System.out.println(e);
                }


               /* try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    String today_Date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    Date today_date = simpleDateFormat.parse(today_Date);
                    Date sub_date = simpleDateFormat.parse(sub);
                    long remaing = Math.abs(today_date.getTime()-sub_date.getTime());
                    System.out.println(remaing+"remaining");
                    Toast.makeText(Dashboard.this, (int) remaing,Toast.LENGTH_LONG).show();

                } catch (ParseException e) {
                    e.printStackTrace();
                }*/

            }
        });
    }
}