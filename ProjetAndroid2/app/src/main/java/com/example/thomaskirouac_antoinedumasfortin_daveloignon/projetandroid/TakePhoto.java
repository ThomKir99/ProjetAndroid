package com.example.thomaskirouac_antoinedumasfortin_daveloignon.projetandroid;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TakePhoto extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagnetometer;
    private float[] mLastAccelerometer = new float[3];
    private float[] mLastMagnetometer = new float[3];
    private boolean mLastAccelerometerSet = false;
    private boolean mLastMagnetometerSet = false;
    private float[] mR = new float[9];
    private float[] mOrientation = new float[3];
    private float mCurrentDegree = 0f;

    String currentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private float currentDegree = 0f;
    private static SensorManager mySensorManager;
    TextView orrientationZ;
    private boolean sersorrunning;
    private double orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        initListener();
    }

    private void initListener() {
        findViewById(R.id.btn_takePicture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        findViewById(R.id.btn_seePicture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSeePhotoView();
            }
        });

        findViewById(R.id.btn_backToMenu2).setOnClickListener(new View.OnClickListener() {
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

    private void startSeePhotoView() {
        Intent i = new Intent(this, SeePicture.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        setPic();
    }



    private SensorEventListener mySensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            orientation = Math.toDegrees(event.values[0]);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(sersorrunning){
            mySensorManager.unregisterListener(mySensorEventListener);
            Toast.makeText(this, "unregisterListener", Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );


        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.thomaskirouac_antoinedumasfortin_daveloignon.projetandroid",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void dispatchTakePictureIntentSimple() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void setPic() {
        ImageView imageView = findViewById(R.id.imageView_photoTaken);
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        Photo photo = new Photo(bitmap);
        photo.setOrientation(mCurrentDegree);
        Localisation localisation = new Localisation(this,photo, this);
        MainActivity.currentUser.addPhoto(photo);
        imageView.setImageBitmap(bitmap);
        setPictureAnimation(imageView);

    }

    public void setPictureAnimation(ImageView imageView) {
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.my_faid_in_animation);
        imageView.startAnimation(fadeInAnimation);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_GAME);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this, mAccelerometer);
        mSensorManager.unregisterListener(this, mMagnetometer);
    }

    public void getOrientation(){

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == mAccelerometer) {
            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
            mLastAccelerometerSet = true;
        } else if (event.sensor == mMagnetometer) {
            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
            mLastMagnetometerSet = true;
        }
        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
            SensorManager.getOrientation(mR, mOrientation);
            float azimuthInRadians = mOrientation[0];
            float azimuthInDegress = (float)(Math.toDegrees(azimuthInRadians)+360)%360;
            mCurrentDegree = -azimuthInDegress;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
