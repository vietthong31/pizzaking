package com.example.fooddelivery.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddelivery.Model.Cart;
import com.example.fooddelivery.R;
import com.google.api.Context;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context context;
    ArrayList<Cart> mListCart;

    public CartAdapter(Context context, ArrayList<Cart> mListCart) {
        this.context = context;
        this.mListCart = mListCart;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        Cart cart = mListCart.get(position);

//        Glide.with(context)
//                .load(mListCart.get(position).getImgCart())
//                .error(R.drawable.error)
//                .into(holder.imgCart);

        holder.date.setText(mListCart.get(position).getCurrentDate());
        holder.time.setText(mListCart.get(position).GetCurrentTime());
        holder.fNameCart.setText(mListCart.get(position).getfNameCart());
        holder.priceCart.setText(mListCart.get(position).getfPriceCart());
        holder.totalPrice.setText(String.valueOf(mListCart.get(position).getTotalPrice()));
        holder.totalQuantity.setText(mListCart.get(position).getTotalQuantity());

    }

    @Override
    public int getItemCount() {
        return mListCart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, time, fNameCart, priceCart, totalQuantity, totalPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            fNameCart = itemView.findViewById(R.id.FoodName);
            priceCart = itemView.findViewById(R.id.Price);
            totalPrice = itemView.findViewById(R.id.total_price);
            totalQuantity = itemView.findViewById(R.id.total_quantity);
        }
    }
}
