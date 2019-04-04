package com.example.thomaskirouac_antoinedumasfortin_daveloignon.projetandroid;

import java.util.ArrayList;

public class User {

    private ArrayList<Photo> photos = new ArrayList<>();

    public User(){

    }

    public void addPhoto(Photo photo){
        photos.add(photo);
    }

    public ArrayList<Photo> getPhoto(){
        return photos;
    }
}
