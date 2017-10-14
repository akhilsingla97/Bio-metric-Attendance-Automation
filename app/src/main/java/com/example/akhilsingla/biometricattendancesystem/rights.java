package com.example.akhilsingla.biometricattendancesystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class rights extends AppCompatActivity {
    String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rights);
        Bundle bundle = getIntent().getExtras();
        phoneNumber = bundle.getString("msg");
    }
    @Override
    public void onBackPressed(){
        System.gc();
        finish();
    }

    public void addACourse(View v){
        Intent i = new Intent(this, after_signup_activity.class);
        i.putExtra("msg", phoneNumber);
        startActivity(i);
    }
}
