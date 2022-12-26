package com.example.projectfinaltest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.TaskStackBuilder;

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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class PostionActivity extends AppCompatActivity {

    //Initialize variable;
    Spinner spType;
    Button btFind;
     SupportMapFragment supportMapFragment;
    private GoogleMap map;
    FusedLocationProviderClient fusedLocationProviderClient;
    double currentLat = 0, currentLong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postion);

        spType = findViewById(R.id.sp_type);
        btFind = findViewById(R.id.bt_find);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        String[] placeTypeList = {"atm","bank","hospital","movie_theater","restaurant"};

        String[] placeNameList = {"ATM","Bank","Hospital","Movie Theater","Restaurant"};

        spType.setAdapter(new ArrayAdapter<>(PostionActivity.this,
                android.R.layout.simple_spinner_dropdown_item,placeNameList));

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if(ActivityCompat.checkSelfPermission(PostionActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            getCurrentLocation();
        }else{
            ActivityCompat.requestPermissions(PostionActivity.this
                    ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }

        btFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = spType.getSelectedItemPosition();
                //https://www.google.com/maps/place/Chu%E1%BB%93ng+Ch%C3%B3+Inox+S%C3%A0i+G%C3%B2n/@10.7855513,106.5886039,17z/data=!3m1!4b1!4m5!3m4!1s0x31752c7a1fdf3265:0xff1c6a256900edf!8m2!3d10.7855513!4d106.5907926?hl=vi-VN
                String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                        "?location=" + currentLat + "," + currentLong +
                        "&radius=5000" +
                        "&types=" + placeTypeList[i] +
                        "&sensor=true" +
                        "&key=" + getResources().getString(R.string.google_map_key);

                new PlaceTask().execute(url);
            }
        });

    }

    private void getCurrentLocation() {

        @SuppressLint("MissingPermission") Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    currentLat = location.getLatitude();

                    currentLong = location.getLongitude();

                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            map = googleMap;
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(currentLat,currentLong),10
                            ));
                        }
                    });
                }

            }
        });

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 44){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if(map!=null){
                    Toast.makeText(PostionActivity.this,"co",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(PostionActivity.this,"k",Toast.LENGTH_LONG).show();
                }
                getCurrentLocation();
            }
        }
    }

    private class PlaceTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            String data = null;
            try {
                data = downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            new ParserTask().execute(s);
        }
    }

    private String downloadUrl(String string) throws IOException {
        URL url = new URL(string);
        HttpURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.connect();
        InputStream stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();
        String line="";
        while ((line = reader.readLine()) != null){
            builder.append(line);
        }

        String data = builder.toString();

        reader.close();

        return data;
    }

    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>> {
        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            JsonParser jsonParser = new JsonParser();

            List<HashMap<String,String>> mapList = null;
            JSONObject object = null;

            try {
                object = new JSONObject(strings[0]);
                mapList = jsonParser.parseResult(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mapList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {

            for(int i =0;i<hashMaps.size(); i++){
                HashMap<String,String> hashMapList = hashMaps.get(i);
                double lat = Double.parseDouble(hashMapList.get("lat"));
                double lng = Double.parseDouble(hashMapList.get("lng"));
                String name = hashMapList.get("name");
                LatLng latLng = new LatLng(lat,lng);
                MarkerOptions options = new MarkerOptions();
                options.position(latLng);
                options.title(name);
                map.addMarker(options);

            }
        }
    }
}










