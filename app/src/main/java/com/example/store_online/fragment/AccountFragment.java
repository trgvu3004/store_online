package com.example.store_online.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.store_online.R;
import com.example.store_online.authentication.SignInActivity;
import com.example.store_online.data_models.AccountInformation;
import com.example.store_online.profile.SettingActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountFragment extends Fragment {
    private View view;
    private ConstraintLayout layoutSetting;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseDatabase myDatabase;
    private DatabaseReference myRef;
    private TextView txtNickname, txtCoin, txtRank;
    private ImageView imgAvatar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);
        setHasOptionsMenu(true);
        //
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        myDatabase = FirebaseDatabase.getInstance();
        //mapping view
        mapping();
        //action
        loadAccountInformation();
        setAction();
        return view;
    }

    private void setAction() {
        layoutSetting.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), SettingActivity.class));
        });
    }

    private void mapping() {
        layoutSetting = view.findViewById(R.id.layoutSetting);
        txtNickname = view.findViewById(R.id.txtNickname);
        txtCoin = view.findViewById(R.id.txtCoin);
        txtRank = view.findViewById(R.id.txtRank);
        imgAvatar = view.findViewById(R.id.imgAvatar);
    }

    private void loadAccountInformation() {
        if (mUser != null) {
            String UID = mUser.getUid();
            myRef = myDatabase.getReference();
            myRef.child("account").child(UID).addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    AccountInformation account = snapshot.getValue(AccountInformation.class);
                    assert account != null;
                    if (account.getRank() != null) {
                        txtRank.setText("Hạng: " + account.getRank());
                    }
                    if (account.getCoin() != null) {
                        txtCoin.setText(account.getCoin());
                    }
                    if (account.getNickname() != null) {
                        txtNickname.setText(account.getNickname());
                    }
                    if(account.getAvatar() != null){
                        Glide.with(getContext()).load(account.getAvatar()).error(R.drawable.ic_store).into(imgAvatar);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_action_profile, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
        return true;
    }

}