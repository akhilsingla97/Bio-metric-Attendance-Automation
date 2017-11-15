package com.example.akhilsingla.biometricattendancesystem;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class add_courses extends AppCompatActivity {
    String parentResult, PhoneNumber, Semester;
    private DatabaseReference mDatabase;
    EditText cname, cid, fid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses);
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
        Toast.makeText(this, "Selected semester is " + (Integer.parseInt(Semester) + 1), Toast.LENGTH_SHORT).show();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        cname = (EditText) findViewById(R.id.edit1);
        cid = (EditText) findViewById(R.id.edit2);
        fid = (EditText) findViewById(R.id.edit3);
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
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

    public void returnBack(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    public void returnToSemesterSelection(){
        Intent intent = new Intent(this, after_signup_activity.class);
        startActivity(intent);
        finish();
    }

    public void addCourseToDb(View v){
        if(cid.getText().toString().matches("")){
            Toast.makeText(this, "Course Id can't be empty !", Toast.LENGTH_SHORT).show();
            return ;
        }
        if(cname.getText().toString().matches("")){
            Toast.makeText(this, "Course name can't be empty !", Toast.LENGTH_SHORT).show();
            return;
        }
        if(fid.getText().toString().matches("")){
            Toast.makeText(this, "Faculty Id can't be empty !", Toast.LENGTH_SHORT).show();
            return;
        }

        courseData t = new courseData(cid.getText().toString(), cname.getText().toString(),
                fid.getText().toString());
        mDatabase.child("users").child(PhoneNumber).child("Semester " + (Integer.parseInt(Semester) + 1)).child(cid.getText().toString()).setValue(t);
        Toast.makeText(this, "Course Added Successfully !", Toast.LENGTH_SHORT).show();


        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Course Added Successfully !\n" +
                "Click Yes to add more courses for this semester");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        cid.setText("");
                        fid.setText("");
                        cname.setText("");
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
}
