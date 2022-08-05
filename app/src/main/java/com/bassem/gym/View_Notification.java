package com.bassem.gym;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.bassem.gym.databinding.ActivityViewNotificationBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class View_Notification extends AppCompatActivity {
    ActivityViewNotificationBinding binding;
    String notiID;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityViewNotificationBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        firebaseFirestore = FirebaseFirestore.getInstance();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        storageReference = FirebaseStorage.getInstance().getReference("image");


        notiID = getIntent().getStringExtra("notiID");
        setUp_notif();

    }

    @Override
    protected void onStart() {
        super.onStart();
        get_photo();
    }

    void setUp_notif() {

        DocumentReference documentReference = firebaseFirestore.collection("notifications").document(notiID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                binding.viewTitle.setText(value.getString("title"));
                binding.viewBody.setText(value.getString("body"));

            }
        });

    }

    void get_photo() {
        StorageReference image = storageReference.child("notification/" + notiID);
        long MAXBYTES = 1024 * 1024;
        image.getBytes(MAXBYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                binding.photoNoti.setVisibility(View.VISIBLE);
                binding.photoNoti.setImageBitmap(bitmap);

            }
        });
    }
}