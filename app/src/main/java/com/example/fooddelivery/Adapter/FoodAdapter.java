package com.example.fooddelivery.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fooddelivery.Model.Food;
import com.example.fooddelivery.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class FoodAdapter extends FirebaseRecyclerAdapter<Food,FoodAdapter.foodViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FoodAdapter(@NonNull FirebaseRecyclerOptions<Food> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull foodViewHolder holder, int position, @NonNull Food model) {
        holder.textFoodName.setText(model.getFoodName());
        holder.textPrice.setText(model.getPrice());

        Glide.with(holder.img.getContext())
                .load(model.getImgUrl())
                .placeholder(com.firebase.ui.storage.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.storage.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);
    }

    @NonNull
    @Override
    public foodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new foodViewHolder(v);
    }

    class  foodViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView textFoodName, textPrice;

        public foodViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView) itemView.findViewById(R.id.Img);
            textFoodName = (TextView) itemView.findViewById(R.id.FoodName);
            textPrice = (TextView) itemView.findViewById(R.id.Price);
        }
    }
}
