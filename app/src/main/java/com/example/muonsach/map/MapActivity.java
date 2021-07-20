package com.example.muonsach.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.muonsach.R;
import com.example.muonsach.databinding.ActivityMaapBinding;
import com.example.muonsach.obj.GeoLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.here.sdk.core.GeoCoordinates;
import com.here.sdk.core.LanguageCode;
import com.here.sdk.core.Point2D;
import com.here.sdk.core.errors.InstantiationErrorException;
import com.here.sdk.mapview.MapImage;
import com.here.sdk.mapview.MapImageFactory;
import com.here.sdk.mapview.MapMarker;
import com.here.sdk.mapview.MapScheme;
import com.here.sdk.search.Place;
import com.here.sdk.search.SearchCallback;
import com.here.sdk.search.SearchEngine;
import com.here.sdk.search.SearchError;
import com.here.sdk.search.SearchOptions;
import com.here.sdk.search.TextQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity {
    ActivityMaapBinding binding;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final String TAG = MapActivity.class.getSimpleName();
    double longtitude = 0;
    double latitude = 0;
    List<GeoLocation> geoLocationList;
    private SearchEngine searchEngine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_maap);
        binding.mapView.onCreate(savedInstanceState);

        try {
            searchEngine = new SearchEngine();

        }catch (InstantiationErrorException exception){
            throw new RuntimeException("oh no master shifu");
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        loadMapScene();

        geoLocationList = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("GeoLocation").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                GeoLocation geoLocation = snapshot.getValue(GeoLocation.class);
                geoLocationList.add(geoLocation);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchButton();




    }

    private void searchButton(){
        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.searchText.getText().toString().equals("")){
                    return;
                }
                binding.clearText.setVisibility(View.VISIBLE);
                searchPlaces();
            }
        });
    }

    public void searchPlaces(){
        SearchOptions searchOptions = new SearchOptions(LanguageCode.VI_VN , 5);
        EditText editText = findViewById(R.id.search_text);
        TextQuery textQuery = new TextQuery(editText.getText().toString().trim(), getScreenCenter());

        searchEngine.search(textQuery, searchOptions, new SearchCallback() {
            @Override
            public void onSearchCompleted(@Nullable SearchError searchError, @Nullable List<Place> list) {
                for (Place result : list){

                    TextView textView = new TextView(getApplicationContext());
                    textView.setTextColor(android.graphics.Color.parseColor("#FFFFFF"));
                    textView.setText(result.getTitle() +"\n" + result.getAddress().addressText);

                    LinearLayout linearLayout = new LinearLayout(getApplicationContext());
                    linearLayout.setBackgroundResource(R.color.design_default_color_primary);
                    linearLayout.setPadding(10,10,10,10);
                    linearLayout.addView(textView);

                    binding.mapView.pinView(linearLayout,result.getGeoCoordinates());

                    binding.clearText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            binding.searchText.setText("");
                            binding.mapView.removeView(linearLayout);
                        }
                    });
                    binding.mapView.getCamera().lookAt(result.getGeoCoordinates());
                }
            }
        });
    }

    private GeoCoordinates getScreenCenter(){
        int screenWidthInPixels = binding.mapView.getWidth();
        int screenHeightInPixels = binding.mapView.getHeight();
        Point2D point2D = new Point2D(screenWidthInPixels * 0.5, screenHeightInPixels * 0.5);
        return binding.mapView.viewToGeoCoordinates(point2D);
    }
    private void loadMapScene(){
        getLocation();
        binding.mapView.getMapScene().loadScene(MapScheme.NORMAL_DAY, mapError -> {
            if (mapError == null){
                for (GeoLocation geoLocation : geoLocationList){
                    addMarker(geoLocation.getLatitude(),geoLocation.getLongtitude());
                }
                binding.mapView.getCamera().lookAt(new GeoCoordinates(latitude, longtitude),100000);
            }else {
                Log.d(TAG,"Loading map failed: mapError: " + mapError.name());
            }
        });
    }
    public void addMarker(double lat, double longtitude){
        MapImage mapImage = MapImageFactory.fromResource(this.getResources(),R.drawable.location);
        MapMarker mapMarker = new MapMarker(new GeoCoordinates(lat,longtitude),mapImage);
        binding.mapView.getMapScene().addMapMarker(mapMarker);
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null){

                    try {
                        Geocoder geocoder = new Geocoder(MapActivity.this, Locale.getDefault());
                        List<Address> list = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

                        latitude = list.get(0).getLatitude();
                        longtitude = list.get(0).getLongitude();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.mapView.onDestroy();
    }
}