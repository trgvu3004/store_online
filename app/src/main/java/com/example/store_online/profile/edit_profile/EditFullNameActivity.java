package com.example.store_online.profile.edit_profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.store_online.R;
import com.example.store_online.dialog.NotificationDialog;
import com.example.store_online.profile.AccountInformationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class EditFullNameActivity extends AppCompatActivity {
    private EditText edtFullName;
    private Button btnSave;
    private NotificationDialog notificationDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_full_name);
        //mapping view
        mapping();
        //init notification dialog
        notificationDialog = new NotificationDialog(this);
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
                String name = edtFullName.getText().toString().trim();
                if(name.isEmpty()){
                    edtFullName.setError(getResources().getString(R.string.field_empty));
                }
                else {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build();
                    user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                notificationDialog.startSuccessfulDialog(getResources().getString(R.string.save_successful));
                            } else {
                                notificationDialog.startErrorDialog(getResources().getString(R.string.save_failed));
                            }
                        }
                    });
                }
            }
        });
    }

    private void mapping() {
        edtFullName = findViewById(R.id.edt_add_full_name);
        btnSave = findViewById(R.id.btn_Save_changes);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}