package com.example.akhilsingla.biometricattendancesystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class after_signin_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_signin_activity);
        Bundle bundle = getIntent().getExtras();
        String phoneNumber = bundle.getString("msg");
    }
}
