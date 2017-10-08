package com.example.akhilsingla.biometricattendancesystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class after_signup_activity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lv;
    String[] semesters = new String[8];
    private String phoneNumber;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_signup_activity);
        Bundle bundle = getIntent().getExtras();
        phoneNumber = bundle.getString("msg");
        for(int i = 0 ; i < 8 ; i ++){
            semesters[i] = "Semester " + (i + 1);
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
        lv = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.semester_selection ,semesters);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView temp = (TextView) view;
        Toast.makeText(this, temp.getText().toString() + " is selected !", Toast.LENGTH_SHORT).show();
        insertSemester(i);
    }

    private void insertSemester(int i) {
        try{
            Intent intent = new Intent(this, add_courses.class);
            intent.putExtra("msg", phoneNumber + " " + i);
            startActivity(intent);
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
