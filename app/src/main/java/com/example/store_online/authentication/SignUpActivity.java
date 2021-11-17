package com.example.store_online.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.store_online.MainActivity;
import com.example.store_online.R;
import com.example.store_online.dialog.NotificationDialog;
import com.example.store_online.dialog.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private Button btnSignUp;
    private EditText edtEmail, edtPassword, edtRe_enterPassword;
    private FirebaseAuth mAuth;
    private LoadingDialog loadingDialog;
    private NotificationDialog notificationDialog;
    private String MESSAGE_SIGN_UP_ERROR = "Please double check your email address or password!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //inti Firebase
        mAuth = FirebaseAuth.getInstance();
        //inti dialog
        loadingDialog = new LoadingDialog(SignUpActivity.this);
        notificationDialog = new NotificationDialog(this);
        //mapping
        mapping();
        //event
        setEvent();
    }

    private void setEvent() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String repassword = edtRe_enterPassword.getText().toString().trim();
        if (email.isEmpty()) {
            edtEmail.setError("Email is not empty!");
        } else if (password.isEmpty()) {
            edtPassword.setError("Email is not empty!");
        } else if (repassword.isEmpty()) {
            edtRe_enterPassword.setError("Email is not empty!");
        } else if (!password.equalsIgnoreCase(repassword)) {
            edtRe_enterPassword.setError("Re-enter password does not match!");
        } else {
            loadingDialog.startLoadingDialog();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        loadingDialog.endLoadingDialog();
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                    } else {
                        notificationDialog.startErrorDialog(MESSAGE_SIGN_UP_ERROR);
                    }
                }
            });
        }
    }

    private void mapping() {
        btnSignUp = findViewById(R.id.btn_sign_up);
        edtEmail = findViewById(R.id.edt_sign_up_email);
        edtPassword = findViewById(R.id.edt_sign_up_password);
        edtRe_enterPassword = findViewById(R.id.edt_sign_up_re_enter_password);
    }
}