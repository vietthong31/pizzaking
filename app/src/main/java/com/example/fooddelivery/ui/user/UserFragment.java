package com.example.fooddelivery.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fooddelivery.MainActivity;
import com.example.fooddelivery.Model.User;
import com.example.fooddelivery.databinding.FragmentUserBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserFragment extends Fragment {

    private FragmentUserBinding binding;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID, fullName, phone, address;
    private Button btnLogout;
    private Button btnUpdate;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        binding = FragmentUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        userID = user.getUid();

        btnLogout = binding.btnLogout;
        btnUpdate = binding.btnUpdate;

        // Đăng xuất
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        // Cập nhật thông tin
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean updated = false;
                String newFullName = binding.inputFullName.getText().toString();
                String newPhone = binding.inputPhone.getText().toString();
                String newAddress = binding.inputAddress.getText().toString();

                if (newFullName.isEmpty() || newPhone.isEmpty()) {
                    Toast.makeText(getActivity(), "Tên và số điện thoại không được để trống", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!fullName.equals(newFullName)) {
                    reference.child(userID).child("name").setValue(newFullName);
                    updated = true;
                }
                if (!phone.equals(newPhone)) {
                    reference.child(userID).child("phone").setValue(newPhone);
                    updated = true;
                }
                if (!address.equals(newAddress)) {
                    reference.child(userID).child("address").setValue(newAddress);
                    updated = true;
                }

                Toast.makeText(getActivity(), updated ? "Đã cập nhật" : "Dữ liệu không thay đổi", Toast.LENGTH_LONG).show();
            }
        });


        // Tải dữ liệu từ Firebase
        reference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    fullName = userProfile.name;
                    address = userProfile.address;
                    phone = userProfile.phone;
                    binding.inputFullName.setText(fullName);
                    binding.inputEmail.setText(userProfile.email);
                    binding.inputAddress.setText(address);
                    binding.inputPhone.setText(phone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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