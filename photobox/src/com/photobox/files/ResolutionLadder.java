package com.photobox.files;

import java.util.List;

import com.photobox.files.*;
import com.photobox.queues.*;
import com.photobox.world.Photo;

import android.graphics.Bitmap;

public class ResolutionLadder {

    private float resolution;
    private AsynchPhotoLoader asynchPhotoLoader;
    private ResolutionLadder nextLevel;
    private SizedQueue<Photo> photoQueue;

    public ResolutionLadder(int maxPhotosAllowed, float resolution, AsynchPhotoLoader asynchPhotoLoader, ResolutionLadder nextLevel) {
        this.resolution = resolution;
        this.asynchPhotoLoader = asynchPhotoLoader;
        this.nextLevel = nextLevel;
        photoQueue = new SizedQueue<Photo>(maxPhotosAllowed);
    }

    public void putOnTop(Photo p) {
        boolean fiddleWithBitmaps = !photoQueue.contains(p);
        remove(p, fiddleWithBitmaps);
        enqueue(p, fiddleWithBitmaps);
    }

    public void fillFrom(MultiplePopQueue<Photo> p) {
        int n = photoQueue.numFreeSlots();
        List<Photo> toActivate = p.popN(n);
        while (toActivate.size() > 0) {
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
        asynchPhotoLoader.addLoadingTask(p, res);
    }

}
