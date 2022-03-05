package com.example.store_online;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.store_online.authentication.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //hide action bar
        getSupportActionBar().hide();
        //initialization
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        handler = new Handler();
        //delay pop up screen
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Checked sign in
                autoSignIn();
            }
        }, 3000);

    }
    private void autoSignIn() {
        if (mUser == null) {
            //redirect to sign in screen
            startActivity(new Intent(SplashActivity.this, SignInActivity.class));
        } else {
            //redirect to main screen
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
        //close splash screen
        finish();
    }
}