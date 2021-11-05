package com.example.store_online.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.store_online.MainActivity;
import com.example.store_online.R;
import com.example.store_online.dialog.ErrorDialog;
import com.example.store_online.dialog.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    private TextView txtSignUp;
    private Button btnSignIn;
    private EditText edtEmail, edtPassword;
    private FirebaseAuth mAuth;
    private LoadingDialog loadingDialog;
    private ErrorDialog errorDialog;
    private String MESSAGE_SIGN_IN_ERROR = "Incorrect account or password!";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        //init Firebase
        mAuth = FirebaseAuth.getInstance();
        //init dialog
        loadingDialog = new LoadingDialog(SignInActivity.this);
        errorDialog = new ErrorDialog(this);
        mapping();
        setEvent();
    }

    private void setEvent() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });
    }

    private void signIn() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if (email.isEmpty()) {
            edtEmail.setError("Email is not empty!");
        } else if (password.isEmpty()) {
            edtPassword.setError("Email is not empty!");
        } else {
            loadingDialog.startLoadingDialog();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        loadingDialog.endLoadingDialog();
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        finishAffinity();
                    } else {
                        errorDialog.startErrorDialog(MESSAGE_SIGN_IN_ERROR);
                    }
                }
            });
        }
    }

    private void mapping() {
        txtSignUp = findViewById(R.id.txt_sign_up);
        edtEmail = findViewById(R.id.edt_sign_in_email);
        edtPassword = findViewById(R.id.edt_sign_in_password);
        btnSignIn = findViewById(R.id.btn_sign_in);
    }
}