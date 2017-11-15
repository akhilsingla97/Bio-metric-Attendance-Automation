package com.example.akhilsingla.biometricattendancesystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class scanfinger extends AppCompatActivity {
    String name, sid, fid, sem, course;
    EditText fingerprint;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanfinger);
        Bundle bundle = getIntent().getExtras();
        sem = bundle.getString("sem");
        course = bundle.getString("course");
        name = bundle.getString("name");
        sid = bundle.getString("sid");
        fingerprint = (EditText) findViewById(R.id.edit1);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    @Override
    public void onBackPressed(){
        System.gc();
        finish();
    }
    public class student{
        public String name, sid, fid;
        public student(){

        }
        public student(String Name, String sid, String fid){
            this.name = Name;
            this.sid = sid;
            this.fid= fid;
        }
    }

    public void insertStudent(View v){
        try{

            student t = new student(name, sid,
                    fingerprint.getText().toString());
            mDatabase.child("courses").child(course).child("Semester").setValue(sem);
            mDatabase.child("courses").child(course).child("Students").child(sid).setValue(t);
            Toast.makeText(getApplicationContext(), "Student Added Successfully !", Toast.LENGTH_SHORT).show();
            finish();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
