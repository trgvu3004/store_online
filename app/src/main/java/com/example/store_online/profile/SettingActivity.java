package com.example.store_online.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.store_online.MainActivity;
import com.example.store_online.R;
import com.example.store_online.authentication.SignInActivity;
import com.example.store_online.dialog.LoadingDialog;
import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {
    private Button btnSignOut;
    private FirebaseAuth mAuth;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //init firebase
        mAuth=FirebaseAuth.getInstance();
        //int lodding dialog
        loadingDialog=new LoadingDialog(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mapping();
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
    }

    private void mapping() {
        btnSignOut = findViewById(R.id.btn_sign_out_setting);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        MainActivity mainActivity=new MainActivity();
        mainActivity.onBackPressed();
        startActivity(new Intent(SettingActivity.this,MainActivity.class));
        finish();
        return true;
    }
}