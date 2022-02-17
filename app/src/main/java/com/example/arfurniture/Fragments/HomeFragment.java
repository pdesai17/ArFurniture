package com.example.arfurniture.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arfurniture.Adapters.CategoriesAdapter;
import com.example.arfurniture.R;
import com.example.arfurniture.databinding.FragmentHomeBinding;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    String TAG="home";
    FragmentHomeBinding binding;
    CategoriesAdapter adapter;
    int tabIcons[]={R.drawable.ic_armchair,R.drawable.ic_wardrobe,R.drawable.ic_sofa,R.drawable.ic_table,R.drawable.ic_bed,R.drawable.ic_lamp};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: in home fragment");
        binding=FragmentHomeBinding.inflate(getLayoutInflater(),container,false);
        getPageTitle();
        setUpIcons();
        setUpRV();
        binding.tlCategorytabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabSelected: tab.getPosition()= "+tab.getPosition());
                setUpRV();
                adapter.setData(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return binding.getRoot();

    }

    private void setUpRV() {
        adapter=new CategoriesAdapter();
        binding.rvCategories.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.rvCategories.setAdapter(adapter);
    }

    private void setUpIcons() {
        binding.tlCategorytabs.getTabAt(0).setIcon(tabIcons[0]);
        binding.tlCategorytabs.getTabAt(1).setIcon(tabIcons[1]);
        binding.tlCategorytabs.getTabAt(2).setIcon(tabIcons[2]);
        binding.tlCategorytabs.getTabAt(3).setIcon(tabIcons[3]);
        binding.tlCategorytabs.getTabAt(4).setIcon(tabIcons[4]);
        binding.tlCategorytabs.getTabAt(5).setIcon(tabIcons[5]);
    }

    private void getPageTitle() {
        binding.tlCategorytabs.addTab(binding.tlCategorytabs.newTab().setText("Arm Chair"));
        binding.tlCategorytabs.addTab(binding.tlCategorytabs.newTab().setText("Wardrobe"));
        binding.tlCategorytabs.addTab(binding.tlCategorytabs.newTab().setText("Sofa"));
        binding.tlCategorytabs.addTab(binding.tlCategorytabs.newTab().setText("Table"));
        binding.tlCategorytabs.addTab(binding.tlCategorytabs.newTab().setText("Bed"));
        binding.tlCategorytabs.addTab(binding.tlCategorytabs.newTab().setText("Lamps"));
    }
}