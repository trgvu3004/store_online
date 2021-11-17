package com.example.store_online.profile;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.store_online.R;
import com.example.store_online.data_models.AccountInformation;
import com.example.store_online.dialog.LoadingDialog;
import com.example.store_online.dialog.PasswordChangeDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountInformationActivity extends AppCompatActivity {
    private LinearLayout vgFullName,vgPasswordChange,vgPasswordReset;
    private TextView txtFullName, txtPhone, txtEmail,txtBirthday,txtNickname,txtGender;
    private Button btnPasswordChange,btnPasswordReset;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private PasswordChangeDialog passwordChangeDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information);
        //mapping view
        mapping();
        //init firebase
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        //
        passwordChangeDialog = new PasswordChangeDialog(this);
        //load acount infor from firebase
        loadAccountInformation();
        //set return screen
        getSupportActionBar().setTitle(R.string.account_Information);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //set event
        setEvent();
    }

    private void loadAccountInformation() {
        if (mUser != null) {
            String name = mUser.getDisplayName();
            String mail = mUser.getEmail();
            String phone = mUser.getPhoneNumber();
            if (!name.isEmpty()) {
                txtFullName.setText(name);
            } else {
                txtFullName.setText(getResources().getString(R.string.txt_add_full_name));
            }
            if (!phone.isEmpty()) {
                txtPhone.setText(phone);
            } else {
                txtPhone.setText(getResources().getString(R.string.txt_add_phone_number));
            }
            txtEmail.setText(mail);
            String UID = mUser.getUid();
            myRef = database.getReference("Account");
            myRef.child(UID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    AccountInformation account = snapshot.getValue(AccountInformation.class);
                    if(account.getBirthday() != null){
                        txtBirthday.setText(account.getBirthday());
                    }
                    if(account.getGender()!=null){
                        txtGender.setText(account.getGender());
                    }
                    if(account.getNickname()!=null){
                        txtNickname.setText(account.getNickname());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void setEvent() {
        vgFullName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show dialog add fullname

            }
        });
        vgPasswordChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordChangeDialog.startAddInforDialog();
            }
        });
    }

    private void mapping() {
        vgFullName = findViewById(R.id.vg_full_name);
        vgPasswordChange = findViewById(R.id.vg_password_change);
        vgPasswordReset = findViewById(R.id.vg_password_reset);
        txtFullName = findViewById(R.id.txt_full_name);
        txtPhone = findViewById(R.id.txt_phone);
        txtEmail = findViewById(R.id.txt_email);
        txtGender = findViewById(R.id.txt_gender);
        txtBirthday = findViewById(R.id.txt_birthday);
        txtNickname = findViewById(R.id.txt_nickname);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}