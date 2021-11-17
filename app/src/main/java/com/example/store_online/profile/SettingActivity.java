package com.example.store_online.profile;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.store_online.R;
import com.example.store_online.authentication.SignInActivity;
import com.example.store_online.dialog.LoadingDialog;
import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {
    private Button btnSignOut;
    private FirebaseAuth mAuth;
    private LoadingDialog loadingDialog;
    private TextView txtUserInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //mapping view
        mapping();
        //init firebase
        mAuth = FirebaseAuth.getInstance();
        //int lodding dialog
        loadingDialog = new LoadingDialog(this);
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
                loadingDialog.startLoadingDialog();
                mAuth.signOut();
                loadingDialog.endLoadingDialog();
                startActivity(new Intent(SettingActivity.this, SignInActivity.class));
                finishAffinity();
            }
        });
        txtUserInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, AccountInformationActivity.class));
            }
        });
    }

    private void mapping() {
        btnSignOut = findViewById(R.id.btn_sign_out_setting);
        txtUserInformation = findViewById(R.id.txtInforUser);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}