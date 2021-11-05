package com.example.store_online.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.store_online.MainActivity;
import com.example.store_online.R;

public class SignInActivity extends AppCompatActivity {
    private TextView txtSignUp;
    private  Button btnSignIn;
    private EditText edtEmail,edtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mapping();
        setEvent();
    }
    private void setEvent() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
            }
        });
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
            }
        });
    }

    private void mapping() {
        txtSignUp=findViewById(R.id.txt_sign_up);
        edtEmail=findViewById(R.id.edt_sign_in_email);
        edtPassword=findViewById(R.id.edt_sign_in_password);
        btnSignIn=findViewById(R.id.btn_sign_in);
    }
}