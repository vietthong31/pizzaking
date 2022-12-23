package com.example.fooddelivery.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.fooddelivery.R;
import com.example.fooddelivery.databinding.ActivityFoodDetailBinding;
import com.example.fooddelivery.ui.cart.CartFragment;

public class Food_Detail extends AppCompatActivity implements View.OnClickListener {

    TextView txt_FoodName, txt_Price, txt_Des, numberOrdertxt;
    ImageView btnPlus, btnMinus;
    Button addToCartBtn;
    int Quantity = 1;

    ActivityFoodDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        anhxa();

        Intent i = this.getIntent();

        if(i != null){
            String foodName = i.getStringExtra("FoodName");
            String foodPrice = i.getStringExtra("Price");
            String foodImg = i.getStringExtra("Img");
//            String foodDes = i.getStringExtra("Des");

            binding.FoodName.setText(foodName);
            binding.Price.setText(foodPrice);
//            binding.descriptionTxt.setText(foodDes);

            Glide.with(this)
                    .load(foodImg)
                    .into(binding.Img);
        }

        numberOrdertxt = binding.numberOrderTxt;
        btnMinus = binding.minusBtn;
        btnPlus = binding.plusBtn;

        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);

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
        Intent intent = new Intent(Food_Detail.this, CartFragment.class);
        startActivity(intent);
    }

    private void Minus() {
        String price = txt_Price.getText().toString();
        int basePrice = Integer.valueOf(price);
        if(Quantity == 0){
            Toast.makeText(this, "Cant decrease quantity < 0", Toast.LENGTH_SHORT).show();
        }else{
            Quantity--;
            displayQuantity();
            int foodPrice = (basePrice * Quantity) - Integer.valueOf(price); // chua giam duoc so tien
            String newPrice = String.valueOf(foodPrice);
            txt_Price.setText(newPrice);
        }
        Log.d("Price Minus", String.valueOf(Quantity));
        Log.d("Price Minus", price);
    }

    private void Plus() {
        String price = txt_Price.getText().toString();
        int basePrice = Integer.valueOf(price);
        Quantity++;
        displayQuantity();
        int foodPrice = basePrice * Quantity;
        String newPrice = String.valueOf(foodPrice);
        txt_Price.setText(newPrice);

        Log.d("Price Plus", String.valueOf(Quantity));
        Log.d("Price Plus", price);

    }


    private void displayQuantity() {
        numberOrdertxt.setText(String.valueOf(Quantity));
    }


    private void anhxa(){
        txt_Price = findViewById(R.id.Price);
        addToCartBtn = findViewById(R.id.addToCartBtn);
    }
}