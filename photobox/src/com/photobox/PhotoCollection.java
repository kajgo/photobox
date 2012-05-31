package com.photobox;

import java.util.ArrayList;
import java.util.List;

public class PhotoCollection {

    private ArrayList<Photo> photos = new ArrayList<Photo>();
    private Photo active = null;

    public void addPhoto(Photo p) {
        photos.add(p);
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public Photo getActive() {
        return active;
    }

    public void fingerDown(float x, float y) {
        active = findPhotoAt(x, y);
    }

    private Photo findPhotoAt(float x, float y) {
        for (Photo photo : photos) {
            if (photo.pointInside(x, y)) {
                return photo;
            }
        }
        return null;
    }

}
