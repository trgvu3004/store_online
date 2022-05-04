package com.example.store_online.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.store_online.MainActivity;
import com.example.store_online.R;
import com.example.store_online.dialog.NotificationDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    private Button btnSignUp;
    private TextView txtSignIn;
    private EditText edtEmail, edtPassword, edtRe_enterPassword;
    private FirebaseAuth mAuth;
    private NotificationDialog notificationDialog;
    private String MESSAGE_SIGN_UP_ERROR = "Please double check your email address or password!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //inti Firebase
        mAuth = FirebaseAuth.getInstance();
        //inti dialog
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
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignInActivity.class));
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
            notificationDialog.startLoadingDialog();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        notificationDialog.endLoadingDialog();
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
        txtSignIn = findViewById(R.id.txt_sign_in);
        edtEmail = findViewById(R.id.edt_sign_up_email);
        edtPassword = findViewById(R.id.edt_sign_up_password);
        edtRe_enterPassword = findViewById(R.id.edt_sign_up_re_enter_password);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}