package com.example.thomaskirouac_antoinedumasfortin_daveloignon.projetandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initListener();
    }

    private void initListener() {
        ImageView imgFavorite = (ImageView) findViewById(R.id.imageView_futurama);
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOnMap();
            }
        });
    }

    private void showOnMap() {
        Toast.makeText(MainActivity.this,
                "The favorite list would appear on clicking this icon",
                Toast.LENGTH_LONG).show();
    }
}
