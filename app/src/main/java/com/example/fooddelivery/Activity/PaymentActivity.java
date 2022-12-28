package com.example.fooddelivery.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddelivery.Adapter.AddressAdapter;
import com.example.fooddelivery.Adapter.CheckoutAdapter;
import com.example.fooddelivery.Model.AddressModel;
import com.example.fooddelivery.Model.Cart;
import com.example.fooddelivery.R;
import com.example.fooddelivery.databinding.ActivityPaymentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    private ActivityPaymentBinding binding;

    private CheckoutAdapter checkoutAdapter;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    Button checkoutBtn;
    TextView totalPrice;
    RecyclerView recyclerView;
    ArrayList<Cart> mListCart;

    long total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        recyclerView = binding.itemRcv;
        checkoutBtn = binding.checkout;
        totalPrice = binding.total;

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mListCart = new ArrayList<>();
        checkoutAdapter = new CheckoutAdapter(this, mListCart);
        recyclerView.setAdapter(checkoutAdapter);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (DocumentSnapshot doc : task.getResult().getDocuments()){
                                Cart cart = doc.toObject(Cart.class);
                                total += cart.getTotalPrice();
                                totalPrice.setText(String.valueOf(total));
                                mListCart.add(cart);
                                checkoutAdapter.notifyDataSetChanged();

                            }
                        }
                    }
                });
    }
}