package com.example.akhilsingla.biometricattendancesystem;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class after_signin_activity extends AppCompatActivity {
    public String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_signin_activity);
        Bundle bundle = getIntent().getExtras();
        phoneNumber = bundle.getString("msg");
    }
    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        System.gc();
        finish();
        System.exit(0); // finish activity
    }

    public void startAttendance(View v){
        Intent i = new Intent(this, SelectsemforAttendance.class);
        i.putExtra("msg",phoneNumber);
        startActivity(i);
    }

    public void myRights(View v){
        Intent i = new Intent(this, rights.class);
        i.putExtra("msg",phoneNumber);
        startActivity(i);
    }

}
