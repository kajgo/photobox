package com.photobox.files;

import java.util.ArrayList;
import java.util.List;

import com.photobox.world.Photo;

import android.graphics.Bitmap;

class BitmapQueue {

    private int maxPhotosAllowed;
    private float resolution;
    private int maxSize;
    private BitmapQueue nextQueue;
    private List<Photo> photos;

    public BitmapQueue(int maxPhotosAllowed, float resolution, int maxSize, BitmapQueue nextQueue) {
        this.maxPhotosAllowed = maxPhotosAllowed;
        this.resolution = resolution;
        this.nextQueue = nextQueue;
        this.maxSize = maxSize;
        photos = new ArrayList<Photo>();
    }

    public void fillQueue(List<Photo> p) {
        int n = maxPhotosAllowed - photos.size();
        List<Photo> toActivate = popNLast(n, p);
        while(toActivate.size() > 0) {
            activate(toActivate.remove(0));
        }
        if(nextQueue != null) {
            nextQueue.fillQueue(p);
        }
    }

    private List<Photo> popNLast(int length, List<Photo> list) {
        List<Photo> subList = new ArrayList<Photo>();
        while(list.size() > 0 && subList.size() < length) {
            subList.add(list.remove(list.size()-1));
        }
        return subList;
    }

    public void activate(Photo photo) {
        remove(photo);
        dequeue();
        enqueue(photo);
    }

    public void remove(Photo photo) {
        if (photos.contains(photo)) {
            photos.remove(photo);
        }
        if (nextQueue != null) {
            nextQueue.remove(photo);
        }
    }

    private void dequeue() {
        if (photos.size() == maxPhotosAllowed) {
            Photo last = photos.remove(photos.size() - 1);
            last.clearBitmap(resolution);
            if (nextQueue != null) {
                nextQueue.activate(last);
            }
        }
    }

    private void enqueue(Photo photo) {
        loadWithRes(resolution, photo);
        photos.add(0, photo);
    }

    private void loadWithRes(float res, Photo p) {
        Bitmap b = p.bitmapLoader.loadWithRes((int)Math.round(maxSize * res));
        p.setBitmap(res, b);
    }

}
