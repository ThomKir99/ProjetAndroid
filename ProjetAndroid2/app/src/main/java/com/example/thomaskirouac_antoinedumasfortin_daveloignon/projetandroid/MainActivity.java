package com.example.thomaskirouac_antoinedumasfortin_daveloignon.projetandroid;

import android.Manifest;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

    public static User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (currentUser == null)
        {
            currentUser = new User();
        }

        setContentView(R.layout.activity_main);

        initListener();
    }

    private void initListener() {
        initButton();
    }

    private void initButton(){
        final ImageButton btn_takePicture = findViewById(R.id.btn_takePicture);
        final ImageButton btn_picture = findViewById(R.id.btn_picture);

        btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picturePage();
            }
        });
        btn_takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicturePage();
            }
        });
    }

    private void takePicturePage(){
        Intent i = new Intent(this, TakePhoto.class);
        startActivity(i);
    }

    private void picturePage(){
        Intent i = new Intent(this, SeePicture.class);
        startActivity(i);
    }



}
