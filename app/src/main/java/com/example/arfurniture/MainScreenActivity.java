package com.example.arfurniture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import com.example.arfurniture.Fragments.CartFragment;
import com.example.arfurniture.Fragments.HomeFragment;
import com.example.arfurniture.Fragments.LikeFragment;
import com.example.arfurniture.Fragments.ProfileFragment;
import com.example.arfurniture.databinding.ActivityMainScreenBinding;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainScreenActivity extends AppCompatActivity {
    String TAG="MainScreen";
    ActivityMainScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.cnpBottomNavigation.setItemSelected(R.id.home,true);
        Log.d(TAG, "onCreate: item selected");
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,new HomeFragment()).commit();
        binding.cnpBottomNavigation.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment=null;
                switch (i)
                {
                    case R.id.home:
                        fragment=new HomeFragment();
                        break;
                    case R.id.like:
                        fragment=new LikeFragment();
                        break;
                    case R.id.cart:
                        fragment=new CartFragment();
                        break;
                    case R.id.profile:
                        fragment=new ProfileFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,fragment).commit();
                Log.d(TAG, "onItemSelected: replaced");
            }
        });
    }
}