package com.example.alex.warehouseapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by Sarah & Lulwah.
 */

public class Search extends Activity {

    ListView listView;
    SearchView searchView;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listView= (ListView)findViewById(R.id.list_view);
        ArrayList<String> arrayItems =new ArrayList<>();
        arrayItems.addAll(Arrays.asList(getResources().getStringArray(R.array.store_items)));
        adapter= new ArrayAdapter<String>(Search.this, android.R.layout.simple_list_item_1,arrayItems);
        listView.setAdapter(adapter);
        searchView=(SearchView)findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.SearchMenu);
        searchView = (SearchView) item.getActionView();
        return super.onCreateOptionsMenu(menu);
    }

}
