package com.example.alex.warehouseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    //Array of stores
    String[] stores;

    //Reference to firebase
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //Setup spinner
        Spinner stores = (Spinner)findViewById(R.id.storeSpinner);

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
                update.put("/stores/" + store + "/items" + key, items);

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
                double latitude = Double.parseDouble(((EditText)findViewById(R.id.storeName)).getText().toString());
                double longitude = Double.parseDouble(((EditText)findViewById(R.id.storeName)).getText().toString());

                //Create store
                Store store = new Store(name, latitude, longitude);
            }
        });
    }
}
