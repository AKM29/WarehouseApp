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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //Location variables
    private FusedLocationProviderClient fusedClient;
    private Location clientLocation;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;

    private static final int FINE_LOCATION_PERMISSION = 12;
    private boolean allowFineLocation = false;

    //Database variables
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    //Store and item variables
    private Store closestStore;
    private ArrayList<Store> Stores = new ArrayList<>();
    private ArrayList<Item> Items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup client
        fusedClient = LocationServices.getFusedLocationProviderClient(this);

        //Setup call back
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for(Location location : locationResult.getLocations()) {
                    clientLocation = location;
                }
            }
        };

        //Set location settings
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){

            //Get last known location
            fusedClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    clientLocation = location;
                }
            });

            //Get location updates
            fusedClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    null);
        }
        //If not allowed, ask for permission
        else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    FINE_LOCATION_PERMISSION
            );
        }

        //Set default store
        closestStore = new Store("No store selected", 0, 0);

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

        //Get stores
        getStores();
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

                    //Get location updates
                    //Ignore error, permission has been accepted if this code is reached
                    fusedClient.requestLocationUpdates(locationRequest,
                            locationCallback,
                            null);
                }
                return;
            }
        }
    }

    //Get stores
    private void getStores() {
        //Stores reference
        DatabaseReference storeRef = ref.child("stores");

        //Loop and add stores
        storeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Get stores
                Map<String, Object> storesData = (Map<String, Object>)dataSnapshot.getValue();
                //Iterate and add to list
                for(Map.Entry<String, Object> entry : storesData.entrySet()){
                    //Get store
                    Map<String, Object> data = (Map<String, Object>)entry.getValue();
                    Map meta = (Map)data.get("meta");
                    Map<String, Object> items = (Map<String, Object>)data.get("items");

                    //Add to list
                    if(meta != null) {
                        Store s = new Store((String) meta.get("name"), (double)meta.get("latitude"), (double)meta.get("longitude"));

                        //Add items
                        ArrayList<Item> itemList = new ArrayList<>();
                        if(items != null) {
                            for(Map.Entry<String, Object> itemx : items.entrySet()) {
                                Map<String, Object> item = (Map<String, Object>)itemx.getValue();

                                Item i = new Item((String)item.get("name"), (String)item.get("description"), (String)item.get("department"), (double)item.get("price"));
                            }
                        }

                        Stores.add(s);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //Set closest store

}
