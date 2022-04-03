package com.example.arfurniture.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.arfurniture.ProductDescriptionActivity;
import com.example.arfurniture.R;
import com.example.arfurniture.models.CatergoryModel;

import java.io.Serializable;
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
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.pName.setText(catergoryList.get(position).getName());
        holder.pPrice.setText("Rs. "+String.valueOf(catergoryList.get(position).getPrice()));
        Glide.with(context).asDrawable().load(catergoryList.get(position).getImage()).into(holder.pImage);
        holder.pCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                Log.d(TAG, "id= "+catergoryList.get(position).getId());

                Intent toProdDesc=new Intent(context, ProductDescriptionActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("CAT_LIST", (Serializable) catergoryList);
                toProdDesc.putExtras(bundle);
                toProdDesc.putExtra("position",position);
                context.startActivity(toProdDesc);

                //AppCompatActivity activity= (AppCompatActivity) view.getContext();
                //ProductDetailsFragment fragment=new ProductDetailsFragment();
                //activity.getSupportFragmentManager().beginTransaction().replace(R.id.rl_mainScreen,fragment).addToBackStack(null).commit();
            }
        });
    }

   /* @Override
   /* public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        holder.pName.setText(catergoryList.get(position).getName());
        holder.pPrice.setText("Rs. "+String.valueOf(catergoryList.get(position).getPrice()));
        Glide.with(context).asDrawable().load(catergoryList.get(position).getImage()).into(holder.pImage);
        holder.pCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                Log.d(TAG, "id= "+catergoryList.get(position).getId());

                Intent toProdDesc=new Intent(context, ProductDescriptionActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("CAT_LIST", (Serializable) catergoryList);
                toProdDesc.putExtras(bundle);
                toProdDesc.putExtra("position",position);
                context.startActivity(toProdDesc);

                //AppCompatActivity activity= (AppCompatActivity) view.getContext();
                //ProductDetailsFragment fragment=new ProductDetailsFragment();
                //activity.getSupportFragmentManager().beginTransaction().replace(R.id.rl_mainScreen,fragment).addToBackStack(null).commit();
            }
        });
    }*/

    @Override
    public int getItemCount() {
        return catergoryList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView pImage;
        TextView pName, pPrice;
        RelativeLayout pCard;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            pImage = itemView.findViewById(R.id.iv_pImage);
            pName = itemView.findViewById(R.id.tv_pName);
            pPrice = itemView.findViewById(R.id.tv_pPrice);
            pCard=itemView.findViewById(R.id.rl_productCard);
        }

    }
}
