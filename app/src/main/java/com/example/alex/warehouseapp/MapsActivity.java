package com.example.alex.warehouseapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.kml.KmlLayer;
import com.google.maps.android.ui.IconGenerator;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.ITALIC;
import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;


/**
 * Created by Sarah & Lulwah.
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private IconGenerator iconFactory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iconFactory=new IconGenerator(this);
        if(googleServicesAvailable()){
            //Toast.makeText(this,"Perfect",Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_maps);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        }


    }
    //opening the search activity
    public void search(View v){
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    //adding the kml layer to the map
    public void kmlLAyer(){
        try {
            InputStream ins = getResources().openRawResource(getResources().getIdentifier("map", "raw", getPackageName()));

            KmlLayer kmlLayer = new KmlLayer(mMap, ins, getApplicationContext());

            kmlLayer.addLayerToMap();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

    }
    //Checking if google play service is available
    public boolean googleServicesAvailable(){
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if(isAvailable == ConnectionResult.SUCCESS){
            return true;
        }else if(api.isUserResolvableError(isAvailable)){
            Dialog dialog = api.getErrorDialog(this, isAvailable,0);
            dialog.show();

        }else {
            Toast.makeText(this,"Cannot connect to play services",Toast.LENGTH_LONG).show();
        }
        return false;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in warehouse and move the camera
        LatLng warehouse = new LatLng(-37.789339, 175.308230);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(warehouse,20));
        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(20), 2000, null);
        //calling the kml layer and adding markers method
        addingMarkers();
        kmlLAyer();

        //Get department from intent
        Intent intent = this.getIntent();
        String Department = intent.getStringExtra("Department");
        String Name = intent.getStringExtra("Name");
        Toast.makeText(this, Name + " are found in the " + Department + " section", Toast.LENGTH_SHORT).show();

        //Highlighting each section on the map
        if(Department.equals("Green Garden")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Green Garden", new LatLng(-37.78861, 175.30779));
        }else if(Department.equals("Indoor Furniture")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Indoor Furniture", new LatLng(-37.7887,175.30779));
        }else if(Department.equals("Storage")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Storage", new LatLng(-37.78879,175.30782));
        }else if(Department.equals("Storage")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Storage", new LatLng(-37.78879,175.30782));
        }else if(Department.equals("Lighting")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Lighting", new LatLng(-37.78887,175.30784));
        }else if(Department.equals("Window Funishings")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Window Funishings", new LatLng(-37.78893,175.30786));
        }else if(Department.equals("Home Textlies")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Home Textlies", new LatLng(-37.78899,175.30789));
        }else if(Department.equals("Homeware")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Homeware", new LatLng(-37.7891, 175.30792));
        }else if(Department.equals("Appliances")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Appliances", new LatLng(-37.7892, 175.30796));
        }else if(Department.equals("Event Canyon")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Event Canyon", new LatLng(-37.78926, 175.30798));
        }else if(Department.equals("Electronics")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Electronics", new LatLng(-37.78942, 175.30803));
        }else if(Department.equals("Media")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Media", new LatLng(-37.78933, 175.30801));
        }else if(Department.equals("Dry Gardening")){
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Dry Gardening", new LatLng(-37.78856, 175.30789));
        }else if(Department.equals("Hardware")){
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Hardware", new LatLng(-37.78858, 175.30795));
        }else if(Department.equals("Automative")){
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Automative", new LatLng(-37.78862, 175.308));
        }else if(Department.equals("Toys")){
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Toys", new LatLng(-37.78854, 175.30807));
        }else if(Department.equals("Sports")){
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Sports", new LatLng(-37.78855, 175.30817));
        }else if(Department.equals("Wheels")){
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Wheels", new LatLng(-37.78857, 175.30824));
        }else if(Department.equals("Sport and Fitness")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Sport and Fitness", new LatLng(-37.78866, 175.30829));
        }else if(Department.equals("Camping")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Camping", new LatLng(-37.78872, 175.30824));
        }else if(Department.equals("Luggage")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Luggage", new LatLng(-37.78877, 175.30828));
        }else if(Department.equals("School Tex")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "School Tex", new LatLng(-37.78883, 175.3083));
        }else if(Department.equals("Men's Shoes")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Footwear", new LatLng(-37.78897, 175.30836));
        }else if(Department.equals("H&B")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "H & B", new LatLng(-37.78912, 175.30841));
        }else if(Department.equals("Stationary & Crafts")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Stationary & Crafts", new LatLng(-37.7892, 175.30845));
        }else if(Department.equals("Party")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Party", new LatLng(-37.78925, 175.30849));
        }else if(Department.equals("Snacks")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Snacks", new LatLng(-37.78928, 175.30847));
        }else if(Department.equals("Pet Care")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Pet Care", new LatLng(-37.78933, 175.30849));
        }else if(Department.equals("Women's Clothing")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Women's Clothing", new LatLng(-37.78908, 175.30823));
        }else if(Department.equals("Jewelry")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Jewelry", new LatLng(-37.78905, 175.30811));
        }else if(Department.equals("Men's Clothing")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Men's Clothing", new LatLng(-37.78881, 175.308));
            addIcon(iconFactory, "Men's Clothing", new LatLng(-37.78899, 175.30808));
        }else if(Department.equals("Kid's Clothing")){
            iconFactory.setRotation(50);
            iconFactory.setStyle(IconGenerator.STYLE_RED);
            addIcon(iconFactory, "Kid's Clothing", new LatLng(-37.78879, 175.30811));
        }

    }

    //Adding icon on each section on the map
    protected void addingMarkers() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-37.789339, 175.308230), 10));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(20), 2000, null);


        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Green Garden", new LatLng(-37.78861, 175.30779));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Indoor Furniture", new LatLng(-37.7887,175.30779));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Storage", new LatLng(-37.78879,175.30782));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Lighting", new LatLng(-37.78887,175.30784));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Window Funishings", new LatLng(-37.78893,175.30786));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Home Textlies", new LatLng(-37.78899,175.30789));


        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Homeware", new LatLng(-37.7891, 175.30792));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Appliances", new LatLng(-37.7892, 175.30796));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Event Canyon", new LatLng(-37.78926, 175.30798));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Electronics", new LatLng(-37.78942, 175.30803));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Media", new LatLng(-37.78933, 175.30801));

        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Dry Gardening", new LatLng(-37.78856, 175.30789));

        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Hardware", new LatLng(-37.78858, 175.30795));

        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Automative", new LatLng(-37.78862, 175.308));

        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Toys", new LatLng(-37.78854, 175.30807));

        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Sports", new LatLng(-37.78855, 175.30817));

        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Wheels", new LatLng(-37.78857, 175.30824));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Sport and Fitness", new LatLng(-37.78866, 175.30829));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Camping", new LatLng(-37.78872, 175.30824));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Luggage", new LatLng(-37.78877, 175.30828));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "School Tex", new LatLng(-37.78883, 175.3083));


        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Footwear", new LatLng(-37.78897, 175.30836));


        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "H&B", new LatLng(-37.78912, 175.30841));


        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Stationary&Crafts", new LatLng(-37.7892, 175.30845));


        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Party", new LatLng(-37.78925, 175.30849));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Snacks", new LatLng(-37.78928, 175.30847));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Pet Care", new LatLng(-37.78933, 175.30849));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Checkout & Service", new LatLng(-37.78934, 175.3083));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Entrance", new LatLng(-37.78939, 175.30817));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Women's Clothing", new LatLng(-37.78908, 175.30823));


        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Jewelry", new LatLng(-37.78905, 175.30811));


        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Men's Clothing", new LatLng(-37.78881, 175.308));


        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Men's Clothing", new LatLng(-37.78899, 175.30808));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Kid's Clothing", new LatLng(-37.78879, 175.30811));


    }
    //add icon method
    private void addIcon(IconGenerator iconFactory, CharSequence text, LatLng position) {
        MarkerOptions markerOptions = new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(text))).
                position(position).
                anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());

        mMap.addMarker(markerOptions);
    }



}
