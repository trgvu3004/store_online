package com.example.store_online.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.store_online.R;
import com.example.store_online.data_models.ProductsSeen;

import java.util.ArrayList;
import java.util.PrimitiveIterator;

public class ProductSeenAdapter extends RecyclerView.Adapter<ProductSeenAdapter.ProductSeenViewHolder>{
    private Context context;
    private ArrayList<ProductsSeen> productsSeenArrayList;

    public ProductSeenAdapter(Context context, ArrayList<ProductsSeen> productsSeenArrayList) {
        this.context = context;
        this.productsSeenArrayList = productsSeenArrayList;
    }

    @NonNull
    @Override
    public ProductSeenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_seen,parent,false);
        return new ProductSeenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductSeenViewHolder holder, int position) {
        ProductsSeen productsSeen = productsSeenArrayList.get(position);
        Glide.with(context).load(productsSeen.getImage()).error(R.drawable.ic_store).into(holder.imgProduct);
        holder.txtNameProduct.setText(productsSeen.getName());
        holder.txtPrice.setText(productsSeen.getPrice() + "VND");
        holder.ratingBar.setRating(Float.parseFloat(String.valueOf(productsSeen.getStar())));
    }

    @Override
    public int getItemCount() {
        return productsSeenArrayList.size();
    }

    public class ProductSeenViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProduct;
        private TextView txtNameProduct, txtPrice;
        private AppCompatRatingBar ratingBar;
        public ProductSeenViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct =itemView.findViewById(R.id.imgProduct);
            txtNameProduct =itemView.findViewById(R.id.txtNameProduct);
            txtPrice =itemView.findViewById(R.id.txtPrice);
            ratingBar =itemView.findViewById(R.id.ratingBar);
        }
    }
}
