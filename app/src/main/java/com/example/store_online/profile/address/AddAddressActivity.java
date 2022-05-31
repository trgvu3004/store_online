package com.example.store_online.profile.address;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.store_online.R;
import com.example.store_online.data_models.Address;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AddAddressActivity extends AppCompatActivity {
    private Button btnAdd;
    private EditText edtName,edtPhone,edtHomenumber,edtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        setTitle("Thêm Địa Chỉ");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mapping();
        action();
    }

    private void action() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String phone = edtPhone.getText().toString();
                String homeNumber = edtHomenumber.getText().toString();
                String city = edtAddress.getText().toString();
                if (name.equals("")){
                    edtName.setError("Không được bỏ trống!");
                }
                else if (phone.equals("")){
                    edtPhone.setError("Không được bỏ trống!");
                }
                else if (homeNumber.equals("")){
                    edtHomenumber.setError("Không được bỏ trống!");
                }
                else if (city.equals("")){
                    edtAddress.setError("Không được bỏ trống!");
                }
                else {
                    Address address = new Address(name,"(+84) " +phone,homeNumber,city);
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
                    mRef.child("address").child(Objects.requireNonNull(mAuth.getUid())).push().setValue(address).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isComplete()){
                                Toast.makeText(AddAddressActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                            else {
                                Toast.makeText(AddAddressActivity.this, "Thêm không thành công!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void mapping() {
        btnAdd = findViewById(R.id.btnAdd);
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtHomenumber = findViewById(R.id.edtHomeNumber);
        edtAddress = findViewById(R.id.edtCity);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}