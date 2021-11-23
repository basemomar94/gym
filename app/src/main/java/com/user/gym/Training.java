package com.user.gym;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.gym.R;

public class Training extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Training");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    public void sendtoTraining(String setnumber) {

        Intent intent = new Intent(getApplicationContext(), TrainingView.class);
        intent.putExtra("Biceps", setnumber);
        startActivity(intent);


    }

    public void Goto(View view) {
        switch (view.getId()) {
            case R.id.set1:
                sendtoTraining("1");
                break;


            case R.id.set2:
                sendtoTraining("2");
                break;

            case R.id.set3:
                sendtoTraining("3");
                break;

            case R.id.set4:
                sendtoTraining("4");
                break;

            case R.id.set5:
                sendtoTraining("5");
                break;

            case R.id.set6:
                sendtoTraining("6");
                break;


        }
    }
}
