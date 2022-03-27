package com.example.arfurniture.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.arfurniture.R;
import com.example.arfurniture.models.CatergoryModel;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {
    Context context;
    String TAG = "adapter";
    List<CatergoryModel> catergoryList;

    public CategoriesAdapter(List<CatergoryModel> catergoryList)
    {
        this.catergoryList =catergoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_layout, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        holder.pName.setText(catergoryList.get(position).getName());
        holder.pPrice.setText("Rs. "+String.valueOf(catergoryList.get(position).getPrice()));
        Glide.with(context).asDrawable().load(catergoryList.get(position).getImage()).into(holder.pImage);
        /*if (this.pos == 0) {
            holder.pImage.setImageResource(R.drawable.grey_chair);
        } else if (this.pos == 1) {
            holder.pImage.setImageResource(R.drawable.wardrobe);
        } else if (this.pos == 2) {
            holder.pImage.setImageResource(R.drawable.sofa);
        } else if (this.pos == 3) {
            holder.pImage.setImageResource(R.drawable.table);
        } else if (this.pos == 4) {
            holder.pImage.setImageResource(R.drawable.bed);
        } else if (this.pos == 5) {
            holder.pImage.setImageResource(R.drawable.lamp);
        }*/
    }

    @Override
    public int getItemCount() {
        return catergoryList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView pImage;
        TextView pName, pPrice;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            pImage = itemView.findViewById(R.id.iv_pImage);
            pName = itemView.findViewById(R.id.tv_pName);
            pPrice = itemView.findViewById(R.id.tv_pPrice);
        }

    }
}
