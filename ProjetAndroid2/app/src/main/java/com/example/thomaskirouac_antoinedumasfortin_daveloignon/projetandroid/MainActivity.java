package com.example.thomaskirouac_antoinedumasfortin_daveloignon.projetandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = new User();
        setContentView(R.layout.activity_main);
      //  initListener();
        Intent i = new Intent(this, TakePhoto.class);
        startActivity(i);
    }
}
