package com.example.akhilsingla.biometricattendancesystem;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup_activity extends AppCompatActivity {
    Button b1;
    private EditText eid, password, confpassword, phone, name;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_activity);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        eid = (EditText)findViewById(R.id.editText);
        password = (EditText)findViewById(R.id.editText2);
        confpassword = (EditText)findViewById(R.id.editText3);
        phone = (EditText)findViewById(R.id.editText4);
        name = (EditText) findViewById(R.id.editText5);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        b1= (Button) findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    insertData();
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
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
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean phoneCheck(){
        String s = phone.getText().toString();
        return (s.length() == 10);
    }

    public class teacher{
        public String Name, Phone, Email, Password;
        public teacher(){

        }
        public teacher(String Name, String Phone, String Email, String Password){
            this.Name = Name;
            this.Phone = Phone;
            this.Email = Email;
            this.Password = Password;
        }
    }

    public void takeMeToSelectMySemester(){
        Intent intent = new Intent(this, after_signup_activity.class);
        intent.putExtra("msg",phone.getText().toString());
        startActivity(intent);
        finish();
    }

    public void insertData(){
        try{
            if(!phoneCheck()){
                Toast.makeText(getApplicationContext(), "Enter correct phone number", Toast.LENGTH_LONG).show();
                return;
            }
            String testingVariable =  password.getText().toString();
            if(testingVariable.length() < 6){
                Toast.makeText(getApplicationContext(), "Password must be atleast 6 characters long", Toast.LENGTH_LONG).show();
                return;
            }
            if(!testingVariable.equals(confpassword.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Enter same password in confirm password", Toast.LENGTH_LONG).show();
                return;
            }
            teacher t = new teacher(name.getText().toString(), phone.getText().toString(),
                    eid.getText().toString(), password.getText().toString());
            mDatabase.child("users").child(phone.getText().toString()).setValue(t);
            Toast.makeText(getApplicationContext(), "Account created Successfully !", Toast.LENGTH_SHORT).show();
            takeMeToSelectMySemester();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
