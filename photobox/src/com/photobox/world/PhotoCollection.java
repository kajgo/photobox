package com.photobox.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public void throwPhotos(int throwBoxSize) {
        final int angleDiff = 45;
        Random rnd = new Random();
        for (Photo p : photos) {
            float x = (float)rnd.nextInt(throwBoxSize)-throwBoxSize/2;
            float y = (float)rnd.nextInt(throwBoxSize)-throwBoxSize/2;
            float angle = (float)rnd.nextInt(angleDiff)-angleDiff/2;
            p.centerX = x;
            p.centerY = y;
            p.angle = angle;
        }
    }

    public void fingerDown(Point p) {
        active = findPhotoAt(p);
        if (active != null) {
            moveElementToEndOfList(active);
        }
    }

    private Photo findPhotoAt(Point p) {
        for (int i = photos.size() - 1; i >= 0; i--) {
            Photo photo = photos.get(i);
            if (photo.pointInside(p)) {
                return photo;
            }
        }
        return null;
    }

    private void moveElementToEndOfList(Photo element) {
        photos.remove(element);
        photos.add(element);
    }

}
