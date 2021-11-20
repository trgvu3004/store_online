package com.example.store_online.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.store_online.R;
import com.example.store_online.data_models.AccountInformation;
import com.example.store_online.dialog.NotificationDialog;
import com.example.store_online.dialog.PasswordChangeDialog;
import com.example.store_online.profile.edit_profile.ChangeEmailAdressActivity;
import com.example.store_online.profile.edit_profile.EditBirthdayActivity;
import com.example.store_online.profile.edit_profile.EditFullNameActivity;
import com.example.store_online.profile.edit_profile.EditGenderActivity;
import com.example.store_online.profile.edit_profile.EditNickNameActivity;
import com.example.store_online.profile.edit_profile.EditPhoneNumberActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountInformationActivity extends AppCompatActivity {
    private LinearLayout vgFullName, vgNickname, vgGender, vgBirthday,
            vgPhone, vgEmail, vgPasswordChange, vgPasswordReset;
    private TextView txtFullName, txtPhone, txtEmail, txtBirthday, txtNickname, txtGender;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private PasswordChangeDialog passwordChangeDialog;
    private NotificationDialog notificationDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set view
        setContentView(R.layout.activity_account_information);
        //mapping view
        mapping();
        //init firebase
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        //load acount infor from firebase
        loadAccountInformation();
        //init dialog
        passwordChangeDialog = new PasswordChangeDialog(this);
        notificationDialog = new NotificationDialog(this);
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

            if (name != null) {
                txtFullName.setText(name);
            } else {
                txtFullName.setText(getResources().getString(R.string.txt_add_full_name));
            }

            if (phone != null) {
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
                    if (account.getBirthday() != null) {
                        txtBirthday.setText(account.getBirthday());
                    } else {
                        txtBirthday.setText(getResources().getString(R.string.txt_add_birthday));
                    }
                    if (account.getGender() != null) {
                        txtGender.setText(account.getGender());
                    } else {
                        txtGender.setText(getResources().getString(R.string.txt_add_gender));
                    }
                    if (account.getNickname() != null) {
                        txtNickname.setText(account.getNickname());
                    } else {
                        txtNickname.setText(getResources().getString(R.string.txt_add_nickname));
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
                startActivity(new Intent(AccountInformationActivity.this, EditFullNameActivity.class));
            }
        });
        vgNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountInformationActivity.this, EditNickNameActivity.class));
            }
        });
        vgGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountInformationActivity.this, EditGenderActivity.class));
            }
        });
        vgBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountInformationActivity.this, EditBirthdayActivity.class));
            }
        });
        vgPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountInformationActivity.this, EditPhoneNumberActivity.class));
            }
        });
        vgEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountInformationActivity.this, ChangeEmailAdressActivity.class));
            }
        });
        vgPasswordChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordChangeDialog.startAddInforDialog();
            }
        });
        vgPasswordReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendPasswordResetEmail();
            }
        });
    }

    private void SendPasswordResetEmail() {
        mAuth.sendPasswordResetEmail(mUser.getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    notificationDialog.startSuccessfulDialog(getResources().getString(R.string.sendEmailSuccess));
                } else {
                    notificationDialog.startErrorDialog(getResources().getString(R.string.sendEmailFailed));
                }
            }
        });
    }

    private void mapping() {
        vgFullName = findViewById(R.id.vg_full_name);
        vgNickname = findViewById(R.id.vg_nickname);
        vgGender = findViewById(R.id.vg_gender);
        vgBirthday = findViewById(R.id.vg_birthday);
        vgPhone = findViewById(R.id.vg_phone_number);
        vgEmail = findViewById(R.id.vg_email);
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

    @Override
    protected void onResume() {
        loadAccountInformation();
        super.onResume();
    }
}