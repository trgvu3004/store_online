package com.example.store_online.product;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

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
import java.util.Objects;

public class ProductSeenActivity extends AppCompatActivity {
    private RecyclerView rvProductSeen;
    private ArrayList<ProductsSeen> productsSeenArrayList;
    private ProductSeenAdapter productSeenAdapter;
    private ArrayList<String> mKey = new ArrayList<>();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_seen);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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
                mKey.add(snapshot.getKey());
                productSeenAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String key = snapshot.getKey();
                int index= mKey.indexOf(key);
                productsSeenArrayList.set(index,snapshot.getValue(ProductsSeen.class));
                productSeenAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String key = snapshot.getKey();
                int index= mKey.indexOf(key);
                productsSeenArrayList.remove(index);
                mKey.remove(index);
                productSeenAdapter.notifyDataSetChanged();
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}