package com.example.store_online.product;

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

import java.text.NumberFormat;
import java.util.Objects;

public class ProductDetailActivity extends AppCompatActivity {
    private TextView txtNameProduct, txtPrice, txtDescription, txtEvaluate, txtSold, txtTotalStar;
    private ImageView imgProduct;
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

    private void getValueProductDetail() {
        Intent intent = getIntent();
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
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}
