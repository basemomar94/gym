package com.example.gym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText mail_log;
    EditText pass_log;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.splashScreenTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        mAuth = FirebaseAuth.getInstance();
        mAuth.getCurrentUser();


        if (mAuth.getCurrentUser() != null) {
            gotoDashboard();

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menue, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {

            case R.id.about: {
                gotoabout();
                System.out.println("I work");
            }
        }


        return super.onOptionsItemSelected(item);


    }

    public void login(View view) {

        gotoLogin();


    }

    public void gotoabout() {
        Intent intentabout = new Intent(MainActivity.this, About.class);
        startActivity(intentabout);

    }

    public void signup(View view) {
        Intent signup = new Intent(MainActivity.this, Signup.class);
        startActivity(signup);
        finish();
    }

    void gotoLogin() {
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish();

    }

    void gotoDashboard() {
        Intent intent = new Intent(MainActivity.this, Dashboard.class);
        startActivity(intent);
        finish();

    }
}