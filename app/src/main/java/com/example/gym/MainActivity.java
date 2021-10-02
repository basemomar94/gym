package com.example.gym;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menue,menu);


        return true;
    }

    public void login(View view) {
        Intent intent = new Intent(MainActivity.this,Login.class);
        startActivity(intent);

    }

    void gotologin (){

    }
}