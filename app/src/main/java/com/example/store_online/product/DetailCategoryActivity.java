package com.example.store_online.product;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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
import java.util.Objects;

public class DetailCategoryActivity extends AppCompatActivity {
    private ArrayList<Products> productsArrayList;
    private String id;
    private RecyclerView rvDetailCategory;
    private ArrayList<String> mKey = new ArrayList<>();
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_category);
        //set back
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //get value category
        getValueCategory();
        //mapping
        mapping();
        //
        productsArrayList = getProductsArrayList();
        productAdapter = new ProductAdapter(this,productsArrayList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        rvDetailCategory.setLayoutManager(gridLayoutManager);
        rvDetailCategory.setAdapter(productAdapter);

    }
    private void mapping(){
        rvDetailCategory = findViewById(R.id.rvDetailCategory);
    }
    private void getValueCategory(){
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        //Set category name for title
        setTitle(name);

    }
    private ArrayList<Products> getProductsArrayList() {
        ArrayList<Products> products = new ArrayList<>();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("products").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Products product = snapshot.getValue(Products.class);
                if(product.getCategory().equals(id)){
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}