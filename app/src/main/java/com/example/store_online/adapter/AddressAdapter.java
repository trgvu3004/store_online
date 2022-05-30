package com.example.store_online.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.store_online.R;
import com.example.store_online.data_models.Address;

import java.util.ArrayList;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    private Context context;
    private ArrayList<Address> addressArrayList;

    public AddressAdapter(Context context, ArrayList<Address> addressArrayList) {
        this.context = context;
        this.addressArrayList = addressArrayList;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address,parent,false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Address address = addressArrayList.get(position);
        holder.txtName.setText(address.getName());
        holder.txtPhone.setText(address.getPhone());
        holder.txtHomeNumber.setText(address.getHome());
        holder.txtAddress.setText(address.getAddress());

        holder.layoutItemAddres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return addressArrayList.size();
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtPhone, txtHomeNumber, txtAddress;
        private ConstraintLayout layoutItemAddres;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            txtHomeNumber = itemView.findViewById(R.id.txtHomeNumber);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            layoutItemAddres = itemView.findViewById(R.id.layoutItemAddress);
        }
    }
}
