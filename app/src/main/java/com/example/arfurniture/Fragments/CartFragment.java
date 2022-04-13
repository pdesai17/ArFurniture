package com.example.arfurniture.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.arfurniture.Adapters.WishlistAdapter;
import com.example.arfurniture.R;
import com.example.arfurniture.databinding.FragmentCartBinding;
import com.example.arfurniture.models.CatergoryModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {
    String TAG="CartFragment";
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FragmentCartBinding binding;
    ArrayList<CatergoryModel> wishlistArrayList;
    WishlistAdapter adapter;
    Long totalPrice= Long.valueOf(0);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentCartBinding.inflate(getLayoutInflater(),container,false);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();

        wishlistArrayList=new ArrayList<>();

        setUpRV();

        firebaseFirestore.collection("USERS")
                .document(firebaseUser.getUid())
                .collection("MY CART")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty())
                            {
                                Log.d(TAG, "onComplete: my cart ");
                                Toast.makeText(getContext(), "Wishlist Empty ", Toast.LENGTH_SHORT).show();
                            }else {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if (!document.get("name").toString().isEmpty())
                                    {
                                        Log.d(TAG, "wishlist " + document.getId() + " => " + document.getData());
                                        CatergoryModel wishlistModel = new CatergoryModel();
                                        wishlistModel.setName(document.get("name").toString());
                                        wishlistModel.setPrice((Long) document.get("price"));
                                        totalPrice = totalPrice + (Long) document.get("price");
                                        wishlistModel.setDescription(document.get("description").toString());
                                        wishlistModel.setImage(document.get("image").toString());
                                        wishlistArrayList.add(wishlistModel);
                                    }
                                }
                                binding.idTVCourseRating5.setText("₹ " + totalPrice);
                                adapter.notifyDataSetChanged();
                                Log.d(TAG, "onComplete: wishlist= " + wishlistArrayList);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        
        return binding.getRoot();
    }
    private void setUpRV() {
        adapter=new WishlistAdapter(wishlistArrayList);
        binding.rvCart.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvCart.setAdapter(adapter);
    }
}