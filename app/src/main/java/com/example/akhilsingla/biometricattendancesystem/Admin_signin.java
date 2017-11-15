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


public class Admin_signin extends AppCompatActivity {
    Button login;
    private EditText adminID, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signin);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        adminID = (EditText)findViewById(R.id.editText6);
        password = (EditText)findViewById(R.id.editText7);
        login= (Button) findViewById(R.id.button2);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate_login();
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
                onBackPressed();
                break;}
        return true;
    }
    public void startNewActivity(){
        Intent intent = new Intent(this, after_admin_signin.class);
        intent.putExtra("msg", adminID.getText().toString());
        startActivity(intent);
        finish();
    }
    void validate_login()
    {
        try{
            if(adminID.getText().toString().equals("admin") && password.getText().toString().equals("asdfgh") )
            {
                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                startNewActivity();
            }
            else if(adminID.getText().toString().equals("admin") == false)
            {
                Toast.makeText(getApplicationContext(),"adminID is wrong!!",Toast.LENGTH_SHORT).show();
            }
            else if(password.getText().toString().equals("asdfgh") == false)
            {
                Toast.makeText(getApplicationContext(),"Wrong Password!!",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
}
