package com.example.bitirme.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bitirme.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText name, email, password;

    private FirebaseAuth authentication;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //getSupportActionBar().hide();

        authentication = FirebaseAuth.getInstance();

        if(authentication.getCurrentUser() != null){

            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();

        }

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        sharedPreferences = getSharedPreferences("onboardingScreen", MODE_PRIVATE);

        boolean isFirstTime = sharedPreferences.getBoolean("firstTime", true);

        if(isFirstTime) {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstTime", false);
            editor.commit();

            Intent intent = new Intent(RegisterActivity.this, OnboardActivity.class);
            startActivity(intent);
            finish();

        }

    }

    public void signup(View view) {

        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if(TextUtils.isEmpty(userName)){

            Toast.makeText(this, "İsim boş bırakılamaz", Toast.LENGTH_SHORT).show();
            return;

        }

        if(TextUtils.isEmpty(userEmail)){

            Toast.makeText(this, "E-mail boş bırakılamaz.", Toast.LENGTH_SHORT).show();
            return;

        }

        if(TextUtils.isEmpty(userPassword)){

            Toast.makeText(this, "Şifre boş bırakılamaz.", Toast.LENGTH_SHORT).show();
            return;

        }

        if(userPassword.length() < 8) {

            Toast.makeText(this, "Şifre 8 karakterden kısa olamaz.", Toast.LENGTH_SHORT).show();
            return;

        }

        authentication.createUserWithEmailAndPassword(userEmail, userPassword)
                      .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                          @Override
                          public void onComplete(@NonNull Task<AuthResult> task) {

                              if(task.isSuccessful())
                              {

                                  Toast.makeText(RegisterActivity.this, "Kayıt başarıyla oluşturuldu.", Toast.LENGTH_SHORT).show();
                                  startActivity(new Intent(RegisterActivity.this, MainActivity.class));

                              } else {

                                  Toast.makeText(RegisterActivity.this, "Kayıt oluşturulamadı, " + task.getException(), Toast.LENGTH_SHORT).show();

                              }

                          }
                      });

    }

    public void signin(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}