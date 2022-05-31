package com.example.store_online.profile.address;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.store_online.R;
import com.example.store_online.adapter.AddressAdapter;
import com.example.store_online.data_models.Address;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class AddressActivity extends AppCompatActivity {
    private RecyclerView rvListAddress;
    private AddressAdapter addressAdapter;
    private ConstraintLayout layoutAddAddress;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        setTitle("Sổ Địa Chỉ");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mapping();
        //get list address form firebase
        ArrayList<Address> addressArrayList = getDataAddress();
        addressAdapter = new AddressAdapter(this, addressArrayList);
        LinearLayoutManager addressLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvListAddress.setLayoutManager(addressLayoutManager);
        rvListAddress.setAdapter(addressAdapter);
        //
        action();
    }

    private void action() {
        layoutAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddAddressActivity.class));
            }
        });
    }

    private ArrayList<Address> getDataAddress() {
        ArrayList<Address> addressArrayList = new ArrayList<>();
        ArrayList<String> mKey = new ArrayList<>();
        mRef.child("address").child(Objects.requireNonNull(mAuth.getUid())).addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Address address = snapshot.getValue(Address.class);
                addressArrayList.add(address);
                mKey.add(snapshot.getKey());
                addressAdapter.notifyDataSetChanged();
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String key = snapshot.getKey();
                int index = mKey.indexOf(key);
                addressArrayList.set(index, snapshot.getValue(Address.class));
                addressAdapter.notifyDataSetChanged();
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String key = snapshot.getKey();
                int index = mKey.indexOf(key);
                addressArrayList.remove(index);
                mKey.remove(key);
                addressAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return addressArrayList;
    }

    private void mapping() {
        rvListAddress = findViewById(R.id.rvListAddress);
        layoutAddAddress = findViewById(R.id.layoutAddAddress);
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}