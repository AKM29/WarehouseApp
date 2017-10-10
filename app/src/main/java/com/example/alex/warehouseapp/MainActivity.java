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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/*
    Author: Alex
 */
public class MainActivity extends AppCompatActivity {

    //Location variables
    private FusedLocationProviderClient fusedClient;
    private Location clientLocation;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;

    private static final int FINE_LOCATION_PERMISSION = 12;

    private boolean notify = true;

    //Database variables
    private DatabaseReference ref;

    //Store and item variables
    private Store closestStore;
    private ArrayList<Store> Stores = new ArrayList<>();
    private ArrayList<Item> Items = new ArrayList<>();

    //
    private TextView welcomeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        ref = FirebaseDatabase.getInstance().getReference();

        //Setup client
        fusedClient = LocationServices.getFusedLocationProviderClient(this);

        //Setup call back
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for(Location location : locationResult.getLocations()) {
                    clientLocation = location;

                    //Get stores
                    getClosestStores();
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

                    if(notify) {
                        notifyClient();
                    }
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

        //Launch map activity
        Button mapButton = (Button)findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create intent and send to admin page
                Intent intent = new Intent(getBaseContext(), NavActivity.class);
                startActivity(intent);
            }
        });

        //Add click to item
        ListView itemList = (ListView)findViewById(R.id.dealsView);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Start nav activity
                Intent startNav = new Intent(getBaseContext(), MapsActivity.class);
                startNav.putExtra("Department", closestStore.getDeals().get(position).getDepartment());
                startNav.putExtra("Name", closestStore.getDeals().get(position).getName());
                startActivity(startNav);
            }
        });

        //
        welcomeView = (TextView)findViewById(R.id.welcomeView);

        displayDeals();
    }

    //Show notification
    private void notifyClient() {
        Location location = new Location("");
        location.setLatitude(closestStore.getLatitude());
        location.setLongitude(closestStore.getLongitude());

        //If within 10 meters
        if(clientLocation.distanceTo(location) < 10) {
            Toast.makeText(this, "Welcome to The Warehouse " + closestStore.getName(), Toast.LENGTH_SHORT).show();
            notify = false;
        }
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

                            //Check if in store
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

    //Get closest store
    private void getClosestStores() {
        //Get reference to store data
        DatabaseReference storeref = ref.child("StoreMeta");

        //Listen for updates
        storeref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Variables
                float distance = 0;
                Location location = new Location("");

                //Get data
                Map<String, Object> StoreMeta = (Map<String, Object>)dataSnapshot.getValue();

                //Loop through stores
                for(Map.Entry<String, Object> entry : StoreMeta.entrySet()) {
                    //Get Store
                    Map<String, Object> store = (Map<String, Object>)entry.getValue();

                    //If closer set as store
                    location.setLatitude((double)store.get("Latitude"));
                    location.setLongitude((double)store.get("Longitude"));
                    if(distance == 0 || clientLocation.distanceTo(location) < distance){
                        //Set new distance
                        distance = clientLocation.distanceTo(location);

                        //Set store
                        closestStore = new Store((String)store.get("Name"), location.getLatitude(), location.getLongitude());
                    }
                }

                welcomeView.setText("The Warehouse " + closestStore.getName() + "!");
                Items = closestStore.getDeals();

                getItems();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //Get items for store
    private void getItems() {
        //Get reference to store
        DatabaseReference itemsref = ref.child(closestStore.getName()).child("Items");

        itemsref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Get data
                Map<String, Object> itemData = (Map<String, Object>)dataSnapshot.getValue();

                //Loop through items
                if(itemData != null) {
                    for (Map.Entry<String, Object> entry : itemData.entrySet()) {
                        //Get item
                        Map<String, Object> item = (Map<String, Object>) entry.getValue();

                        //Add item to store
                        Item i = new Item((String) item.get("Name"), (String) item.get("Description"), (String) item.get("Department"), (double) item.get("Price"));
                        if(item.get("Image") != null) {
                            i.setImage((String)item.get("Image"));
                        }
                        closestStore.addItem(i);
                    }
                }

                displayDeals();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //Displays deals
    private void displayDeals() {
        //Display deals
        ItemAdapter adaptItem = new ItemAdapter(this, 0, Items);
        ListView displayItems = (ListView) findViewById(R.id.dealsView);
        displayItems.setAdapter(adaptItem);
    }


}
