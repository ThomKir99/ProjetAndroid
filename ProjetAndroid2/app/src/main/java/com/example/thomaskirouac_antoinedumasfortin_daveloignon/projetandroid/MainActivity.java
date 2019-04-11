package com.example.thomaskirouac_antoinedumasfortin_daveloignon.projetandroid;

import android.Manifest;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import android.support.v4.app.ActivityCompat;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import com.google.android.gms.tasks.*;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initListener();
        //Intent i = new Intent(this, TakePhoto.class);
        //startActivity(i);

    }

    private void initListener() {

        initButton();

    }

    private void initButton(){
        final Button btn_coord = findViewById(R.id.btn_coord);

        btn_coord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCurrentCoord();
            }
        });
    }

    private void scrollUp() {
        ScrollView scrollView = (ScrollView)  findViewById(R.id.scrollView);
        LinearLayout linearLayout = (LinearLayout)  findViewById(R.id.linearLayout);

        linearLayout.animate().translationY(linearLayout.getHeight());
    }

    private void showCurrentCoord(){
        FusedLocationProviderClient fusedLocationProviderClient;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    2);
        }
        else{
            try
                {
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(100);
                    locationRequest.setFastestInterval(0);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                    LocationCallback locationCallback = new LocationCallback(){
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            super.onLocationResult(locationResult);

                            if(locationResult != null){
                                Toast.makeText(MainActivity.this, "Latitude: "+ locationResult.getLastLocation().getLatitude() + "\nLongitude: " + locationResult.getLastLocation().getLongitude(), Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(MainActivity.this, "You have no location", Toast.LENGTH_SHORT).show();
                            }
                        }
                    };

                    if (fusedLocationProviderClient.getLastLocation() == null){
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null);
                    }else {
                        Task<Location> location = fusedLocationProviderClient.getLastLocation();
                        location.addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location currentlocations) {
                                if(currentlocations != null){
                                    Toast.makeText(MainActivity.this, "Latitude: "+ currentlocations.getLatitude() + "\nLongitude: " + currentlocations.getLongitude(), Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "You have no location", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }




            }
            catch (SecurityException e)
            {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }

}
