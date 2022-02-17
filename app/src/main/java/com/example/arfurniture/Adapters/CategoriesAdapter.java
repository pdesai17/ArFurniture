package com.example.arfurniture.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arfurniture.R;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {
    String TAG = "adapter";
    int pos;

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_layout, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Log.d(TAG, "bind: pos= " + this.pos);
        if (this.pos == 0) {
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
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public void setData(int pos) {
        this.pos = pos;
        Log.d(TAG, "setData: this.pos= " + this.pos);
        notifyItemInserted(10 - 1);
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
