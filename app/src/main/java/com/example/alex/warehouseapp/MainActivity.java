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
import android.widget.ListView;
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

                    //Get closest store
                    getClosestStore();
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
        getClosestStore();
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
                } else {
                    //Set default store
                    if(Stores.get(0) != null) {
                        closestStore = Stores.get(0);
                    }
                }
                return;
            }
        }
    }

    //Gets deals from closest store
    private void populateItems() {
        //Get store items from firebase
        DatabaseReference itemsref = ref.child(closestStore.getName()).child("Items");

        itemsref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Get items
                Map<String, Object> itemData = (Map<String, Object>)dataSnapshot.getValue();

                if(itemData != null) {
                    for (Map.Entry<String, Object> entry : itemData.entrySet()) {
                        //Get item
                        Map<String, Object> item = (Map<String, Object>) entry.getValue();
                        //Add to store
                        closestStore.addItem(new Item((String)item.get("Name"), (String)item.get("Description"), (String)item.get("Department"), (double)item.get("Price")));
                    }
                }

                //Display deals
                displayDeals();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //Displays deals from list of closest store
    private void displayDeals() {
        //Display deals
        ItemAdapter adaptItem = new ItemAdapter(this, 0, closestStore.getDeals());
        ListView displayItems = (ListView) findViewById(R.id.dealsView);
        displayItems.setAdapter(adaptItem);
    }

    //Gets the closest store from meta data
    private void getClosestStore() {
        //Get store meta data from firebase
        DatabaseReference storeref = ref.child("StoreMeta");

        //Get the closest store based on location
        storeref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float distance = 0;
                Location compare = new Location("");
                Map<String, Object> metaData = (Map<String, Object>)dataSnapshot.getValue();

                //Iterate through store and get closest store
                    for (Map.Entry<String, Object> entry : metaData.entrySet()) {
                        //Get store
                        Map<String, Object> store = (Map<String, Object>) entry.getValue();

                            //Get location of store
                            compare.setLatitude((double) store.get("Latitude"));
                            compare.setLongitude((double) store.get("Longitude"));

                            //Check if closer
                            if (clientLocation.distanceTo(compare) < distance || distance == 0) {
                                closestStore = new Store((String) store.get("Name"), (double) store.get("Latitude"), (double) store.get("Longitude"));
                            }

                    }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Populate items
        populateItems();
    }
}
