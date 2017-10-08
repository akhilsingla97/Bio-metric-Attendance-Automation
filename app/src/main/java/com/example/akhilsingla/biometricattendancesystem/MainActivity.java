package com.example.akhilsingla.biometricattendancesystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void signin(View v){
        Intent intent = new Intent(this, signin_activity.class);
        startActivity(intent);
        finish();
    }
    public void signup(View v){
        Intent intent = new Intent(this, signup_activity.class);
        startActivity(intent);
        finish();
    }
}
