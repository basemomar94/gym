package com.example.gym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    EditText mail_log;
    EditText pass_log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mail_log = findViewById(R.id.mail_log);
        pass_log = findViewById(R.id.pass_log);

    }

    public void loginlogin(View view) {
        String checkmail = mail_log.toString();
        System.out.println("I am fine");


        if (mail_log.getText().toString().trim().length() > 8 && pass_log.getText().toString().trim().length() > 8) {

            goToDashboard();
        }
        if (mail_log.getText().toString().trim().length() < 8 && !checkmail.contains("@")) {
            mail_log.setError("Please enter your mail");
        }
        if (pass_log.getText().toString().trim().length() < 8) {
            pass_log.setError("Please enter your password");
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menue, menu);


        return true;
    }

    public void creatnew(View view) {
        Intent signup = new Intent(Login.this, Signup.class);
        startActivity(signup);
    }

    void goToDashboard() {

        Intent intent = new Intent(Login.this, Dashboard.class);
        startActivity(intent);
        finish();


    }
}