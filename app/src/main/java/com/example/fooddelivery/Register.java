package com.example.fooddelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fooddelivery.Model.User;
import com.example.fooddelivery.databinding.ActivityLoginBinding;
import com.example.fooddelivery.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private EditText editTextName, editTextEmail, editTextPass, editPhone;
    private ProgressBar progressBar;
    private Button btnRegister;
    private ActivityRegisterBinding binding;
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://fooddelivery-f3ed3.appspot.com");
    private StorageReference reference = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        StorageReference logoReference = reference.child("images/logo.png");
        Glide.with(this).load(logoReference).into(binding.imagePizza);

        anhxa();
        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSignin:
                btnRegister();
                break;
        }
    }

    private void btnRegister() {
        String full_name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();
        String pass = editTextPass.getText().toString().trim();

        if(full_name.isEmpty()){
            editTextName.setError("Full name is required");
            editTextName.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email!!!");
            editTextEmail.requestFocus();
            return;
        }

        if(phone.isEmpty()){
            editPhone.setError("Phone is required");
            editPhone.requestFocus();
            return;
        }

        if(pass.isEmpty()){
            editTextPass.setError("Full name is required");
            editTextPass.requestFocus();
            return;
        }

        if(pass.length() < 10){
            editTextPass.setError("Min password lenght should be 10 characters!");
            editTextPass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(full_name, email, phone);

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCanceledListener(new OnCanceledListener() {
                                        @Override
                                        public void onCanceled() {
                                            if(task.isSuccessful()){
                                                Toast.makeText(Register.this, "Successfully", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(Register.this, Login.class));

                                                progressBar.setVisibility(View.VISIBLE);
                                            }else{
                                                Toast.makeText(Register.this, "Fail", Toast.LENGTH_SHORT).show();                                                progressBar.setVisibility(View.VISIBLE);
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(Register.this, "Fail to register! Try again", Toast.LENGTH_SHORT).show();                                                progressBar.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );
    }

    private void anhxa(){
        editTextName = findViewById(R.id.inputFullName);
        editTextEmail = findViewById(R.id.inputEmail);
        editPhone = findViewById(R.id.inputPhone);
        editTextPass = findViewById(R.id.inputPassword);

        btnRegister = findViewById(R.id.btnSignin);
        progressBar = findViewById(R.id.progressBar);
    }
    //test
}