package com.example.gym;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class Notifications extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Notificationitem> notificationitems;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference collectionReference;
    Notification_Adpater_Firebase firebaseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Notifications");
        setUpRecycle();


    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAdapter.stopListening();
    }

    void setUpRecycle() {

        collectionReference = firebaseFirestore.collection("notifications");
        Query query = collectionReference.orderBy("stamp", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Notificationitem> options = new FirestoreRecyclerOptions.Builder<Notificationitem>().setQuery(query, Notificationitem.class).build();
        firebaseAdapter = new Notification_Adpater_Firebase(options);
        RecyclerView recyclerView = findViewById(R.id.recycleview_noti);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(firebaseAdapter);


    }


}