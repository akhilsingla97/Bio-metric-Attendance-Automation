package com.example.akhilsingla.biometricattendancesystem;

import android.app.Dialog;
import android.app.IntentService;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class add_student extends AppCompatActivity {
    TextView sem, course;
    EditText name, sid;
    String phoneno;
    ArrayList<String> courses = new ArrayList<String>();
    public int counter;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        sem = (TextView) findViewById(R.id.edit3);
        course = (TextView) findViewById(R.id.edit4);
        name = (EditText) findViewById(R.id.edit1);
        sid = (EditText) findViewById(R.id.edit2);
        Bundle bundle = getIntent().getExtras();
        phoneno = bundle.getString("msg");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        counter = 0;

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
    public void scanIt(View v){
        Intent i = new Intent(this, scanfinger.class);
        i.putExtra("sem", format(sem.getText().toString()));
        i.putExtra("course", format(course.getText().toString()));
        i.putExtra("name", name.getText().toString());
        i.putExtra("sid", sid.getText().toString());
        if(name.getText().toString() == "" || sid.getText().toString() == "" ||
                sem.getText().toString() == "" || course.getText().toString() == "") return;
        startActivity(i);
        finish();
    }
    private void fetchData(DataSnapshot dataSnapshot)
    {
        courses.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            String cid = (String) ds.getKey();
            courses.add(cid);
        }
    }

    public String format(String s){
        return s.substring(0, s.length()-3);
    }

    public void getCoursesForDb(){
        try{
            mDatabase.child("users").child(phoneno).child(format(sem.getText().toString())).addValueEventListener(new ValueEventListener() {
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
    public void showRadioButtonDialog(View v) {

        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.radioselection);
        ArrayList<String> stringList=new ArrayList<>();  // here is list
        for(int i=0;i<8;i++) {
            stringList.add("Semester " + (i + 1) + "   ");
        }
        RadioGroup rg = dialog.findViewById(R.id.radio_group);

        for(int i=0;i<stringList.size();i++){
            RadioButton rb=new RadioButton(this); // dynamically creating RadioButton and adding to RadioGroup.
            rb.setText(stringList.get(i));
            rg.addView(rb);
        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int childCount = group.getChildCount();
                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) group.getChildAt(x);
                    if (btn.getId() == checkedId) {
                        Log.e("selected RadioButton->",btn.getText().toString());
                        sem.setText(btn.getText().toString());
                        getCoursesForDb();
                    }
                }
            }
        });
        dialog.show();
    }

    public void showRadioButtonDialogCourse(View v) {

        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.radioselection);
        ArrayList<String> stringList=new ArrayList<>();  // here is list
        for(String c : courses) {
            stringList.add(c + "   ");
        }
        RadioGroup rg = dialog.findViewById(R.id.radio_group);

        for(int i=0;i<stringList.size();i++){
            RadioButton rb=new RadioButton(this); // dynamically creating RadioButton and adding to RadioGroup.
            rb.setText(stringList.get(i));
            rg.addView(rb);
        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int childCount = group.getChildCount();
                for (int x = 0; x < childCount; x++) {
                    RadioButton btn = (RadioButton) group.getChildAt(x);
                    if (btn.getId() == checkedId) {
                        Log.e("selected RadioButton->",btn.getText().toString());
                        course.setText(btn.getText().toString());
                    }
                }
            }
        });
        dialog.show();
    }


}
