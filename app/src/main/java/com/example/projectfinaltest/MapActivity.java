package com.example.projectfinaltest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

//import com.example.refilltracker.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
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

public class MapActivity extends AppCompatActivity {

    private static final int REQUEST_ACCESS_FINE_LOCATION = 111;

    Spinner spType;
    Button btnFind;

    SupportMapFragment supportMapFragment;
    GoogleMap map;
    FusedLocationProviderClient fusedLocationProviderClient;

    double currentLat = 10.827737158510356;
    double currentLong = 106.45695835084776;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        spType = findViewById(R.id.sp_type);
        btnFind = findViewById(R.id.btn_find);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);

        String[] placeTypeList = {"gas_station", "parking", "atm", "bank", "hospital", "movie_theater", "restaurant"};
        String[] placeNameList = {"Trạm xăng", "Bãi giữ xe", "ATM", "Ngân hàng", "Bệnh viện", "Rạp chiếu phim", "Nhà hàng"};

        spType.setAdapter(new ArrayAdapter<>(MapActivity.this
                , android.R.layout.simple_spinner_dropdown_item, placeNameList));

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(MapActivity.this
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(MapActivity.this
                    , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = spType.getSelectedItemPosition();

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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MapActivity.this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    }, REQUEST_ACCESS_FINE_LOCATION);
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLat = location.getLatitude();
                    currentLong = location.getLongitude();

                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            map = googleMap;

                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(10.827737158510356, 106.45695835084776), 20
                            ));
                            if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED
                                    && ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_DENIED) {
                                ActivityCompat.requestPermissions(MapActivity.this,
                                        new String[]{
                                                Manifest.permission.ACCESS_FINE_LOCATION,
                                                Manifest.permission.ACCESS_COARSE_LOCATION
                                        }, REQUEST_ACCESS_FINE_LOCATION);
                            }
                            map.setMyLocationEnabled(true);
                            UiSettings uiSettings = googleMap.getUiSettings();
                            uiSettings.setZoomControlsEnabled(true);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }

    private class PlaceTask extends AsyncTask<String,Integer,String> {
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
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();

        InputStream stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        StringBuilder builder = new StringBuilder();

        String line = "";

        while((line = reader.readLine()) != null){
            builder.append(line);
        }

        String data = builder.toString();
        reader.close();

        return data;
    }

    private class ParserTask extends AsyncTask<String,Integer,List<HashMap<String,String>>> {
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
            map.clear();
            for(int i=0; i<hashMaps.size(); i++){
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