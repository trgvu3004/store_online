package com.example.store_online.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.store_online.R;
import com.example.store_online.adapter.PhotoBannerAdapter;
import com.example.store_online.data_models.PhotoBanner;
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
    private CircleIndicator3 circleIndicator3;
    private List<PhotoBanner> listPhoto;
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
        //
        listPhoto = getListPhoto();
        PhotoBannerAdapter photoBannerAdapter = new PhotoBannerAdapter(getContext(), listPhoto);
        viewPager2.setAdapter(photoBannerAdapter);
        circleIndicator3.setViewPager(viewPager2);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable,3000);
            }
        });
        return view;
    }

    private void mapping() {
        viewPager2 = view.findViewById(R.id.list_img_banner);
        circleIndicator3 = view.findViewById(R.id.circleIndicator);
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

    private List<PhotoBanner> getListPhoto() {
        List<PhotoBanner> photoBanners = new ArrayList<>();
        photoBanners.add(new PhotoBanner(R.drawable.ic_bn1));
        photoBanners.add(new PhotoBanner(R.drawable.ic_bn2));
        photoBanners.add(new PhotoBanner(R.drawable.ic_bn3));
        /*DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("banner");
        mRef.child("banner_home_page").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PhotoBanner photoBanner = snapshot.getValue(PhotoBanner.class);
                photoBanners.add(photoBanner);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        return photoBanners;
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable,3000);
    }
}