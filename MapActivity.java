package com.example.linyin.museumfinder;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;


import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        String name = getIntent().getStringExtra("name");
        String lat = getIntent().getStringExtra("lat");
        String lng = getIntent().getStringExtra("lng");
        LatLng latLngOnMap = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));

        /*display google map and mark it, referring from example code in the google map api website */
        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOnMap,16));
        map.addMarker(new MarkerOptions().position(latLngOnMap).title(name));
    }

}
