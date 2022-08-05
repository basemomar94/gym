package com.bassem.gym;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bassem.gym.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Profile extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    TextView fname, lname, mail, mobile, height, weight, age;
    ActivityProfileBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userID;
    Date currentdate;
    String userbirthdate;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    ImageView profie_profile;
    RecyclerView recyclerView;
    String mail_user;
    int num = 0;
    String time;
    private Uri image;
    private ArrayList<date_item> Date_list;
    private ArrayList<String> Time_list;


    @Override
    protected void onStart() {
        super.onStart();
        getprofilepic();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("image");
        fname = findViewById(R.id.fname_pro);
        lname = findViewById(R.id.lname_prof);
        mail = findViewById(R.id.mail_prof);
        mobile = findViewById(R.id.mobile_prof);
        age = findViewById(R.id.age_prof);
        getCurrentDate();
        System.out.println(currentdate);
        profie_profile = findViewById(R.id.profie_profile);


        //Recycle View
        recyclerView = findViewById(R.id.recycle_dates);
        Date_list = new ArrayList<>();
        Time_list = new ArrayList<>();

        //Get user id
        userID = firebaseAuth.getCurrentUser().getUid();
        // Fetching Data
        DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                fname.setText("First name : " + value.getString("fname"));
                lname.setText("Last name : " + value.getString("lname"));
                age.setText("Age : " + value.getString("age"));
                mail.setText("Email : " + value.getString("mail"));
                mail_user = value.getString("mail");
                mobile.setText("Phone number : " + value.getString("phone"));
                age.setText("Birth date : " + value.getString("age"));
                System.out.println(userbirthdate + "HBD");

                getdates();
            }
        });


    }

    private String getCurrentDate() {

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
        // currentdate=date


    }

    void getprofilepic() {
        try {
            StorageReference profile = storageReference.child("/profile/" + userID);
            long MAXBYTE = 1024 * 1024;
            profile.getBytes(MAXBYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {

                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    profie_profile.setImageBitmap(bitmap);


                }
            });

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    void choose_image() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            image = data.getData();
            binding.profieProfile.setImageURI(image);

            upload_image();


        }
    }

    public void update(View view) {
        choose_image();
    }

    void upload_image() {
        binding.progressProfile.setVisibility(View.VISIBLE);
        if (image != null) {
            StorageReference profileupload = storageReference.child("profile/" + userID);
            profileupload.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(Profile.this, "profile photo is updated", Toast.LENGTH_LONG).show();
                    binding.progressProfile.setVisibility(View.GONE);
                    getprofilepic();


                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    binding.progressProfile.setProgress((int) (100 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount()));
                    //  binding.progressProfile.setProgress(50);


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Profile.this, "please try again", Toast.LENGTH_LONG).show();
                    binding.progressProfile.setVisibility(View.GONE);

                }
            });
        }

    }

    public void test(View view) {


    }

    void getdates() {
        firebaseFirestore.collection(mail_user).orderBy("stamp", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {


                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {

                        DocumentReference documentReference = firebaseFirestore.collection(mail_user).document(queryDocumentSnapshot.getId());
                        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {


                                time = value.getString("time");
                                Date_list.add(new date_item(queryDocumentSnapshot.getId(), time));
                                setAdapter();

                            }
                        });


                        System.out.println(Date_list.size() + "fire");


                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e.getMessage());

            }
        });

    }


    void setAdapter() {
        date_adpater date_adpater = new date_adpater(Date_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(date_adpater);
        System.out.println(Date_list.size() + "size");


    }
}