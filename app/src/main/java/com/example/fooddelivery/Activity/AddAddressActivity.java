package com.example.fooddelivery.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fooddelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity implements View.OnClickListener{

    EditText name, address,city,postalCode,phoneNumber;
    Button addAddresBtn;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        anhxa();
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        addAddresBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_address_btn:
                AddAddr();
                break;
        }

    }

    private void AddAddr() {
        String userName = name.getText().toString();
        String userAddres = address.getText().toString();
        String userCity = city.getText().toString();
        String userCode = postalCode.getText().toString();
        String userPhone = phoneNumber.getText().toString();

        String final_addres = "";

        if(!userName.isEmpty()){
            final_addres+=userName;
        }
        if(!userAddres.isEmpty()){
            final_addres+=userAddres; // nhấn được rồi mà chưa vào database
        }
        if(!userCity.isEmpty()){
            final_addres+=userCity;
        }
        if(!userCode.isEmpty()){
            final_addres+=userCode;
        }
        if(!userPhone.isEmpty()){
            final_addres+=userPhone;
        }

        if(!userName.isEmpty() && !userAddres.isEmpty() && !userCity.isEmpty() && !userCode.isEmpty() && !userPhone.isEmpty()){
            Map<String, String> map = new HashMap<>();
            map.put("userAddress", final_addres);

            firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                                    .collection("Address").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            Toast.makeText(AddAddressActivity.this, "Address added", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {
            Toast.makeText(AddAddressActivity.this, "fail", Toast.LENGTH_SHORT).show();
        }
    }
    public void anhxa() {
        name = findViewById(R.id.ad_name);
        address = findViewById(R.id.ad_address);
        city = findViewById(R.id.ad_city);
        postalCode = findViewById(R.id.ad_code);
        phoneNumber = findViewById(R.id.ad_phone);
        addAddresBtn = findViewById(R.id.ad_add_address);

    }
}