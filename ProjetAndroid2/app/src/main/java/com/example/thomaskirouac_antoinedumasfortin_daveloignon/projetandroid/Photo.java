package com.example.thomaskirouac_antoinedumasfortin_daveloignon.projetandroid;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;

public class Photo {

    private Bitmap photoBitmap;
    private Bitmap thumbnail;
    private double orientation;
    private double longitude;
    private double latitude;

    public Photo(Bitmap photoBitMap){
        this.photoBitmap = photoBitMap;
        thumbnail = ThumbnailUtils.extractThumbnail(photoBitMap, 500, 500);
    }

    public Bitmap getPhotoBitmap() {
        return photoBitmap;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public double getOrientation() {
        return orientation;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
