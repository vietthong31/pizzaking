package com.example.fooddelivery;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddelivery.Adapter.FoodAdapter;
import com.example.fooddelivery.Model.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    RecyclerView recyclerView;
    FoodAdapter foodAdapter;
    ArrayList<Food> mListFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        anhxa();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mListFood = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Food");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                clearAll();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Food food = new Food();
                    food.setFoodName(snapshot.getValue().toString());
                    food.setImgUrl(snapshot.getValue().toString());
                    food.setPrice(snapshot.getValue().toString());

                    mListFood.add(food);
                }
                foodAdapter = new FoodAdapter(getApplicationContext(), mListFood);
                recyclerView.setAdapter(foodAdapter);
                foodAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void clearAll() {
    }


    private void anhxa(){
        recyclerView = findViewById(R.id.recycleView);
    }
}