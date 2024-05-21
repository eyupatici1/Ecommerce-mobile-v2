package com.example.bitirme.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bitirme.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    EditText address, city, name, postalCode, phoneNumber;
    Toolbar toolbar;
    Button addAddressBtn;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        toolbar = findViewById(R.id.add_address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        address = findViewById(R.id.ad_address);
        city = findViewById(R.id.ad_city);
        name = findViewById(R.id.ad_name);
        postalCode = findViewById(R.id.ad_code);
        phoneNumber = findViewById(R.id.ad_phone);
        addAddressBtn = findViewById(R.id.ad_add_address);

        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = name.getText().toString();
                String userCity = city.getText().toString();
                String userAddress = address.getText().toString();
                String userCode = postalCode.getText().toString();
                String userNumber = phoneNumber.getText().toString();
                String finalAddress = "";

                //if(!userName.isEmpty()) {
                  //  finalAddress += userName;
                //}

                if(!userCity.isEmpty()) {
                    finalAddress += userCity + " ";
                }

                if(!userAddress.isEmpty()) {
                    finalAddress += userAddress + " ";
                }

                if(!userCode.isEmpty()) {
                    finalAddress += userCode + " ";
                }

                //if(!userNumber.isEmpty()) {
                 //   finalAddress += userNumber;
                //}

                if(!userName.isEmpty() && !userCity.isEmpty() && !userAddress.isEmpty() && !userCode.isEmpty() && !userNumber.isEmpty()) {

                    Map<String, String> map = new HashMap<>();
                    map.put("userAddress", finalAddress);

                    firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                             .collection("Address").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {

                                    if(task.isSuccessful()) {
                                        Toast.makeText(AddAddressActivity.this, "Adres başarıyla eklendi.", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(AddAddressActivity.this, AddressActivity.class));
                                    }

                                }
                            });

                } else {
                    Toast.makeText(AddAddressActivity.this, "Adres eklenemedi, boş alanları kontrol ediniz.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}