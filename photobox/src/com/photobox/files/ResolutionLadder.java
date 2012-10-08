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

    public ResolutionLadder(int maxPhotosAllowed, float resolution, int maxSize, ResolutionLadder nextLevel) {
        this.resolution = resolution;
        this.maxSize = maxSize;
        this.nextLevel = nextLevel;
        photoQueue = new SizedQueue<Photo>(maxPhotosAllowed);
    }

    public void putOnTop(Photo p) {
        boolean fiddleWithBitmaps = !photoQueue.contains(p);
        remove(p, fiddleWithBitmaps);
        enqueue(p, fiddleWithBitmaps);
    }

    public void fillFrom(List<Photo> p) {
        int n = photoQueue.numFreeSlots();
        List<Photo> toActivate = popNLast(n, p);
        while(toActivate.size() > 0) {
            putOnTop(toActivate.remove(0));
        }
        if(nextLevel != null) {
            nextLevel.fillFrom(p);
        }
    }

    private void remove(Photo photo, boolean clearPhoto) {
        photoQueue.remove(photo);
        if (nextLevel != null) {
            nextLevel.remove(photo, clearPhoto);
        }
        if (clearPhoto) {
            photo.clearBitmap(resolution);
        }
    }

    private List<Photo> popNLast(int length, List<Photo> list) {
        List<Photo> subList = new ArrayList<Photo>();
        while(list.size() > 0 && subList.size() < length) {
            subList.add(list.remove(list.size()-1));
        }
        return subList;
    }

    private void enqueue(Photo photo, boolean fiddleWithBitmaps) {
        Photo dequeued = photoQueue.enqueue(photo);
        if (dequeued != null) {
            if (fiddleWithBitmaps) {
                dequeued.clearBitmap(resolution);
            }
            if (nextLevel != null) {
                nextLevel.enqueue(dequeued, fiddleWithBitmaps);
            }
        }
        if(fiddleWithBitmaps) {
            loadWithRes(resolution, photo);
        }
    }

    private void loadWithRes(float res, Photo p) {
        Bitmap b = p.bitmapLoader.loadWithRes((int)Math.round(maxSize * res));
        p.setBitmap(res, b);
    }

}
