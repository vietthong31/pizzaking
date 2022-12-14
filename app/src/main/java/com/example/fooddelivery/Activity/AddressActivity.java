package com.example.fooddelivery.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddelivery.Adapter.AddressAdapter;
import com.example.fooddelivery.Model.AddressModel;
import com.example.fooddelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress {

    public static String selectedAddress = "";
    Button addAddressBtn, paymentBtn;
    RecyclerView recyclerView;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    String mAddress = "";
    private ArrayList<AddressModel> addressModelArrayList;
    private AddressAdapter addressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.address_recycler);
        paymentBtn = findViewById(R.id.payment_btn);
        addAddressBtn = findViewById(R.id.add_address_btn);


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        addressModelArrayList = new ArrayList<>();
        addressAdapter = new AddressAdapter(getApplicationContext(), addressModelArrayList, this);
        recyclerView.setAdapter(addressAdapter);

        //Get data from FireStore
        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                AddressModel addressModel = doc.toObject(AddressModel.class);
                                addressModelArrayList.add(addressModel);
                                addressAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedAddress.isEmpty()) {
                    Toast.makeText(AddressActivity.this, "Ch???n ?????a ch???", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(AddressActivity.this, PaymentActivity.class));
                }
            }
        });

        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddressActivity.this, AddAddressActivity.class));
            }
        });
    }

    @Override
    public void setAddress(String address) {
        mAddress = address;
    }
}