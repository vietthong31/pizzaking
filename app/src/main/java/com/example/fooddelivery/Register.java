package com.example.fooddelivery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fooddelivery.Model.User;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private EditText editTextName, editTextEmail, editTextPass, editPhone;
    private ProgressBar progressBar;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
            editTextPass.setError("Password is required");
            editTextPass.requestFocus();
            return;
        }

        if(pass.length() < 10){
            editTextPass.setError("Min password length should be 10 characters!");
            editTextPass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //User user = new User(full_name, email, phone);

                            Log.d("SIGN UP", "success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Register.this, "Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Register.this, Login.class));

                            progressBar.setVisibility(View.GONE);

                        }else{
                            Log.d("SIGN UP", "fail", task.getException());
                            Toast.makeText(Register.this, "Fail to register! Try again", Toast.LENGTH_SHORT).show();                                                progressBar.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
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