package com.example.arfurniture.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arfurniture.Adapters.CategoriesAdapter;
import com.example.arfurniture.Adapters.WishlistAdapter;
import com.example.arfurniture.R;
import com.example.arfurniture.databinding.FragmentHomeBinding;
import com.example.arfurniture.databinding.FragmentLikeBinding;
import com.example.arfurniture.models.CatergoryModel;
import com.example.arfurniture.models.WishlistModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LikeFragment#} factory method to
 * create an instance of this fragment.
 */
public class LikeFragment extends Fragment {
    String TAG="LikeFragment";
    RecyclerView RVCourse;
    FragmentLikeBinding binding;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ArrayList<WishlistModel> wishlistArrayList;
    WishlistAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLikeBinding.inflate(getLayoutInflater(), container, false);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();

        wishlistArrayList=new ArrayList<>();

        setUpRV();

        firebaseFirestore.collection("USERS").document(firebaseUser.getUid())
                .collection("WISHLIST")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "wishlist "+document.getId() + " => " + document.getData());
                                WishlistModel wishlistModel=new WishlistModel();
                                wishlistModel.setName(document.get("name").toString());
                                wishlistModel.setPrice((Long) document.get("price"));
                                wishlistModel.setDescription(document.get("description").toString());
                                wishlistModel.setImage(document.get("image").toString());
                                wishlistArrayList.add(wishlistModel);
                            }
                            adapter.notifyDataSetChanged();
                            Log.d(TAG, "onComplete: wishlist= "+wishlistArrayList);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        //Log.d(TAG, "onCreateView: wishlist array= "+wishlistArrayList);
        return binding.getRoot();
    }

    private void setUpRV() {
        adapter=new WishlistAdapter(wishlistArrayList);
        binding.idRVCourse.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.idRVCourse.setAdapter(adapter);
    }
}

