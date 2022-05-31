package com.example.store_online.profile;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.store_online.R;
import com.example.store_online.authentication.SignInActivity;
import com.example.store_online.dialog.NotificationDialog;
import com.example.store_online.profile.address.AddressActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {
    private Button btnSignOut;
    private FirebaseAuth mAuth;
    private NotificationDialog notificationDialog;
    private ConstraintLayout layoutAccountInformation, layoutListAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //mapping view
        mapping();
        //init firebase
        mAuth = FirebaseAuth.getInstance();
        //int lodding dialog
        notificationDialog = new NotificationDialog(this);
        //set ToolBar
        getSupportActionBar().setTitle(R.string.setting_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //set Event Listening
        setEvent();
    }

    private void setEvent() {
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationDialog.startLoadingDialog();
                mAuth.signOut();
                notificationDialog.endLoadingDialog();
                startActivity(new Intent(SettingActivity.this, SignInActivity.class));
                finishAffinity();
            }
        });
        layoutAccountInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, AccountInformationActivity.class));
            }
        });
        layoutListAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, AddressActivity.class));
            }
        });
    }

    private void mapping() {
        btnSignOut = findViewById(R.id.btn_sign_out_setting);
        layoutAccountInformation = findViewById(R.id.layoutAccountInformation);
        layoutListAddress = findViewById(R.id.layoutListAddress);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}