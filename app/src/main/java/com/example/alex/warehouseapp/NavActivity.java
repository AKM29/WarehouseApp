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
 * Created by Lulwah & Sarah.
 */
public class NavActivity extends FragmentActivity implements OnMapReadyCallback {

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
        //calling methods
        addingMarkers();
        kmlLAyer();


    }


    //Adding icon on each section on the map
    protected void addingMarkers() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-37.789339, 175.308230), 10));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(20), 2000, null);


        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Green Garden", new LatLng(-37.78859, 175.30781));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Indoor Furniture", new LatLng(-37.78869, 175.30779));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Storage", new LatLng(-37.78876, 175.30782));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Lighting", new LatLng(-37.78883, 175.30784));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Home Texlies", new LatLng(-37.78889, 175.30786));


        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Homeware", new LatLng(-37.78899, 175.3079));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Appliances", new LatLng(-37.78905, 175.30792));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Event Canyon", new LatLng(-37.7891, 175.30794));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Electronics", new LatLng(-37.78932, 175.30802));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Media", new LatLng(-37.78921, 175.30797));

        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Dry Gardening", new LatLng(-37.78856, 175.30794));

        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Hardware", new LatLng(-37.78855, 175.30801));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Confectionery", new LatLng(-37.78912, 175.30836));

        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Toys", new LatLng(-37.78853, 175.30811));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Sport and Fitness", new LatLng(-37.78862, 175.30828));
        addIcon(iconFactory,"Sport and Fitness", new LatLng(-37.78847, 175.30819));

        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Wheels", new LatLng(-37.78857, 175.30824));


        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Camping", new LatLng(-37.78867, 175.3082));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Luggage", new LatLng(-37.78874, 175.30831));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "School Tex", new LatLng(-37.78872, 175.30823));


        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Footwear", new LatLng(-37.78886, 175.3083));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "H & B", new LatLng(-37.78897, 175.30834));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Stationary & Crafts", new LatLng(-37.78904, 175.30836));


        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Party", new LatLng(-37.78918, 175.30841));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Snacks", new LatLng(-37.78924, 175.3084));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Pet Care", new LatLng(-37.7893, 175.30847));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Checkout & Service", new LatLng(-37.78934, 175.3083));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Entrance", new LatLng(-37.78939, 175.30817));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Women's Clothing", new LatLng(-37.78902, 175.30814));


        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Jewelry", new LatLng(-37.7891, 175.30812));

        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Men's Clothing", new LatLng(-37.78881, 175.30803));


        iconFactory.setRotation(50);
        iconFactory.setStyle(IconGenerator.STYLE_BLUE);
        addIcon(iconFactory, "Kid's Clothing", new LatLng(-37.78888, 175.30814));


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
