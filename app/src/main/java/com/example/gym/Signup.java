package com.example.gym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gym.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class Signup extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private NumberPicker weight_picker;
    private TextView heighttext, weightvalue, bitrhdaydate;
    private EditText fnmae, lname, email, password, phone, birth;
    private Integer height = 170;
    private Integer weight = 70;
    private String userID;
    private SeekBar seekBar;
    private ProgressBar loading, uploading;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Uri image;
    private RadioGroup planradio;
    private int daysofsub;
    private Button chooseimage, uploadimage;
    private ImageView profileimage;
    private StorageReference storageReference;
    private DatabaseReference firebaseDatabase;
    String birthdatte;
    Button signup;
    String subscribtion_Date;
    ActivitySignupBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        storageReference = FirebaseStorage.getInstance().getReference("image");
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("image");


        // chooseimage = findViewById(R.id.choosephoto);
        uploadimage = findViewById(R.id.uploadphoto);
        profileimage = findViewById(R.id.profileimage);
        signup = findViewById(R.id.signupup);

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
        weight_picker.setValue(weight);
        // height_picker.setValue(height);
        loading = findViewById(R.id.loading_photo);
        bitrhdaydate = findViewById(R.id.Birthdaydate);

        subscribtion_Date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


        //choose plan




        //Birth Date Selector
        bitrhdaydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Signup.this,
                        android.R.style.Theme_Holo_Light,
                        dateSetListener, year, month, day
                );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                month = month + 1;

                birthdatte = day + "/" + month + "/" + year;
                bitrhdaydate.setText(birthdatte);


            }
        };


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

    //Signup Button
    public void signupp(View view) {

        sendinguserData();


    }

    public void camera(View view) {
        chooseimage();

    }


    private void chooseimage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_IMAGE_REQUEST && data != null && resultCode == RESULT_OK) {

            image = data.getData();
            profileimage.setImageURI(image);
            binding.uploadphoto.setVisibility(View.INVISIBLE);
            binding.profileimage.setVisibility(View.VISIBLE);

        } else {
            System.out.println("FAIL");
        }


    }


    void Addphototofirebase() {

        if (image != null) {
            StorageReference profileupload = storageReference.child("profile/" + userID);
            profileupload.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    gotoPlan();


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Signup.this, e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    loading.setProgress((int) (100 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount()));

                }
            });

        } else {
            Toast.makeText(Signup.this, "Please upload your photo", Toast.LENGTH_LONG).show();
        }


    }

    void gotoPlan() {
        Intent intent = new Intent(Signup.this, Plan.class);
        intent.putExtra("userid", userID);
        startActivity(intent);

        finish();
    }

    public void test(View view) {
        gettingToken();
        subscribe_noti();
    }

    void gettingToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    String token = task.getResult();
                    DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
                    documentReference.update("token", token);
                    Log.v("firebase_token", token);

                }

            }
        });
    }

    void sendmessage() {


    }

    void sendinguserData() {


        System.out.println(daysofsub + "check days");


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


            try {
                signup.setEnabled(false);
                signup.setAlpha(.5F);
                loading.setVisibility(View.VISIBLE);


                mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {


                            //injectData

                            userID = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
                            Map<String, Object> users = new HashMap<>();

                            users.put("fname", fnmae.getText().toString().trim());
                            users.put("lname", lname.getText().toString().trim());
                            users.put("mail", email.getText().toString().trim());
                            users.put("phone", phone.getText().toString().trim());
                            users.put("password", password.getText().toString().trim());
                            users.put("height", height);
                            users.put("weight", weight);
                            users.put("age", birthdatte);
                            users.put("daysnumber", daysofsub);
                            users.put("date", subscribtion_Date);
                            users.put("activation", "False");
                            documentReference.set(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Addphototofirebase();
                                    gettingToken();
                                    subscribe_noti();


                                }
                            });


                        } else {
                            signup.setEnabled(true);
                            signup.setAlpha(1);
                            loading.setVisibility(View.INVISIBLE);

                            Toast.makeText(Signup.this, task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();

                        }

                    }


                });
            } catch (Exception e) {
                Toast.makeText(Signup.this, e.toString(), Toast.LENGTH_LONG).show();
                signup.setEnabled(true);
                signup.setAlpha(0);
                loading.setVisibility(View.INVISIBLE);

            }
        }

    }

    void subscribe_noti() {
        FirebaseMessaging.getInstance().subscribeToTopic("Gym")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Signup.this, "Done", Toast.LENGTH_LONG).show();
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Signup.this, e.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
