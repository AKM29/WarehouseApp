package com.example.alex.warehouseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    //Array of stores
    private ArrayList<String> stores = new ArrayList<>();

    //Reference to firebase
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //Setup spinner
        final Spinner storeSpinner = (Spinner)findViewById(R.id.storeSpinner);
        //Setup for default
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.defaultstores, R.layout.support_simple_spinner_dropdown_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storeSpinner.setAdapter(adapter);

        //setup database listener
        DatabaseReference storeRef = ref.child("stores");
        storeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Get stores
                Map<String, Object> storesData = (Map<String, Object>)dataSnapshot.getValue();
                //Iterate and add to list
                for(Map.Entry<String, Object> entry : storesData.entrySet()){
                    //Get item
                    Map<String, Object> data = (Map<String, Object>)entry.getValue();
                    Map meta = (Map)data.get("meta");
                    //Add to list
                    if(meta != null) {
                        stores.add((String) meta.get("name"));
                    }
                }

                //Bind spinner to arraylist
                ArrayAdapter adapter = new ArrayAdapter(getBaseContext(),
                        R.layout.support_simple_spinner_dropdown_item, stores
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                storeSpinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Logic for creating an Item
        Button createItem = (Button)findViewById(R.id.createItem);
        createItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get data
                String name = ((EditText)findViewById(R.id.itemName)).getText().toString();
                String department = ((EditText)findViewById(R.id.itemDepartment)).getText().toString();
                double price = Double.parseDouble(((EditText)findViewById(R.id.itemPrice)).getText().toString());
                String description = ((EditText)findViewById(R.id.itemDescription)).getText().toString();
                String store = ((Spinner)findViewById(R.id.storeSpinner)).getSelectedItem().toString();

                //Unique key for item
                String key = ref.child("stores").child(store).child("items").push().getKey();

                //Create item and map
                Item item = new Item(name, description, department, price);
                Map<String, Object> update = new HashMap<>();
                Map<String, Object> items = item.map();
                update.put("/stores/" + store + "/items/" + key, items);

                //Update database
                ref.updateChildren(update);

                //Notify of added item
                Toast.makeText(getBaseContext(), name + " added!", Toast.LENGTH_SHORT).show();
            }
        });

        //Logic for creating a store
        Button createStore = (Button)findViewById(R.id.createStore);
        createStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get data
                String name = ((EditText)findViewById(R.id.storeName)).getText().toString();

                //Attempt to get lat long
                double latitude = Double.parseDouble(((EditText)findViewById(R.id.storeLatitude)).getText().toString());
                double longitude = Double.parseDouble(((EditText)findViewById(R.id.storeLongitude)).getText().toString());

                //Create store
                Store store = new Store(name, latitude, longitude);
                //Create item and map
                Map<String, Object> update = new HashMap<>();
                Map<String, Object> items = store.map();
                update.put("/stores/" + name, items);

                //Update database
                ref.updateChildren(update);

                //Notify of added item
                Toast.makeText(getBaseContext(), name + " added!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
