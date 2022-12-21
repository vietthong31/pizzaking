package com.example.fooddelivery.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddelivery.Adapter.FoodAdapter;
import com.example.fooddelivery.Model.Food;
import com.example.fooddelivery.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    RecyclerView recyclerView;
    FoodAdapter foodAdapter;
    ArrayList<Food> mListFood;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        anhxa();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        mListFood = new ArrayList<>();

        clearAll();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Food");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                clearAll();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Food food = snapshot.getValue(Food.class);
                    //food.setFoodName(snapshot.get);
                    //food.setImgUrl(snapshot.getValue().toString());
                    //food.setPrice(snapshot.getValue().toString());
                    mListFood.add(food);
                }
                foodAdapter = new FoodAdapter(getActivity(), mListFood);
                recyclerView.setAdapter(foodAdapter);
                foodAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error :" +error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void clearAll() {
        if(mListFood != null){
            mListFood.clear();
            if(foodAdapter != null){
                foodAdapter.notifyDataSetChanged();
            }
        }
        mListFood = new ArrayList<>();
    }


    private void anhxa(){
        recyclerView = binding.recycleView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}