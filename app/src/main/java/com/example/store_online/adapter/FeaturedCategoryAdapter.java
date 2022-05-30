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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.store_online.R;
import com.example.store_online.data_models.Category;
import com.example.store_online.data_models.FeaturedCategory;
import com.example.store_online.product.CartActivity;

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
        Glide.with(context).load(category.getImage()).error(R.drawable.ic_store).into(holder.imgCategory);
        holder.txtName.setText(category.getName());
        holder.layoutFeaturedCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (featuredCategoryList.get(position).getId()){
//                    case "1":
//                        context.startActivity(new Intent(context,ForYouActivity.class));
//                    case "2":
//                        context.startActivity(new Intent(context,HotSaleActivity.class));
                    case "3":
                        context.startActivity(new Intent(context, CartActivity.class));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return featuredCategoryList.size();
    }

    public class FeaturedCategoryHolder extends RecyclerView.ViewHolder {
        private ImageView imgCategory;
        private TextView txtName;
        private LinearLayout layoutFeaturedCategory;

        public FeaturedCategoryHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.imgCategory);
            txtName = itemView.findViewById(R.id.txtName);
            layoutFeaturedCategory = itemView.findViewById(R.id.layoutFeaturedCategory);
        }
    }
}
