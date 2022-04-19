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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.arfurniture.ProductDescriptionActivity;
import com.example.arfurniture.R;
import com.example.arfurniture.models.CatergoryModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartlistHolder> {
    Context context;
    String TAG = "WishlistAdapter";
    List<CatergoryModel> wishlistArrayList;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    public CartAdapter(List<CatergoryModel> wishlistArrayList) {
        this.wishlistArrayList=wishlistArrayList;
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public CartlistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new CartlistHolder(LayoutInflater.from(context).inflate(R.layout.item_cart_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartlistHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.wName.setText(wishlistArrayList.get(position).getName());
        holder.wPrice.setText("Price = ₹ "+wishlistArrayList.get(position).getPrice().toString());
        Glide.with(context).asDrawable().load(wishlistArrayList.get(position).getImage()).into(holder.wImage);
        holder.wCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toProdDesc=new Intent(context, ProductDescriptionActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("CAT_LIST", (Serializable) wishlistArrayList);
                toProdDesc.putExtras(bundle);
                toProdDesc.putExtra("position",position);
                Log.d(TAG, "onClick: i1="+wishlistArrayList.get(position).getI1());
                //toProdDesc.putExtra("index",1);//index = 1 for wishlist
                context.startActivity(toProdDesc);
            }
        });
        holder.wRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //holder.wRemove.setImageResource(R.drawable.ic_heart);
                firebaseFirestore.collection("USERS")
                        .document(firebaseUser.getUid())
                        .collection("MY CART" )
                        .document(wishlistArrayList.get(position).getName())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                wishlistArrayList.remove(wishlistArrayList.get(position));
                                notifyDataSetChanged();
                                Toast.makeText(context, "Item removed from Cart", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return wishlistArrayList.size();
    }

    public class CartlistHolder extends RecyclerView.ViewHolder{
        TextView wName;
        TextView wPrice;
        ImageView wImage,wRemove;
        CardView wCard;
        public CartlistHolder(@NonNull View itemView) {
            super(itemView);
            wName=itemView.findViewById(R.id.idTVCourseName);
            wPrice=itemView.findViewById(R.id.idTVCourseRating1);
            wImage=itemView.findViewById(R.id.idIVCourseImage);
            wCard=itemView.findViewById(R.id.cv_cart_item);
            wRemove=itemView.findViewById(R.id.iv_remove);
        }
    }
}
