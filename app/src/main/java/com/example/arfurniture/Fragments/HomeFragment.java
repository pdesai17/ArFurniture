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
import com.example.arfurniture.models.CatergoryModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    String TAG = "home";
    FragmentHomeBinding binding;
    CategoriesAdapter adapter;
    List<CatergoryModel> catergoryList;
    GridLayoutManager gridLayoutManager;
    List<String> productNameList;
    FirebaseFirestore firebaseFirestore;
    Query query=null;
    int tabIcons[] = {R.drawable.ic_armchair, R.drawable.ic_wardrobe, R.drawable.ic_sofa, R.drawable.ic_table, R.drawable.ic_bed, R.drawable.ic_lamp};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: in home fragment");
        catergoryList=new ArrayList<>();
        productNameList=new ArrayList<>();
        gridLayoutManager=new GridLayoutManager(getContext(),2);
        binding = FragmentHomeBinding.inflate(getLayoutInflater(), container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        getPageTitle();
        setUpIcons();
        setUpRV();
        retrieveProductName();
        retrieveData(0);
        Log.d(TAG, "onCreateView:productNameList= "+productNameList);
        binding.tlCategorytabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabSelected: tab.getPosition()= " + tab.getPosition());
                retrieveData(tab.getPosition());
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

    private void retrieveProductName() {
        DocumentReference documentReference= firebaseFirestore.collection("CATEGORIES").document("cat_doc");
        query = documentReference.collection("ARMCHAIR").orderBy("index", Query.Direction.ASCENDING);
        addProductName(query);
        query = documentReference.collection("WARDROBE").orderBy("index", Query.Direction.ASCENDING);
        addProductName(query);
        query = documentReference.collection("SOFA").orderBy("index", Query.Direction.ASCENDING);
        addProductName(query);
        query = documentReference.collection("TABLE").orderBy("index", Query.Direction.ASCENDING);
        addProductName(query);
        query = documentReference.collection("BED").orderBy("index", Query.Direction.ASCENDING);
        addProductName(query);
        query = documentReference.collection("LAMPS").orderBy("index", Query.Direction.ASCENDING);
        addProductName(query);
        Log.d(TAG, "retrieveProductName: productNameList= "+productNameList);
    }

    private void addProductName(Query query) {
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d:list)
                {
                    CatergoryModel obj=d.toObject(CatergoryModel.class);
                    productNameList.add(obj.getName());
                }
                Log.d(TAG, "onSuccess:productNameList= "+productNameList.size() );

            }

        });
    }

    private void retrieveData(int tabPos) {
        DocumentReference documentReference= firebaseFirestore.collection("CATEGORIES").document("cat_doc");
        Query query = null;
        switch (tabPos){
            case 0:
                Log.d(TAG, "setUpRV: case 0");
                query = documentReference.collection("ARMCHAIR").orderBy("index", Query.Direction.ASCENDING);
                break;
            case 1:
                Log.d(TAG, "setUpRV: case 1");
                query = documentReference.collection("WARDROBE").orderBy("index", Query.Direction.ASCENDING);
                break;
            case 2:
                Log.d(TAG, "setUpRV: case 2");
                query = documentReference.collection("SOFA").orderBy("index", Query.Direction.ASCENDING);
                break;
            case 3:
                Log.d(TAG, "setUpRV: case 3");
                query = documentReference.collection("TABLE").orderBy("index", Query.Direction.ASCENDING);
                break;
            case 4:
                Log.d(TAG, "setUpRV: case 4");
                query = documentReference.collection("BED").orderBy("index", Query.Direction.ASCENDING);
                break;
            case 5:
                Log.d(TAG, "setUpRV: case 5");
                query = documentReference.collection("LAMPS").orderBy("index", Query.Direction.ASCENDING);
                break;
        }
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                catergoryList.clear();
                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d:list)
                {
                    CatergoryModel obj=d.toObject(CatergoryModel.class);
                    catergoryList.add(obj);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void getPageTitle () {
        binding.tlCategorytabs.addTab(binding.tlCategorytabs.newTab().setText("Arm Chair"));
        binding.tlCategorytabs.addTab(binding.tlCategorytabs.newTab().setText("Wardrobe"));
        binding.tlCategorytabs.addTab(binding.tlCategorytabs.newTab().setText("Sofa"));
        binding.tlCategorytabs.addTab(binding.tlCategorytabs.newTab().setText("Table"));
        binding.tlCategorytabs.addTab(binding.tlCategorytabs.newTab().setText("Bed"));
        binding.tlCategorytabs.addTab(binding.tlCategorytabs.newTab().setText("Lamps"));
    }
    private void setUpIcons () {
        binding.tlCategorytabs.getTabAt(0).setIcon(tabIcons[0]);
        binding.tlCategorytabs.getTabAt(1).setIcon(tabIcons[1]);
        binding.tlCategorytabs.getTabAt(2).setIcon(tabIcons[2]);
        binding.tlCategorytabs.getTabAt(3).setIcon(tabIcons[3]);
        binding.tlCategorytabs.getTabAt(4).setIcon(tabIcons[4]);
        binding.tlCategorytabs.getTabAt(5).setIcon(tabIcons[5]);
    }
    private void setUpRV() {
        adapter=new CategoriesAdapter(catergoryList);
        binding.rvCategories.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.rvCategories.setAdapter(adapter);


       /* binding.progressBar.setVisibility(View.VISIBLE);
        Log.d(TAG, "setUpRV: progress bar visible");

        DocumentReference documentReference=firebaseFirestore.collection("CATEGORIES").document("cat_doc");
        Query query = null;
        switch (tabPos){
            case 0:
                Log.d(TAG, "setUpRV: case 0");
                query = documentReference.collection("ARMCHAIR").orderBy("index", Query.Direction.ASCENDING);
                break;
            case 1:
                Log.d(TAG, "setUpRV: case 1");
                query = documentReference.collection("WARDROBE").orderBy("index", Query.Direction.ASCENDING);
                break;
            case 2:
                Log.d(TAG, "setUpRV: case 2");
                query = documentReference.collection("SOFA").orderBy("index", Query.Direction.ASCENDING);
                break;
            case 3:
                Log.d(TAG, "setUpRV: case 3");
                query = documentReference.collection("TABLE").orderBy("index", Query.Direction.ASCENDING);
                break;
            case 4:
                Log.d(TAG, "setUpRV: case 4");
                query = documentReference.collection("BED").orderBy("index", Query.Direction.ASCENDING);
                break;
            case 5:
                Log.d(TAG, "setUpRV: case 5");
                query = documentReference.collection("LAMPS").orderBy("index", Query.Direction.ASCENDING);
                break;
        }
        FirestoreRecyclerOptions<CatergoryModel> allNotes = new FirestoreRecyclerOptions.Builder<CatergoryModel>().setQuery(query, CatergoryModel.class).build();
                Log.d(TAG, "setUpRV: allNotes= "+allNotes);
                catergoryAdapter = new FirestoreRecyclerAdapter<CatergoryModel, CategoryViewHolder>(allNotes) {
                    @NonNull
                    @Override
                    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        Log.d(TAG, "onCreateViewHolder: ");
                        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_layout,parent,false));
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull CategoryViewHolder holder, int position, @NonNull CatergoryModel model) {
                        Log.d(TAG, "onBindViewHolder: price "+model.getPrice());
                        holder.pName.setText(model.getName());
                        holder.pPrice.setText("Rs. "+String.valueOf(model.getPrice()));
                        Glide.with(getContext()).asDrawable().load(model.getImage()).into(holder.pImage);
                        Log.d(TAG, "onBindViewHolder: "+model.getImage());
                        binding.progressBar.setVisibility(View.GONE);
                    }
                };

        Log.d(TAG, "setUpRV: "+query);

        Log.d(TAG, "setUpRV: grid");
        binding.rvCategories.setLayoutManager(gridLayoutManager);
        binding.rvCategories.setAdapter(catergoryAdapter);*/


    }

    /*class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView pImage;
        TextView pName, pPrice;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            pImage = itemView.findViewById(R.id.iv_pImage);
            pName = itemView.findViewById(R.id.tv_pName);
            pPrice = itemView.findViewById(R.id.tv_pPrice);
        }

    }*/

    /*@Override
    public void onStart() {
        super.onStart();
        catergoryAdapter.startListening();
    }*/

    /*@Override
    public void onStop() {
        super.onStop();
        if (catergoryAdapter != null) {
            catergoryAdapter.stopListening();
        }
    }*/
}
