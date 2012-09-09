package com.photobox.files;

import java.util.*;

import com.photobox.world.Photo;

public class BitmapCache {

    private BitmapQueue queue;
    private List<Photo> photos;

    public BitmapCache(int maxSize) {
        queue = new BitmapQueue(1, 1, maxSize,
                new BitmapQueue(1, 0.5f, maxSize,
                    new BitmapQueue(1, 0.25f, maxSize, null)));
        photos = new ArrayList<Photo>();
    }

    public void setHighRes(Photo p) {
        queue.activate(p);
    }

    public void add(Photo p, BitmapLoader b) {
        p.bitmapLoader = b;
        photos.add(p);
    }

    public void loadAllBitmaps() {
        queue.fillQueue(photos);
    }

}
