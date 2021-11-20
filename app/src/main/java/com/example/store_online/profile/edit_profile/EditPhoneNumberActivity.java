package com.example.store_online.profile.edit_profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.store_online.R;
import com.example.store_online.dialog.NotificationDialog;
import com.example.store_online.dialog.PhoneVerifyDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.concurrent.TimeUnit;

public class EditPhoneNumberActivity extends AppCompatActivity {
    private EditText edtPhoneNumber;
    private Button btnSave;
    private NotificationDialog notificationDialog;
    private PhoneVerifyDialog phoneVerifyDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_phone_number);
        //mapping view
        mapping();
        //init notification dialog
        notificationDialog = new NotificationDialog(this);
        phoneVerifyDialog = new PhoneVerifyDialog(this);
        //return screen for edit full name screen
        getSupportActionBar().setTitle(R.string.edit_information);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //set Event listener
        setEvent();
    }

    private void setEvent() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edtPhoneNumber.getText().toString().trim();
                if (phone.isEmpty()) {
                    edtPhoneNumber.setError(getResources().getString(R.string.field_empty));
                } else {
                    phoneVerification(phone);
                }
            }
        });
    }

    private void phoneVerification(String phone) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phone)      // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(EditPhoneNumberActivity.this)// Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                FirebaseUser mUser = mAuth.getCurrentUser();
                                mUser.updatePhoneNumber(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        notificationDialog.startSuccessfulDialog("Thành công");
                                    }
                                });
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                notificationDialog.startErrorDialog("Thất bại");
                            }

                            @Override
                            public void onCodeSent(@NonNull String verifiactionID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verifiactionID, forceResendingToken);
                                phoneVerifyDialog.startPhoneVerifyDialog(phone,verifiactionID);
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void mapping() {
        edtPhoneNumber = findViewById(R.id.edt_add_phone_number);
        btnSave = findViewById(R.id.btn_Save_changes);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}