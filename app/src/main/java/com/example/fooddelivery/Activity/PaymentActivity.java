package com.example.fooddelivery.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddelivery.Adapter.CheckoutAdapter;
import com.example.fooddelivery.App;
import com.example.fooddelivery.Model.Cart;
import com.example.fooddelivery.databinding.ActivityPaymentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    Button checkoutBtn;
    TextView totalPrice;
    RecyclerView recyclerView;
    ArrayList<Cart> mListCart;
    long total;
    private ActivityPaymentBinding binding;
    private CheckoutAdapter checkoutAdapter;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                Cart cart = doc.toObject(Cart.class);
                                total += cart.getTotalPrice();
                                totalPrice.setText(String.valueOf(total));
                                mListCart.add(cart);
                                checkoutAdapter.notifyDataSetChanged();

                            }
                        }
                    }
                });

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> order = new HashMap<>();
                order.put("status", "Đã đặt");
                order.put("totalPrice", total);
                order.put("userID", auth.getCurrentUser().getUid());
                firestore.collection("Order")
                        .add(order)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(PaymentActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                                deleteAddToCart();
                                startActivity(new Intent(PaymentActivity.this, App.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(PaymentActivity.this, "Đặt hàng thất bại", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void deleteAddToCart() {
        firestore.collection("AddToCart")
                .document(auth.getCurrentUser().getUid())
                .collection("User")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                doc.getReference().delete();
                            }
                        }
                    }
                });
    }
}