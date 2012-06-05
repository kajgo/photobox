package com.photobox.world;

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

    public void fingerDown(Point p) {
        active = findPhotoAt(p);
    }

    private Photo findPhotoAt(Point p) {
        for (Photo photo : photos) {
            if (photo.pointInside(p)) {
                return photo;
            }
        }
        return null;
    }

}
