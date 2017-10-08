package com.example.akhilsingla.biometricattendancesystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class after_signup_activity extends AppCompatActivity {
    ListView lv;
    String[] semesters = new String[8];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_signup_activity);
        for(int i = 0 ; i < 8 ; i ++){
            semesters[i] = "Semester " + (i + 1);
        }
        lv = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.semester_selection ,semesters);
        lv.setAdapter(adapter);
    }
}
