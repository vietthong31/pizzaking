package com.example.fooddelivery.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

        holder.fNameCart.setText(cart.getfNameCart());
        holder.priceCart.setText(cart.getPriceCart().toString());

    }

    @Override
    public int getItemCount() {
        return mListCart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCart;
        TextView fNameCart, priceCart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCart = itemView.findViewById(R.id.Img);
            fNameCart = itemView.findViewById(R.id.FoodName);
            priceCart = itemView.findViewById(R.id.Price);
        }
    }
}
