package com.example.thomaskirouac_antoinedumasfortin_daveloignon.projetandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

public class SeePicture extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_picture);
        addPhoto();
        setListener();
    }

    private void setListener(){
        Button btn_backToMenu = findViewById(R.id.btn_backToMenu);

        btn_backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMenu();
            }
        });
    }

    private void backToMenu(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void addPhoto() {
        ScrollView scrollView = findViewById(R.id.scrollViewPhoto);
        LinearLayout linearLayout = findViewById(R.id.linearLayoutPhoto);
      for(Photo photo:MainActivity.currentUser.getPhoto()){

          final ImageButton imageView = new ImageButton(this);
          imageView.setImageBitmap(photo.getThumbnail());
          imageView.setId(linearLayout.getChildCount());
          imageView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  toastMe(imageView);
              }
          });
          linearLayout.addView(imageView);


        }
    }

    private void toastMe(ImageButton imageView) {
        ArrayList<Photo> photos = MainActivity.currentUser.getPhoto();
        Photo photo = photos.get(imageView.getId());
        Toast.makeText(this, String.valueOf(photo.getOrientation()),Toast.LENGTH_SHORT).show();
        //showConfirmDialog();
    }

    private void showConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Are you sure");
        builder.setMessage("Do you really want to open Google map at this photo position?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showMessage("confirm");
                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showMessage("cancel");
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showMessage(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
