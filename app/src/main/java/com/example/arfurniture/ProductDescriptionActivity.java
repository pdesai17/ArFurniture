package com.example.arfurniture;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.arfurniture.Adapters.SliderAdapter;
import com.example.arfurniture.databinding.ActivityProductDescriptionBinding;
import com.example.arfurniture.models.CatergoryModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDescriptionActivity extends AppCompatActivity {
    String TAG="ProductDescription";
    ActivityProductDescriptionBinding binding;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    private SliderAdapter adapter;
    private SliderView sliderView;
    Long size= Long.valueOf(0);
    List<String> prodImages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        sliderView = findViewById(R.id.sv_imagesSlider);

        Intent data = getIntent();
        Bundle bundle=getIntent().getExtras();
        prodImages=new ArrayList<>();
        List<CatergoryModel> catergoryList= (List<CatergoryModel>) bundle.getSerializable("CAT_LIST");
        int pos=data.getIntExtra("position",0);
        Log.d(TAG, "onCreate: pos= "+pos);
        Log.d(TAG, "onCreate: des= "+catergoryList.get(pos).getDescription());
        Log.d(TAG, "onCreate: i1= "+catergoryList.get(pos).getI1());

        prodImages.add(catergoryList.get(pos).getI1());
        prodImages.add(catergoryList.get(pos).getI2());
        prodImages.add(catergoryList.get(pos).getI3());
        prodImages.add(catergoryList.get(pos).getI4());

        Log.d(TAG, "onCreate: prodImages= "+prodImages);
        adapter = new SliderAdapter(prodImages);
        sliderView.setSliderAdapter(adapter);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.FILL);

        binding.productDsc.setText(catergoryList.get(pos).getDescription());
        binding.tvProductName.setText(catergoryList.get(pos).getName());
        binding.productPrice.setText(String.valueOf(catergoryList.get(pos).getPrice()));

        binding.btnWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference documentReference=firebaseFirestore
                        .collection("USERS")
                        .document(firebaseUser.getUid())
                        .collection("WISHLIST")
                        .document("prod_id");
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists())
                        {
                            Toast.makeText(ProductDescriptionActivity.this, documentSnapshot.get("size").toString(), Toast.LENGTH_SHORT).show();
                            size= (Long) documentSnapshot.get("size");
                            size++;
                            Log.d(TAG, "onSuccess: size= "+size);
                        }else {
                            Toast.makeText(ProductDescriptionActivity.this, "documentSnapshot not exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProductDescriptionActivity.this, "failure", Toast.LENGTH_SHORT).show();
                    }
                });

                Map<String,Object> map=new HashMap<>();
                map.put("prod_id"+size,catergoryList.get(pos).getId());
                map.put("size",  size);
                documentReference.set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete: ");
                    }
                });


            }
        });

    }
}