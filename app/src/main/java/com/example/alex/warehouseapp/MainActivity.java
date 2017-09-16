package com.example.alex.warehouseapp;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedClient;
    private Location clientLocation;


    private static final int FINE_LOCATION_PERMISSION = 12;
    private boolean allowFineLocation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup client
        fusedClient = LocationServices.getFusedLocationProviderClient(this);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){

            //Get last known location
            fusedClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    clientLocation = location;
                }
            });
        }
        //If not allowed, ask for permission
        else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    FINE_LOCATION_PERMISSION
            );
        }


        //Setup spinner
        Spinner departmentSpinner = (Spinner)findViewById(R.id.departmentSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.departments, R.layout.support_simple_spinner_dropdown_item
                );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departmentSpinner.setAdapter(adapter);

        //Setup search edit text to launch item activity
        final EditText searchItem = (EditText)findViewById(R.id.searchItem);
        Button submitButton = (Button)findViewById(R.id.submitButton);
        //Launch on button press
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get text and send with intent
                String value = searchItem.getText().toString();
                Intent nextActivity = new Intent(getBaseContext(), ItemsActivity.class);
                nextActivity.putExtra("search", value);
                startActivity(nextActivity);
            }
        });

        //Launch admin activity
        Button adminButton = (Button)findViewById(R.id.adminButton);
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create intent and send to admin page
                Intent intent = new Intent(getBaseContext(), AdminActivity.class);
                startActivity(intent);
            }
        });
    }

    //Handle permission requests
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch(requestCode) {
            case FINE_LOCATION_PERMISSION: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Get last known location
                    //Ignore error, permission has been accepted if this code is reached
                    fusedClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location)
                        {
                            clientLocation = location;
                        }
                    });

                }
                return;
            }
        }
    }
}
