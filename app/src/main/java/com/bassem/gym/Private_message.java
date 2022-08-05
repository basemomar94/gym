package com.bassem.gym;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bassem.gym.databinding.ActivityPrivateMessageBinding;

public class Private_message extends AppCompatActivity {
    ActivityPrivateMessageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityPrivateMessageBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

    }
}