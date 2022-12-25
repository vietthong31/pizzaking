package com.example.fooddelivery.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.fooddelivery.R;
import com.example.fooddelivery.databinding.ActivityFoodDetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Food_Detail extends AppCompatActivity implements View.OnClickListener {

    TextView txt_FoodName, txt_Price, txt_Des, numberOrdertxt;
    ImageView btnPlus, btnMinus;
    Button addToCartBtn;
    int Quantity = 1;
    int basePrice;
    int totalPrice;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;


    ActivityFoodDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        anhxa();

        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        Intent i = this.getIntent();

        if(i != null){
            String foodName = i.getStringExtra("FoodName");
            String foodPrice = i.getStringExtra("Price");
            String foodImg = i.getStringExtra("Img");
            String foodDes = i.getStringExtra("Des");
            basePrice = Integer.valueOf(foodPrice);

            binding.FoodName.setText(foodName);
            binding.Price.setText(foodPrice);
            binding.descriptionTxt.setText(foodDes);

            Glide.with(this)
                    .load(foodImg)
                    .into(binding.Img);
        }

        numberOrdertxt = binding.numberOrderTxt;
        btnMinus = binding.minusBtn;
        btnPlus = binding.plusBtn;
        txt_FoodName = binding.FoodName;

        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        addToCartBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.minusBtn:
                Minus();
                break;
            case  R.id.plusBtn:
                Plus();
                break;
            case R.id.addToCartBtn:
                AddtoCart();
                break;
        }

    }

    private void AddtoCart() {
//        Intent intent = new Intent(Food_Detail.this, CartFragment.class);
//        startActivity(intent);

        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM,dd,yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH::mm::ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("FoodName",txt_FoodName.getText().toString());
        cartMap.put("Price",basePrice);
        cartMap.put("TotalQuantity",numberOrdertxt.getText().toString());
        cartMap.put("TotalPrice", totalPrice);
        cartMap.put("CurrentTime", saveCurrentTime);
        cartMap.put("CurrentDate", saveCurrentDate);

        firestore.collection("AddToCart").document(mAuth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(Food_Detail.this, "Added To A Cart", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private void Minus() {
        if(Quantity == 1){
            Toast.makeText(this, "Cant decrease quantity < 0", Toast.LENGTH_SHORT).show();
        }else{
            int newPrice = (basePrice * Quantity) - Integer.valueOf(basePrice); // chua giam duoc so tien
            Quantity--;
            displayQuantity();
            txt_Price.setText(String.valueOf(newPrice));
            totalPrice = newPrice;
            Log.d("Price Minus", String.valueOf(Quantity));
            Log.d("Price Minus", String.valueOf(newPrice));
        }

    }

    private void Plus() {
        Quantity++;
        displayQuantity();
        int foodPrice = basePrice * Quantity;
        String newPrice = String.valueOf(foodPrice);
        txt_Price.setText(newPrice);
        totalPrice = foodPrice;
        Log.d("Price Plus", String.valueOf(Quantity));
        Log.d("Price Plus", newPrice);

    }


    private void displayQuantity() {
        numberOrdertxt.setText(String.valueOf(Quantity));
    }


    private void anhxa(){
//        txt_FoodName = findViewById(R.id.FoodName);
//        txt_Des = findViewById(R.id.Price);
        txt_Price = findViewById(R.id.Price);
        addToCartBtn = findViewById(R.id.addToCartBtn);
    }
}