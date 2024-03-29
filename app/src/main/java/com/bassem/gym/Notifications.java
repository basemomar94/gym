package com.bassem.gym;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class Notifications extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    CollectionReference collectionReference;
    Notification_Adpater_Firebase firebaseAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Notificationitem> notificationitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Notifications");
        setUpRecycle();
        firebaseAdapter.startListening();


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

    @Override
    protected void onResume() {
        super.onResume();
        setUpRecycle();
        firebaseAdapter.startListening();
    }

    void setUpRecycle() {

        collectionReference = firebaseFirestore.collection("notifications");
        Query query = collectionReference.orderBy("stamp", Query.Direction.DESCENDING).limit(15);
        FirestoreRecyclerOptions<Notificationitem> options = new FirestoreRecyclerOptions.Builder<Notificationitem>().setQuery(query, Notificationitem.class).build();
        firebaseAdapter = new Notification_Adpater_Firebase(options);
        RecyclerView recyclerView = findViewById(R.id.recycleview_noti);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(firebaseAdapter);
        firebaseAdapter.setOnitemClick(new Notification_Adpater_Firebase.OnitemClick() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                Notificationitem notificationitem = documentSnapshot.toObject(Notificationitem.class);
                String notiID = documentSnapshot.getId();
                Intent intent = new Intent(Notifications.this, View_Notification.class);
                intent.putExtra("notiID", notiID);
                startActivity(intent);


            }
        });


    }


}