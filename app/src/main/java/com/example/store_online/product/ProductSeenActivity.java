package com.example.store_online.product;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.store_online.R;
import com.example.store_online.adapter.ProductSeenAdapter;
import com.example.store_online.data_models.ProductsSeen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProductSeenActivity extends AppCompatActivity {
    private RecyclerView rvProductSeen;
    private ArrayList<ProductsSeen> productsSeenArrayList;
    private ProductSeenAdapter productSeenAdapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_seen);
        //mapping view
        mapping();
        //load product seen
        productsSeenArrayList = getValueProductSeen();
        productSeenAdapter = new ProductSeenAdapter(this,productsSeenArrayList);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rvProductSeen.setLayoutManager(linearLayoutManager);
        rvProductSeen.setAdapter(productSeenAdapter);
    }

    private ArrayList<ProductsSeen> getValueProductSeen() {
        ArrayList<ProductsSeen> productsSeen = new ArrayList<>();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("products_seen").child(mAuth.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ProductsSeen products = snapshot.getValue(ProductsSeen.class);
                productsSeen.add(products);
                productSeenAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return productsSeen;
    }

    private void mapping() {
        mAuth = FirebaseAuth.getInstance();
        rvProductSeen = findViewById(R.id.rvProductSeen);
    }
}