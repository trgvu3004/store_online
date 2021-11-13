package com.example.store_online.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.store_online.R;

public class HomePageFragment extends Fragment {
    private View view;
    private ViewPager2 viewPager2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_page, container, false);
        //mapping
        mapping();
        //set show option menu for fragment
        setHasOptionsMenu(true);
        return view;
    }

    private void mapping() {
        viewPager2 = view.findViewById(R.id.list_img_banner);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home_page, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_search:
                break;
            case R.id.menu_notification:
                break;
            case R.id.menu_cart:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}