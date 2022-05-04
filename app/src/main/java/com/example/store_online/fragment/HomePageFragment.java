package com.example.store_online.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.store_online.R;
import com.example.store_online.adapter.CategoryAdapter;
import com.example.store_online.adapter.FeaturedCategoryAdapter;
import com.example.store_online.adapter.PhotoBannerAdapter;
import com.example.store_online.adapter.ProductAdapter;
import com.example.store_online.data_models.Category;
import com.example.store_online.data_models.FeaturedCategory;
import com.example.store_online.data_models.PhotoBanner;
import com.example.store_online.data_models.Products;
import com.example.store_online.product.ProductDetailActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class HomePageFragment extends Fragment {
    private View view;
    private ViewPager2 viewPager2;
    private RecyclerView rvFeaturedCategory, rvProducts;
    private CircleIndicator3 circleIndicator3;
    private List<PhotoBanner> listPhoto;
    private List<FeaturedCategory> featuredCategoryList;
    private ArrayList<Products> productsArrayList;
    private ArrayList<String> mKey = new ArrayList<>();
    private ProductAdapter productAdapter;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager2.getCurrentItem() == listPhoto.size() - 1) {
                viewPager2.setCurrentItem(0);
            } else {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_page, container, false);
        //mapping
        mapping();
        //set show option menu for fragment
        setHasOptionsMenu(true);
        //get list data
        listPhoto = getListPhoto();
        featuredCategoryList = getFeaturedCategoryList();
        productsArrayList = getProductsArrayList();
        //load banner
        PhotoBannerAdapter photoBannerAdapter = new PhotoBannerAdapter(getContext(), listPhoto);
        viewPager2.setAdapter(photoBannerAdapter);
        circleIndicator3.setViewPager(viewPager2);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
        });
        //create Product adapter, set adapter for recycler view
        productAdapter = new ProductAdapter(getContext(), productsArrayList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        rvProducts.setLayoutManager(gridLayoutManager);
        rvProducts.setAdapter(productAdapter);

        //create Featured Category adapter, set adapter for recycler view
        FeaturedCategoryAdapter featuredCategoryAdapter = new FeaturedCategoryAdapter(getContext(), featuredCategoryList);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1, RecyclerView.HORIZONTAL, false);
        rvFeaturedCategory.setLayoutManager(layoutManager);
        rvFeaturedCategory.setAdapter(featuredCategoryAdapter);

        //set action
       // action();
        return view;
    }
    private void action(){

    }

    private void mapping() {
        viewPager2 = view.findViewById(R.id.list_img_banner);
        circleIndicator3 = view.findViewById(R.id.circleIndicator);
        rvFeaturedCategory = view.findViewById(R.id.rvFeaturedCategory);
        rvProducts = view.findViewById(R.id.rvProducs);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home_page, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                break;
            case R.id.menu_notification:
                break;
            case R.id.menu_cart:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<FeaturedCategory> getFeaturedCategoryList() {
        List<FeaturedCategory> featuredCategoryLists = new ArrayList<>();
        featuredCategoryLists.add(new FeaturedCategory(R.drawable.ic_bn2, "Dành cho ban"));
        featuredCategoryLists.add(new FeaturedCategory(R.drawable.ic_bn2, "Hot Sale"));
        featuredCategoryLists.add(new FeaturedCategory(R.drawable.ic_bn2, "Bán chạy"));
        featuredCategoryLists.add(new FeaturedCategory(R.drawable.ic_bn2, "Hàng mới"));
        featuredCategoryLists.add(new FeaturedCategory(R.drawable.ic_bn2, "Rẻ vô đối"));
        return featuredCategoryLists;
    }

    private List<PhotoBanner> getListPhoto() {
        List<PhotoBanner> photoBanners = new ArrayList<>();
        photoBanners.add(new PhotoBanner(R.drawable.ic_bn1));
        photoBanners.add(new PhotoBanner(R.drawable.ic_bn2));
        photoBanners.add(new PhotoBanner(R.drawable.ic_bn3));
        return photoBanners;
    }

    private ArrayList<Products> getProductsArrayList() {
        ArrayList<Products> products = new ArrayList<>();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("products").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Products product = snapshot.getValue(Products.class);
                products.add(product);
                mKey.add(snapshot.getKey());
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String key = snapshot.getKey();
                int index = mKey.indexOf(key);
                productsArrayList.set(index, snapshot.getValue(Products.class));
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String key = snapshot.getKey();
                int index = mKey.indexOf(key);
                productsArrayList.remove(index);
                mKey.remove(index);
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return products;
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }
}