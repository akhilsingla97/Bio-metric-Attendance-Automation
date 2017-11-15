package com.example.akhilsingla.biometricattendancesystem;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectCourseActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    String parentResult, PhoneNumber, Semester;
    ListView lv;
    ArrayList<String> courses = new ArrayList<String>();
    public int counter;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        try {
            parentResult = bundle.getString("msg");
            PhoneNumber = fetchPhone(parentResult);
            Semester = fetchSem(parentResult);
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
        counter = 0;
        getCoursesForDb();
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView temp = (TextView) view;
        Toast.makeText(this, temp.getText().toString() + " is selected !", Toast.LENGTH_SHORT).show();
        try{
            Intent intent = new Intent(this, startAttendance.class);
            intent.putExtra("course",temp.getText().toString());
            startActivity(intent);
            finish();
        }catch(Exception e){
            Toast.makeText(this, "Some Error Occurs -> " + e.toString(), Toast.LENGTH_SHORT).show();
        }
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

    public String fetchPhone(String s){
        String ret = "";
        for(int i = 0 ; i < s.length() ; i ++){
            if(s.charAt(i) == ' '){
                break;
            }
            ret = ret + s.charAt(i);
        }
        return  ret;
    }
    public String fetchSem(String s){
        String ret = "";
        int i;
        for( i = 0 ; i < s.length() ; i ++){
            if(s.charAt(i) == ' ') {
                i++;
                break;
            }
        }
        for(; i < s.length() ; i ++){
            ret = ret + s.charAt(i);
        }
        return  ret;
    }

    public class courseData{
        public String CourseId, CourseName, FacultyId;
        public courseData(){

        }
        public courseData(String CourseId, String CourseName, String FacultyId){
            this.CourseId = CourseId;
            this.CourseName = CourseName;
            this.FacultyId = FacultyId;
        }
    }
    private void fetchData(DataSnapshot dataSnapshot)
    {
        courses.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            String cid = (String) ds.getKey();
            courses.add(cid);
        }
        lv = (ListView) findViewById(R.id.selectCourse);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 ,courses);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }
    public void getCoursesForDb(){
        try{
            mDatabase.child("users").child(PhoneNumber).child("Semester " + Semester).addValueEventListener(new ValueEventListener() {
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
