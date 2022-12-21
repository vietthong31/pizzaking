package com.example.fooddelivery.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fooddelivery.App;
import com.example.fooddelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextEmail, editTextPass;
    private Button btnSignin;
    private TextView forgot, register;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        anhxa();

        register.setOnClickListener(this);
        btnSignin.setOnClickListener(this);
        forgot.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register:
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.btnSignin:
                Signin();
                break;
            case R.id.forgot:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }
    }

    private void Signin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPass.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Yêu cầu nhập Email!!");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Vui lòng nhập email đúng @gmail !!");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPass.setError("Yêu cầu nhập mật khẩu!!!");
            editTextPass.requestFocus();
            return;
        }
        if(password.length() < 10){
            editTextPass.setError("Mật khẩu phải có 10 ký tự!");
            editTextPass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //redirect to user profile
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(Login.this, App.class));
                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "Đăng nhập thất bại, vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void anhxa(){
        editTextEmail = findViewById(R.id.inputEmail);
        editTextPass = findViewById(R.id.inputPassword);

        btnSignin = findViewById(R.id.btnSignin);

        forgot = findViewById(R.id.forgot);
        register = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressBar);
    }
}