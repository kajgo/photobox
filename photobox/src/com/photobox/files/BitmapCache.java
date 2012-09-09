package com.photobox.files;

import java.util.*;

import com.photobox.world.Photo;

public class BitmapCache {

    private BitmapQueue queue;
    private List<Photo> photos;

    public BitmapCache(int maxSize) {
        queue = new BitmapQueue(2, 1, maxSize,
                new BitmapQueue(5, 0.5f, maxSize,
                    new BitmapQueue(20, 0.25f, maxSize, null)));
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
        for (Photo p : photos) {
            queue.activate(p);
        }
    }

}
