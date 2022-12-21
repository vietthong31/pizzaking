package com.example.fooddelivery.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fooddelivery.GlideApp;
import com.example.fooddelivery.Model.Food;
import com.example.fooddelivery.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Food> mListFood;
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://fooddelivery-f3ed3.appspot.com");
    private StorageReference reference = storage.getReference();
    StorageReference logoReference = reference.child("images/logo.png");

    public FoodAdapter(Context context, ArrayList<Food> mListFood) {
        this.context = context;
        this.mListFood = mListFood;
    }

    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, int position) {
        Food f = mListFood.get(position);
        Glide.with(context)
                .load(mListFood.get(position).getImgUrl())
                .placeholder(com.firebase.ui.storage.R.drawable.common_google_signin_btn_icon_dark)
                .error(R.drawable.error)
                .into(holder.img);
        //GlideApp.with(context).load(logoReference).into(holder.img);

        holder.textFoodName.setText(f.getFoodName());
        holder.textPrice.setText(f.getPrice().toString());

    }

    @Override
    public int getItemCount() {
        return mListFood.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView textFoodName, textPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (CircleImageView) itemView.findViewById(R.id.Img);
            textFoodName = (TextView) itemView.findViewById(R.id.FoodName);
            textPrice = (TextView) itemView.findViewById(R.id.Price);

        }
    }
}
