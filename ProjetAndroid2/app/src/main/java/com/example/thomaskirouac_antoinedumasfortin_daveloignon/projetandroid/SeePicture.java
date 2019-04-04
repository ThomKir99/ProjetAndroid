package com.example.thomaskirouac_antoinedumasfortin_daveloignon.projetandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class SeePicture extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_picture);
        addPhoto();
    }

    private void addPhoto() {
        ScrollView scrollView = findViewById(R.id.scrollViewPhoto);
        LinearLayout linearLayout = findViewById(R.id.linearLayoutPhoto);
      for(Photo photo:MainActivity.currentUser.getPhoto()){

          final ImageButton imageView = new ImageButton(this);
          imageView.setImageBitmap(photo.getThumbnail());
          imageView.setId(R.id.button +linearLayout.getChildCount() );


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
        Toast.makeText(this,String.valueOf(imageView.getId()),Toast.LENGTH_SHORT).show();

    }
}
