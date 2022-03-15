package com.example.store_online.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.store_online.R;
import com.example.store_online.data_models.Category;
import com.example.store_online.data_models.FeaturedCategory;

import java.util.List;

public class FeaturedCategoryAdapter extends RecyclerView.Adapter<FeaturedCategoryAdapter.FeaturedCategoryHolder> {
    private Context context;
    private List<FeaturedCategory> featuredCategoryList;

    public FeaturedCategoryAdapter(Context context, List<FeaturedCategory> featuredCategoryList) {
        this.context = context;
        this.featuredCategoryList = featuredCategoryList;
    }

    @NonNull
    @Override
    public FeaturedCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_featured_category, parent, false);
        return new FeaturedCategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedCategoryHolder holder, int position) {
        FeaturedCategory category = featuredCategoryList.get(position);
        holder.imgCategory.setImageResource(category.getResourceID());
        holder.txtName.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return featuredCategoryList.size();
    }

    public class FeaturedCategoryHolder extends RecyclerView.ViewHolder {
        private ImageView imgCategory;
        private TextView txtName;

        public FeaturedCategoryHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.imgCategory);
            txtName = itemView.findViewById(R.id.txtName);
        }
    }
}
