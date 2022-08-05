package com.bassem.gym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.bassem.gym.databinding.ActivityPhoneVertificationBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class Phone_vertification extends AppCompatActivity {
    ActivityPhoneVertificationBinding binding;
    FirebaseAuth firebaseAuth;
    String phonenumber;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneVertificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();

    }

    void phone_vertification() {
        String phone = binding.editTextPhone.getText().toString();
        mcallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String vertificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(vertificationID, forceResendingToken);
                binding.processtext.setText("OTP has been sent");
                binding.processtext.setVisibility(View.VISIBLE);
                System.out.println(vertificationID + "code");
                // PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential()
            }
        };

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth).setPhoneNumber(phone).setTimeout(60L, TimeUnit.SECONDS).setActivity(Phone_vertification.this).setCallbacks(mcallback).build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }

    void check_code() {

    }


    public void vertify(View view) {
        phone_vertification();
    }
}