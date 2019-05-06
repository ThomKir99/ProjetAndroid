package com.example.thomaskirouac_antoinedumasfortin_daveloignon.projetandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
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
        LinearLayout linearLayout = findViewById(R.id.linearLayoutPhoto);
      for(Photo photo:MainActivity.currentUser.getPhoto()){

          final ImageButton imageView = new ImageButton(this);
          imageView.setImageBitmap(photo.getThumbnail());
          int photoId = getNumberOfPhoto(linearLayout);

          imageView.setId(photoId);

          imageView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  showConfirmDialog(imageView);
              }
          });

          final TextView position = new EditText(this);
          position.setText("Photo's position : (" +photo.getLatitude() + " , " +photo.getLongitude() + " )");
          position.setEnabled(false);
          final EditText orientation = new EditText(this);
          orientation.setText("Photo's orientation : " + (photo.getOrientation()));
          orientation.setEnabled(false);
          linearLayout.addView(imageView);
          linearLayout.addView(position);
          linearLayout.addView(orientation);
        }
    }

    private int getNumberOfPhoto(LinearLayout linearLayout) {
    int id= 0;
    for(int i =0; i<linearLayout.getChildCount();i++){
        if(linearLayout.getChildAt(i).getClass() == ImageButton.class){
        id++;
        }
    }
    return id;

    }

    private void showConfirmDialog(final ImageButton imageView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Are you sure");
        builder.setMessage("Do you really want to open Google map at this photo position?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showOnMap(imageView);
                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showOnMap(ImageButton imageView) {
        ArrayList<Photo> photos = MainActivity.currentUser.getPhoto();
        Photo photo = photos.get(imageView.getId());
        String longitude = String.valueOf(photo.getLongitude());
        String latitude = String.valueOf(photo.getLatitude());

        Uri gmmIntentUri = Uri.parse("google.streetview:cbll="+latitude+","+longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

}
