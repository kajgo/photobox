package com.photobox.files;

import java.util.ArrayList;
import java.util.List;

import com.photobox.world.Photo;

import android.graphics.Bitmap;

class BitmapQueue {

    private float resolution;
    private int maxSize;
    private BitmapQueue nextQueue;
    private SizedQueue<Photo> photoQueue;

    public BitmapQueue(int maxPhotosAllowed, float resolution, int maxSize, BitmapQueue nextQueue) {
        this.resolution = resolution;
        this.nextQueue = nextQueue;
        this.maxSize = maxSize;
        photoQueue = new SizedQueue<Photo>(maxPhotosAllowed);
    }

    public void fillQueue(List<Photo> p) {
        int n = photoQueue.numFreeSlots();
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
        enqueue(photo);
    }

    public void remove(Photo photo) {
        photoQueue.remove(photo);
        if (nextQueue != null) {
            nextQueue.remove(photo);
        }
    }

    private void enqueue(Photo photo) {
        Photo dequeued = photoQueue.enqueue(photo);
        if (dequeued != null) {
            dequeued.clearBitmap(resolution);
            if (nextQueue != null) {
                nextQueue.enqueue(dequeued);
            }
        }
        loadWithRes(resolution, photo);
    }

    private void loadWithRes(float res, Photo p) {
        Bitmap b = p.bitmapLoader.loadWithRes((int)Math.round(maxSize * res));
        p.setBitmap(res, b);
    }

}
