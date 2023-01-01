package com.example.fooddelivery.ui.cart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddelivery.Activity.AddressActivity;
import com.example.fooddelivery.Adapter.CartAdapter;
import com.example.fooddelivery.Model.Cart;
import com.example.fooddelivery.databinding.FragmentDashboardBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private Button buttonCart;
    RecyclerView recyclerView;
    ArrayList<Cart> mListCart;
    CartAdapter cartAdapter;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private FirebaseDatabase firebaseDatabase;
    TextView overAllMount;
    long overAllTotalAmount;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CartViewModel cartViewModel =
                new ViewModelProvider(this).get(CartViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);

        buttonCart = binding.button;

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        overAllMount = binding.textView2;
        recyclerView = binding.cartRcv;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//
        recyclerView.setHasFixedSize(true);

        mListCart = new ArrayList<>();
        cartAdapter = new CartAdapter(getActivity(), mListCart);
        recyclerView.setAdapter(cartAdapter);

        //get data from carAdapter
//        LocalBroadcastManager.getInstance(getActivity())
//                        .registerReceiver(mMessageReceiver, new IntentFilter("MyTotalAmount"));

        //get data from FirebaseStore
        firestore.collection("AddToCart").document(mAuth.getCurrentUser().getUid())
                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (DocumentSnapshot doc : task.getResult().getDocuments()){

                                String documentId = doc.getId();

                                Cart cart = doc.toObject(Cart.class);
                                cart.setDocumentId(documentId);
                                overAllTotalAmount += cart.getTotalPrice();
                                overAllMount.setText(String.valueOf(overAllTotalAmount) + "Đ");
                                Log.d("Cart", String.valueOf(overAllMount));
                                mListCart.add(cart);
                                cartAdapter.notifyDataSetChanged();

                                Log.d("ListCart", cart == null ? "Yes" : "No");
                            }
                        }
                    }
                });


        View root = binding.getRoot();


//        final TextView textView = binding.textDashboard;
//        cartViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        buttonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddressActivity.class));
            }
        });
        return root;
    }

//
//    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            long totalBill = intent.getLongExtra("totalAmount", 0);
//            overAllMount.setText(totalBill + "Đ");
//        }
//    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}