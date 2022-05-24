package com.example.store_online.product;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.store_online.R;
import com.example.store_online.adapter.CartAdapter;
import com.example.store_online.data_models.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class CartActivity extends AppCompatActivity {
    private RecyclerView rvCart;
    private ArrayList<Cart> cartArrayList;
    private FirebaseAuth mAuth;
    private CartAdapter cartAdapter;
    private ArrayList<String> mKey = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //mapping
        mapping();
        //
        cartArrayList = getValueCart();
        cartAdapter = new CartAdapter(this,cartArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvCart.setLayoutManager(linearLayoutManager);
        rvCart.setAdapter(cartAdapter);

    }

    private ArrayList<Cart> getValueCart() {
        ArrayList<Cart> cartArrayList = new ArrayList<>();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("cart").child(Objects.requireNonNull(mAuth.getUid())).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Cart cart = snapshot.getValue(Cart.class);
                cartArrayList.add(cart);
                mKey.add(snapshot.getKey());
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String key = snapshot.getKey();
                int index = mKey.indexOf(key);
                cartArrayList.set(index,snapshot.getValue(Cart.class));
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String key = snapshot.getKey();
                int index = mKey.indexOf(key);
                cartArrayList.remove(index);
                mKey.remove(index);
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return cartArrayList;
    }

    private void mapping() {
        mAuth = FirebaseAuth.getInstance();
        rvCart = findViewById(R.id.rvCart);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}