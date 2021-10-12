package com.example.gym;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;

public class FirebaseNotification extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String s) {

        System.out.println(s);
        super.onNewToken(s);
    }
}
