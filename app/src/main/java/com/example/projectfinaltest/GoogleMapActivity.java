package com.example.projectfinaltest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectfinaltest.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
public class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap gm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        createMap();
    }
    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void createMap() {
        SupportMapFragment smf = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        smf.getMapAsync(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        createMap();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng hcm = new LatLng(10.762622, 106.660172);
        gm = googleMap;
        gm.addMarker(new MarkerOptions().position(hcm).title("Marker in HCM City"));
        CameraPosition cp = new CameraPosition.Builder().target(hcm).zoom(13).build();
        gm.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
    }
}