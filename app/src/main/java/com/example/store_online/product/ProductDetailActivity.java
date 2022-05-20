package com.example.store_online.product;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;

import com.bumptech.glide.Glide;
import com.example.store_online.R;
import com.example.store_online.data_models.Products;
import com.example.store_online.data_models.ProductsSeen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.Objects;

public class ProductDetailActivity extends AppCompatActivity {
    private TextView txtNameProduct, txtPrice, txtDescription, txtEvaluate, txtSold, txtTotalStar;
    private ImageView imgProduct;
    private FirebaseAuth mAuth;
    private AppCompatRatingBar rbRatingProduct;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        //set display how for action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        // mapping view
        mapping();
        //get value product detail
        getValueProductDetail();
        //set action
        action();
    }

    private void mapping() {
        mAuth = FirebaseAuth.getInstance();
        txtNameProduct = findViewById(R.id.txtNameProduct);
        txtDescription = findViewById(R.id.txtDescription);
        txtEvaluate = findViewById(R.id.txtEvaluate);
        txtSold = findViewById(R.id.txtSold);
        txtPrice = findViewById(R.id.txtPrice);
        txtTotalStar = findViewById(R.id.txtTotalStar);
        imgProduct = findViewById(R.id.imgProduct);
        rbRatingProduct = findViewById(R.id.rbRatingProduct);
    }

    private void action() {

    }

    @SuppressLint("WrongConstant")
    private void getValueProductDetail() {
        Intent intent = getIntent();
        if(intent.getFlags() == 0){
            String id = intent.getStringExtra("id");
            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
            mRef.child("products").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Products products = snapshot.getValue(Products.class);
                    txtNameProduct.setText(products.getName());
                    txtPrice.setText(products.getPrice() + " VND | ");
                    txtDescription.setText(products.getDescription());
                    txtSold.setText("Đã bán " + String.valueOf(products.getSold()));
                    txtEvaluate.setText(String.valueOf(products.getEvaluate()));
                    txtTotalStar.setText(String.valueOf( products.getStar()));
                    rbRatingProduct.setRating(Float.parseFloat(String.valueOf(products.getStar())));
                    Glide.with(getApplication()).load(products.getImage()).error(R.drawable.ic_bn2).into(imgProduct);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else {
            String id = intent.getStringExtra("id");
            String name = intent.getStringExtra("name");
            String description = intent.getStringExtra("description");
            String category = intent.getStringExtra("category");
            String image = intent.getStringExtra("image");
            Integer price = intent.getIntExtra("price", 0);
            Integer sold = intent.getIntExtra("sold", 0);
            Integer evaluate = intent.getIntExtra("evaluate", 0);
            Double star = intent.getDoubleExtra("star", 5.0);
            //set value in text
            txtNameProduct.setText(name.toString());
            txtPrice.setText(String.valueOf(price) + " vnd | ");
            txtDescription.setText(description.toString());
            txtSold.setText("Đã bán " + String.valueOf(sold));
            txtEvaluate.setText(String.valueOf(evaluate));
            txtTotalStar.setText(String.valueOf(star));
            rbRatingProduct.setRating(Float.parseFloat(String.valueOf(star)));
            Glide.with(this).load(image).error(R.drawable.ic_bn2).into(imgProduct);
            //save infor product seen
            ProductsSeen productsSeen = new ProductsSeen(id,name,price,image,star);

            //save product for list product seen
            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("products_seen");
            mRef.child(Objects.requireNonNull(mAuth.getUid())).child(id).setValue(productsSeen);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}
