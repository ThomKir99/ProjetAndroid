package com.example.thomaskirouac_antoinedumasfortin_daveloignon.projetandroid;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;

public class Photo {

    private Bitmap photoBitmap;
    private Bitmap thumbnail;


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


}
