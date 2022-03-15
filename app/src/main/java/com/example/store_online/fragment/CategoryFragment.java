package com.example.store_online.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.store_online.R;
import com.example.store_online.adapter.CategoryAdapter;
import com.example.store_online.data_models.Category;

import java.util.ArrayList;
import java.util.List;


public class CategoryFragment extends Fragment {
    private View view;
    private List<Category> categoryList;
    private RecyclerView rvCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category, container, false);
        //
        mapping();
        //
        categoryList = getCategoryList();
        //Load category
        CategoryAdapter categoryAdapter  = new CategoryAdapter(getContext(),categoryList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2, RecyclerView.VERTICAL,false);
        rvCategory.setLayoutManager(gridLayoutManager);
        rvCategory.setAdapter(categoryAdapter);
        return view;
    }

    private void mapping() {
        rvCategory = view.findViewById(R.id.rvCategory);
    }
    private List<Category> getCategoryList(){
        List<Category> categoryLists = new ArrayList<>();
        categoryLists.add(new Category(R.drawable.ic_bn1,"Book 1"));
        categoryLists.add(new Category(R.drawable.ic_bn2,"Book 1"));
        categoryLists.add(new Category(R.drawable.ic_bn3,"Book 1"));
        categoryLists.add(new Category(R.drawable.ic_bn1,"Book 1"));
        categoryLists.add(new Category(R.drawable.ic_bn2,"Book 1"));
        categoryLists.add(new Category(R.drawable.ic_bn3,"Book 1"));
        return categoryLists;
    }
}