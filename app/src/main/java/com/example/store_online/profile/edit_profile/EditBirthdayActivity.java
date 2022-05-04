package com.example.store_online.profile.edit_profile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

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

import java.util.Calendar;

public class EditBirthdayActivity extends AppCompatActivity {
    private EditText edtBirthday;
    private ImageView imgDateChoose;
    private Button btnSave;
    private NotificationDialog notificationDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_birthday);
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
                String birthday = edtBirthday.getText().toString().trim();
                if (birthday.isEmpty()) {
                    edtBirthday.setError(getResources().getString(R.string.field_empty));
                } else {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("account");
                    mRef.child(user.getUid()).child("birthday").setValue(birthday).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        imgDateChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //choose date
                chooseDate();
            }
        });
    }

    private void chooseDate() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        DatePickerDialog dialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtBirthday.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        dialog.show();
    }

    private void mapping() {
        edtBirthday = findViewById(R.id.edt_add_birthday);
        imgDateChoose = findViewById(R.id.img_date_choose);
        btnSave = findViewById(R.id.btn_Save_changes);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}