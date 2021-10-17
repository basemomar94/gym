package com.example.gym;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class Signup extends AppCompatActivity {
    NumberPicker height_picker;
    NumberPicker weight_picker;
    NumberPicker agepicker;
    TextView heighttext;
    TextView weightvalue;
    EditText fnmae;
    EditText lname;
    Integer height = 170;
    Integer weight = 70;
    EditText email;
    EditText password;
    EditText phone;
    String userID;
    SeekBar seekBar;
    ProgressBar loading;
    FirebaseFirestore firebaseFirestore;
    DatePickerDialog.OnDateSetListener dateSetListener;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        email = findViewById(R.id.email_signup);
        password = findViewById(R.id.password_signup);
        fnmae = findViewById(R.id.firstname_ET);
        lname = findViewById(R.id.lastname_ET);

        phone = findViewById(R.id.phone_signup);
        weight_picker = findViewById(R.id.weight_picker);
        weightvalue = (TextView) findViewById(R.id.weightvalue);
        weight_picker.setMaxValue(300);
        weight_picker.setMinValue(30);
        weight_picker.setValue(70);
        loading = findViewById(R.id.loading);


        //Weight
        weight_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldvalue, int newvalue) {
                weightvalue.setText(newvalue + " Kg");
                weight = newvalue;
            }
        });
        //Heightpicker
        seekBar = findViewById(R.id.seekBar);
        heighttext = findViewById(R.id.heightvalue);
        seekBar.setMax(250);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progess, boolean b) {

                seekBar.setProgress(progess);
                height = progess;
                heighttext.setText("" + progess + " Cm");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });


    }

    //Signup
    public void signupp(View view) {


        if (email.getText().toString().trim().length() == 0) {
            email.setError("Please enter your email");

        }
        if (password.getText().toString().trim().length() == 0) {
            password.setError("Please enter your password");


        }
        if (fnmae.getText().toString().trim().length() == 0) {
            fnmae.setError("please enter your first name");
        }
        if (lname.getText().toString().trim().length() == 0) {
            lname.setError("please enter your last name");

        }
        if (phone.getText().toString().trim().length() == 0) {
            phone.setError("Please enter your phone number");


        }

        if (email.getText().toString().trim().length() != 0 && password.getText().toString().trim().length() != 0) {
            loading.setVisibility(View.VISIBLE);
            try {


                mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {
                            Toast.makeText(Signup.this, "User is Created", Toast.LENGTH_LONG).show();

                            //injectData

                            userID = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
                            Map<String, Object> users = new HashMap<>();

                            users.put("fname", fnmae.getText().toString().trim());
                            users.put("lname", lname.getText().toString().trim());
                            users.put("mail", email.getText().toString().trim());
                            users.put("phone", phone.getText().toString().trim());
                            users.put("password", password.getText().toString().trim());
                            users.put("height", height.toString());
                            users.put("weight", weight.toString());


                            documentReference.set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {


                                }
                            });


                            Intent intent = new Intent(Signup.this, Dashboard.class);
                            startActivity(intent);
                            loading.setVisibility(View.INVISIBLE);
                        } else {

                            Toast.makeText(Signup.this, task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();
                            System.out.println(email);
                        }

                    }


                });
            } catch (Exception e) {
                Toast.makeText(Signup.this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }


        System.out.println("hello  " + email.getText());


    }

    public void camera(View view) {
        Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        startActivity(intent);

    }

    void errors(EditText field, Map users, String value) {
        if (field.getText().toString().trim().length() == 0) {
            field.setError("You have to compelete this");
        } else users.put(value, field.getText().toString().trim());
    }

    // Data base
    void injectData() {


    }

    // Auth
    void auth() {

    }

    // Date

}