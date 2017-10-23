package com.example.akhilsingla.biometricattendancesystem;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SelectsemforAttendance extends AppCompatActivity implements AdapterView.OnItemClickListener  {
    ListView lv;
    String[] semesters = new String[8];
    private String phoneNumber;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectsemfor_attendance);
        Bundle bundle = getIntent().getExtras();
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
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
    public void onBackPressed(){
        System.gc();
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            TextView temp = (TextView) view;
            Toast.makeText(this, temp.getText().toString() + " is Selected !", Toast.LENGTH_SHORT).show();
            insertSemester(i);
        }catch(Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void insertSemester(int i) {
        try{
            Intent intent = new Intent(this, SelectCourseActivity.class);
            intent.putExtra("msg", phoneNumber + " " + (i + 1));
            startActivity(intent);
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
