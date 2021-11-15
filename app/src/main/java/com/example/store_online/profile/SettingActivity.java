package com.example.store_online.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;



import com.example.store_online.R;
import com.example.store_online.authentication.SignInActivity;
import com.example.store_online.dialog.LoadingDialog;
import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {
    private Button btnSignOut;
    private FirebaseAuth mAuth;
    private LoadingDialog loadingDialog;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //mapping view
        mapping();
        //init firebase
        mAuth=FirebaseAuth.getInstance();
        //int lodding dialog
        loadingDialog = new LoadingDialog(this);
        //set ToolBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.setting_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        //set Event Listening
        setEvent();
    }

    private void setEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.startLoadingDialog();
                mAuth.signOut();
                loadingDialog.endLoadingDialog();
                startActivity(new Intent(SettingActivity.this, SignInActivity.class));
                finishAffinity();
            }
        });
    }

    private void mapping() {
        btnSignOut = findViewById(R.id.btn_sign_out_setting);
        toolbar = findViewById(R.id.tbSetting);
    }
}