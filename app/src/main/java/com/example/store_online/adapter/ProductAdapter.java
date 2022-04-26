package com.example.store_online.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.store_online.R;
import com.example.store_online.data_models.Products;
import com.example.store_online.product.ProductDetailActivity;

import java.text.NumberFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private ArrayList<Products> productsArrayList;

    public ProductAdapter(Context context, ArrayList<Products> productsArrayList) {
        this.context = context;
        this.productsArrayList = productsArrayList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new ProductViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Products products = productsArrayList.get(position);
        holder.txtName.setText(products.getName());
        holder.txtEvaluate.setText("| Evalute: " + products.getEvaluate());
        holder.txtPrice.setText(NumberFormat.getInstance().format(products.getPrice())+ " VND");
        holder.txtSold.setText("| Sold: "+ products.getSold());
        holder.rbRating.setRating((float) products.getStar());
        Glide.with(context).load(products.getImage()).error(R.drawable.ic_store).into(holder.imgProduct);

        holder.itemRecyclerViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id",productsArrayList.get(position).getId());
                intent.putExtra("category",productsArrayList.get(position).getCategory());
                intent.putExtra("name",productsArrayList.get(position).getName());
                intent.putExtra("description",productsArrayList.get(position).getDescription());
                intent.putExtra("evaluate",productsArrayList.get(position).getEvaluate());
                intent.putExtra("image",productsArrayList.get(position).getImage());
                intent.putExtra("price",productsArrayList.get(position).getPrice());
                intent.putExtra("sold",productsArrayList.get(position).getSold());
                intent.putExtra("star",productsArrayList.get(position).getStar());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsArrayList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProduct;
        private LinearLayout itemRecyclerViewProduct;
        private TextView txtName,txtPrice,txtSold,txtEvaluate;
        private AppCompatRatingBar rbRating;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtName = itemView.findViewById(R.id.txtNameProduct);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtSold = itemView.findViewById(R.id.txtSold);
            txtEvaluate = itemView.findViewById(R.id.txtEvaluate);
            rbRating = itemView.findViewById(R.id.rbRatingProduct);
            itemRecyclerViewProduct = itemView.findViewById(R.id.itemRecyclerViewProduct);
        }
    }
}
