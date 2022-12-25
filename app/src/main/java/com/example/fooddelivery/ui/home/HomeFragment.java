package com.example.fooddelivery.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddelivery.Adapter.FoodAdapter;
import com.example.fooddelivery.Model.Food;
import com.example.fooddelivery.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Food");

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

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                clearAll();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Food food = snapshot.getValue(Food.class);
                    mListFood.add(food);
                    foodAdapter = new FoodAdapter(getActivity(), mListFood);
                    recyclerView.setAdapter(foodAdapter);
                    foodAdapter.notifyDataSetChanged();
                }
                binding.result.setText(mListFood.size() + " sản phẩm");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error :" +error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = binding.inputSearch.getText().toString();
                Log.d("SEARCH INPUT", input);
                find(input);
                closeKeyboard();
            }
        });

        return root;
    }

    private void find(String foodName) {
        mListFood.clear();
        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Log.d("FIND", String.valueOf(task.getResult().getChildrenCount()));
                for (DataSnapshot snapshot: task.getResult().getChildren()) {
                    Food food = snapshot.getValue(Food.class);
                    if (food.getFoodName().trim().toLowerCase().contains(foodName.trim().toLowerCase())) {
                        mListFood.add(food);
                    }
                }
                binding.result.setText(mListFood.size() + " sản phẩm");
                foodAdapter = new FoodAdapter(getActivity(), mListFood);
                recyclerView.setAdapter(foodAdapter);
            }
        });
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

    private void closeKeyboard()
    {
        // get current focused view
        View view = getActivity().getCurrentFocus();

        // hide if view is exist
        if (view != null) {

            // now assign the system service to InputMethodManager
            InputMethodManager manager
                    = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            manager
                    .hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
        }
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