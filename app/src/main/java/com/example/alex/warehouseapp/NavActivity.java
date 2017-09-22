package com.example.alex.warehouseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class NavActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        //Get department from intent
        Intent intent = this.getIntent();
        String Department = intent.getStringExtra("Department");
        String Name = intent.getStringExtra("Name");
        Toast.makeText(this, Name + " are found in the " + Department + " section", Toast.LENGTH_SHORT).show();
    }
}
