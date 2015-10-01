package me.zujko.globalpics.activities;

import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import me.zujko.globalpics.R;
import me.zujko.globalpics.models.Location;

public class LocationActivity extends AppCompatActivity {

    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private SharedPreferences mPrefs;
    private Geocoder mGeoCoder;
    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mGeoCoder = new Geocoder(this, Locale.getDefault());
        mGson = new Gson();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Locations");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        createMap(savedInstanceState);
    }

    private void createMap(Bundle savedInstanceState) {
        mMapView = (MapView) findViewById(R.id.mapview);
        mMapView.onCreate(savedInstanceState);
        mGoogleMap = mMapView.getMap();
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Address address = storeLocation(latLng);
                mGoogleMap.addMarker(createMarker(address,latLng));
            }
        });
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(marker.isInfoWindowShown()) {
                    marker.hideInfoWindow();
                } else {
                    marker.showInfoWindow();
                }
                return false;
            }
        });
    }

    private Address storeLocation(LatLng latLng) {
        try {
            List<Address> addresses = mGeoCoder.getFromLocation(latLng.latitude,latLng.longitude,1);
            if(addresses.size() > 0) {
                Address addr = addresses.get(0);
                Location location = new Location(latLng.latitude, latLng.longitude, addr.getLocality());
                mPrefs.edit().putString(addr.getLocality(), mGson.toJson(location)).apply();
                return addr;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private MarkerOptions createMarker(Address address,LatLng latLng) {
        return new MarkerOptions().title(address.getLocality()+", "+address.getAdminArea()).position(latLng);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.location_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mMapView.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }
}
