package com.example.store_online.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.store_online.R;
import com.example.store_online.data_models.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private ArrayList<Cart> cartArrayList;

    public CartAdapter(Context context, ArrayList<Cart> cartArrayList) {
        this.context = context;
        this.cartArrayList = cartArrayList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Cart cart = cartArrayList.get(position);
        Glide.with(context).load(cart.getImage()).error(R.drawable.ic_store).into(holder.imgProduct);
        holder.txtName.setText(cart.getName());
        holder.txtPrice.setText(String.valueOf(cart.getPrice()));
        holder.txtAmount.setText(String.valueOf(cart.getAmount()));

        holder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef.child("cart").child(mAuth.getUid()).child(cart.getId()).removeValue();
            }
        });
        holder.txtSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cart.getAmount() <= 1) {
                    mRef.child("cart").child(mAuth.getUid()).child(cart.getId()).removeValue();
                } else {
                    mRef.child("cart").child(mAuth.getUid()).child(cart.getId()).child("amount").setValue(cart.getAmount() - 1);
                }
            }
        });

        holder.txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef.child("cart").child(mAuth.getUid()).child(cart.getId()).child("amount").setValue(cart.getAmount() + 1);
            }
        });
    }


    @Override
    public int getItemCount() {
        return cartArrayList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProduct;
        private TextView txtName, txtPrice, txtAdd, txtSub, txtDelete, txtAmount;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtName = itemView.findViewById(R.id.txtName);
            txtAdd = itemView.findViewById(R.id.txtAdd);
            txtSub = itemView.findViewById(R.id.txtSub);
            txtDelete = itemView.findViewById(R.id.txtDelete);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtAmount = itemView.findViewById(R.id.txtAmount);
        }
    }
}
