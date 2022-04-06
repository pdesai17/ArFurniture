package com.example.arfurniture;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.arfurniture.Adapters.SliderAdapter;
import com.example.arfurniture.databinding.ActivityProductDescriptionBinding;
import com.example.arfurniture.models.CatergoryModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class ProductDescriptionActivity extends AppCompatActivity {
    String TAG="ProductDescription";
    ActivityProductDescriptionBinding binding;
    FirebaseFirestore firebaseFirestore;
    private SliderAdapter adapter;
    private SliderView sliderView;
    List<String> prodImages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore = FirebaseFirestore.getInstance();
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



    }
}