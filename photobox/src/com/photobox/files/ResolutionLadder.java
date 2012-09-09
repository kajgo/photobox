package com.photobox.files;

import java.util.ArrayList;
import java.util.List;

import com.photobox.world.Photo;

import android.graphics.Bitmap;

public class ResolutionLadder {

    private float resolution;
    private int maxSize;
    private ResolutionLadder nextLevel;
    private SizedQueue<Photo> photoQueue;
    private List<Photo> photos = new ArrayList<Photo>();

    public ResolutionLadder(int maxPhotosAllowed, float resolution, int maxSize, ResolutionLadder nextLevel) {
        this.resolution = resolution;
        this.nextLevel = nextLevel;
        this.maxSize = maxSize;
        photoQueue = new SizedQueue<Photo>(maxPhotosAllowed);
    }

    public void setHighRes(Photo p) {
        activate(p);
    }

    public void add(Photo p, BitmapLoader b) {
        p.bitmapLoader = b;
        photos.add(p);
    }

    public void loadAllBitmaps() {
        fillQueue(photos);
    }

    public void fillQueue(List<Photo> p) {
        int n = photoQueue.numFreeSlots();
        List<Photo> toActivate = popNLast(n, p);
        while(toActivate.size() > 0) {
            activate(toActivate.remove(0));
        }
        if(nextLevel != null) {
            nextLevel.fillQueue(p);
        }
    }

    public void activate(Photo photo) {
        remove(photo);
        enqueue(photo);
    }

    public void remove(Photo photo) {
        photoQueue.remove(photo);
        if (nextLevel != null) {
            nextLevel.remove(photo);
        }
    }

    private List<Photo> popNLast(int length, List<Photo> list) {
        List<Photo> subList = new ArrayList<Photo>();
        while(list.size() > 0 && subList.size() < length) {
            subList.add(list.remove(list.size()-1));
        }
        return subList;
    }

    private void enqueue(Photo photo) {
        Photo dequeued = photoQueue.enqueue(photo);
        if (dequeued != null) {
            dequeued.clearBitmap(resolution);
            if (nextLevel != null) {
                nextLevel.enqueue(dequeued);
            }
        }
        loadWithRes(resolution, photo);
    }

    private void loadWithRes(float res, Photo p) {
        Bitmap b = p.bitmapLoader.loadWithRes((int)Math.round(maxSize * res));
        p.setBitmap(res, b);
    }

}
