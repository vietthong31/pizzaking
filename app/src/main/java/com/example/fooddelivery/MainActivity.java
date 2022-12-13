package com.example.fooddelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.example.fooddelivery.databinding.ActivityMainBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSignin, btnSignup;
    private ActivityMainBinding binding;
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://fooddelivery-f3ed3.appspot.com");
    private StorageReference reference = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        StorageReference logoReference = reference.child("images/logo.png");
        Glide.with(this).load(logoReference).into(binding.logo);

        btnSignin = findViewById(R.id.btnSignin);
        btnSignup = findViewById(R.id.btnSignup);

        btnSignin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignin:
                startActivity(new Intent(this, Login.class));
                break;

            case R.id.btnSignup:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }
}