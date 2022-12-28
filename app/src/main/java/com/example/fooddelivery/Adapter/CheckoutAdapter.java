package com.example.fooddelivery.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddelivery.Model.Cart;
import com.example.fooddelivery.R;

import java.util.ArrayList;


public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder> {

    Context context;
    ArrayList<Cart> mListCart;

    public CheckoutAdapter(Context context, ArrayList<Cart> mListCart) {
        this.context = context;
        this.mListCart = mListCart;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart = mListCart.get(position);

        holder.fNameCart.setText(mListCart.get(position).getFoodName());
        //holder.totalPrice.setText(String.valueOf(mListCart.get(position).getTotalPrice()));
        holder.totalQuantity.setText(mListCart.get(position).getTotalQuantity());
    }

    @Override
    public int getItemCount() {
        return mListCart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fNameCart, totalPrice, totalQuantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fNameCart = itemView.findViewById(R.id.foodName);
            totalQuantity = itemView.findViewById(R.id.quantity);
        }
    }
}
