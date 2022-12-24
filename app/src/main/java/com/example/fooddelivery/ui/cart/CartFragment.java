package com.example.fooddelivery.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fooddelivery.databinding.FragmentDashboardBinding;

public class CartFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private Button buttonCart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CartViewModel cartViewModel =
                new ViewModelProvider(this).get(CartViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        buttonCart = binding.button;
        View root = binding.getRoot();

//        final TextView textView = binding.textDashboard;
//        cartViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        buttonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}