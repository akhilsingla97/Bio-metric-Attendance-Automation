package com.example.akhilsingla.biometricattendancesystem;

import android.icu.text.SimpleDateFormat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import static android.R.attr.name;
import static android.R.attr.password;

public class FingerprintMatcher extends AppCompatActivity {
    String mainID;
    EditText ed1;
    String course, sid;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint_matcher);
        Bundle bundle = getIntent().getExtras();
        mainID = bundle.getString("fingerprint");
        course = bundle.getString("course");
        sid = bundle.getString("sid");
        ed1 = (EditText) findViewById(R.id.ed1);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;}
        return true;
    }
    public void markMePresent(View v){
        if(ed1.getText().toString().equals(mainID)){
            Toast.makeText(this, "Matched Successfully !", Toast.LENGTH_SHORT).show();
            try{
                String date = new Date().toString();
                mDatabase.child("courses").child(course).child("Students").child(sid).child("Present On").child(date).setValue("Present");
                finish();
            }catch(Exception e){
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "Incorrect finger ID !", Toast.LENGTH_SHORT).show();
        }
    }
}
