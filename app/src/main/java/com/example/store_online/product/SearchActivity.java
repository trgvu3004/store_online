package com.example.store_online.product;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.store_online.R;
import com.example.store_online.adapter.ProductAdapter;
import com.example.store_online.data_models.Products;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {
    private SearchView searchView;
    private RecyclerView rvSearch;
    private ArrayList<Products> productsArrayList;
    private ArrayList<String> mKey = new ArrayList<>();
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mapping();
        action();

    }
    private ArrayList<Products> getProductsArrayList(String query) {
        ArrayList<Products> products = new ArrayList<>();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("products").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Products product = snapshot.getValue(Products.class);
                if(product.getName().toLowerCase(Locale.ROOT).contains(query)){
                    products.add(product);
                }
                mKey.add(snapshot.getKey());
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String key = snapshot.getKey();
                int index = mKey.indexOf(key);
                productsArrayList.set(index, snapshot.getValue(Products.class));
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String key = snapshot.getKey();
                int index = mKey.indexOf(key);
                productsArrayList.remove(index);
                mKey.remove(index);
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return products;
    }
    private void action() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                productsArrayList = getProductsArrayList(query.toLowerCase(Locale.ROOT));
                //create Product adapter, set adapter for recycler view
                productAdapter = new ProductAdapter(getApplicationContext(), productsArrayList);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, RecyclerView.VERTICAL, false);
                rvSearch.setLayoutManager(gridLayoutManager);
                rvSearch.setAdapter(productAdapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void mapping() {
        searchView = findViewById(R.id.searchView);
        rvSearch = findViewById(R.id.rvSearch);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}