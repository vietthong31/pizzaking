package com.example.fooddelivery.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fooddelivery.R;

public class Food_Detail extends AppCompatActivity {

    TextView txt_FoodName, txt_Price, txt_Des;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        anhxa();


    }

    private void anhxa(){

    }
}