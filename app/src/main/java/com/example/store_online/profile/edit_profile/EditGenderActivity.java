package com.example.store_online.profile.edit_profile;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.store_online.R;
import com.example.store_online.dialog.NotificationDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditGenderActivity extends AppCompatActivity {
    private RadioButton radMale, radFemale, radOther, radSecret;
    private Button btnSave;
    private String gender;
    private NotificationDialog notificationDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_gender);
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
                if (radMale.isChecked()) {
                    gender = radMale.getText().toString();
                } else if (radFemale.isChecked()) {
                    gender = radFemale.getText().toString();
                } else if (radOther.isChecked()) {
                    gender = radOther.getText().toString();
                } else {
                    gender = radSecret.getText().toString();
                }
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("account");
                mRef.child(user.getUid()).child("gender").setValue(gender).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        });
    }

    private void mapping() {
        radMale = findViewById(R.id.radMale);
        radFemale = findViewById(R.id.radFemale);
        radOther = findViewById(R.id.radOther);
        radSecret = findViewById(R.id.radSecret);
        btnSave = findViewById(R.id.btn_Save_changes);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}