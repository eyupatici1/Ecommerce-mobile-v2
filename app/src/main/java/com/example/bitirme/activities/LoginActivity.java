package com.example.bitirme.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    private FirebaseAuth authentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getSupportActionBar().hide();

        authentication = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
    }

    public void signIn(View view) {

        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

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

        authentication.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {

                            Toast.makeText(LoginActivity.this, "Başarıyla giriş yapıldı.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));

                        } else {

                            Toast.makeText(LoginActivity.this, "Giriş yapılamadı, " + task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }

    public void signUp(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}