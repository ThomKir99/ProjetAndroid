package com.example.thomaskirouac_antoinedumasfortin_daveloignon.projetandroid;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class Localisation {
    private Activity activity;
    private Context context;
    private Photo photo;
    private double latitude;
    private double longitude;
    public Localisation(Context context , Photo photo, Activity activity){

        this.context = context;
        this.photo = photo;
        this.activity = activity;
        saveLocalistion();
    }
    public void saveLocalistion(){
        FusedLocationProviderClient fusedLocationProviderClient;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
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
                            photo.setLatitude(locationResult.getLastLocation().getLatitude());
                            photo.setLongitude(locationResult.getLastLocation().getLongitude());
                        }
                        else{
                            Toast.makeText(context, "You have no location", Toast.LENGTH_SHORT).show();
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

                                photo.setLatitude(currentlocations.getLatitude());
                                photo.setLongitude(currentlocations.getLongitude());
                            }
                            else{
                                Toast.makeText(context, "You have no location", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
            catch (SecurityException e)
            {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }

}
