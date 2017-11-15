package com.example.akhilsingla.biometricattendancesystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class startAttendance extends AppCompatActivity implements AdapterView.OnItemClickListener{
    String course;
    ListView lv;
    ArrayList<String> students = new ArrayList<String>();
    ArrayList<String> fingerPrintID = new ArrayList<>();
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_attendance);
        Bundle bundle = getIntent().getExtras();
        try {
            course = bundle.getString("course");
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        };
        mDatabase = FirebaseDatabase.getInstance().getReference();
        getStudents();
    }
    int counter = 0;
    @Override
    public void onBackPressed(){
        if(counter == 0){
            ++counter;
            Toast.makeText(this, "Press one more time to stop.", Toast.LENGTH_SHORT).show();
        }else{
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView temp = (TextView) view;
        Toast.makeText(this, fingerPrintID.get(i) + " is selected !", Toast.LENGTH_SHORT).show();
    }
    private void fetchData(DataSnapshot dataSnapshot)
    {
        students.clear();
        fingerPrintID.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            String cid = (String) ds.getKey();
            String fid = (String) ds.child("fid").getValue();
            fingerPrintID.add(fid);
            students.add(cid);
        }
        lv = (ListView) findViewById(R.id.select_students);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 ,students);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    public void getStudents(){
        try{
            mDatabase.child("courses").child(course).child("Students").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    fetchData(dataSnapshot);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
}
