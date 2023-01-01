package com.example.fooddelivery.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddelivery.Model.Cart;
import com.example.fooddelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context context;
    ArrayList<Cart> mListCart;
    long totalAmount = 0;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public CartAdapter(Context context, ArrayList<Cart> mListCart) {
        this.context = context;
        this.mListCart = mListCart;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Cart cart = mListCart.get(position);

//        Glide.with(context)
//                .load(mListCart.get(position).getImgCart())
//                .error(R.drawable.error)
//                .into(holder.imgCart);

        holder.date.setText(mListCart.get(position).getCurrentDate());
        holder.time.setText(mListCart.get(position).getCurrentTime());
        holder.fNameCart.setText(mListCart.get(position).getFoodName());
        holder.priceCart.setText(String.valueOf(mListCart.get(position).getPrice()));
        holder.totalPrice.setText(String.valueOf(mListCart.get(position).getTotalPrice()));
        holder.totalQuantity.setText(mListCart.get(position).getTotalQuantity());

        //Click delete item in Cart
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                        .collection("User")
                        .document(mListCart.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    mListCart.remove(mListCart.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Xoa hang ", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        //Total Amount pass to CartFragment
//        totalAmount = totalAmount + mListCart.get(position).getTotalPrice();
//        Intent intent = new Intent("MyTotalAmount");
//        intent.putExtra("totalAmount", totalAmount);
//
//        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return mListCart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, time, fNameCart, priceCart, totalQuantity, totalPrice;
        ImageView delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            fNameCart = itemView.findViewById(R.id.food_name);
            priceCart = itemView.findViewById(R.id.food_price);
            totalPrice = itemView.findViewById(R.id.total_price);
            totalQuantity = itemView.findViewById(R.id.total_quantity);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
