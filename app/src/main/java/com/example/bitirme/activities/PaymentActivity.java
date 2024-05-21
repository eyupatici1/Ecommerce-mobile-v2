package com.example.bitirme.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.bitirme.R;

public class PaymentActivity extends AppCompatActivity {

    TextView subTotal, shipping, total;
    Button button;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        toolbar = findViewById(R.id.payment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        double amount = 0.0;
        amount = getIntent().getDoubleExtra("amount", 0.0);

        subTotal = findViewById(R.id.sub_total);
        shipping = findViewById(R.id.textView18);
        total = findViewById(R.id.total_amt);
        button = findViewById(R.id.pay_btn);

        button.setEnabled(false);

        subTotal.setText(amount + "₺");
    }
}