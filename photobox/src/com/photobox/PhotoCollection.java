package com.photobox;

import java.util.ArrayList;
import java.util.List;

public class PhotoCollection {

    ArrayList<Photo> photos = new ArrayList<Photo>();

    public void addPhoto(Photo p) {
        photos.add(p);
    }

    public List<Photo> getPhotos() {
        return photos;
    }

}
