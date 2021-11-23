package com.user.gym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gym.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText mail_log;
    EditText pass_log;
    FirebaseAuth mAuth;
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mail_log = findViewById(R.id.mail_log);
        pass_log = findViewById(R.id.pass_log);


        mAuth = FirebaseAuth.getInstance();
        loading = findViewById(R.id.loading_log);

    }


    public void loginlogin(View view) {


        if (mail_log.getText().toString().trim().length() > 5 && pass_log.getText().toString().trim().length() > 5) {
            loading.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(mail_log.getText().toString().trim(), pass_log.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Login.this, "Login", Toast.LENGTH_LONG).show();
                        goToDashboard();
                        loading.setVisibility(View.INVISIBLE);
                    } else {
                        Toast.makeText(Login.this, task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();
                        loading.setVisibility(View.INVISIBLE);

                    }

                }
            });


        }
        if (mail_log.getText().toString().trim().length() < 5) {
            mail_log.setError("Please enter your mail");
        }
        if (pass_log.getText().toString().trim().length() < 5) {
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