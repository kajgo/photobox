package com.photobox.files;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import com.photobox.queues.*;
import com.photobox.world.Photo;

import android.os.AsyncTask;
import android.graphics.Bitmap;
import android.view.View;

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
        Bitmap b = p.bitmapLoader.loadWithRes((int)Math.round(maxSize * res));
        p.setBitmap(res, b);
    }

    class AsynchPhotoLoader {
        private HashMap<Photo, PhotoLoaderAsynchTask> loadingTasks = new HashMap<Photo, PhotoLoaderAsynchTask>();
        private View view;

        public AsynchPhotoLoader(View view) {
            this.view = view;
        }

        public void addLoadingTask(Photo photo, float resolution, int maxSize) {
            if (loadingTasks.containsKey(photo)) {
                loadingTasks.get(photo).cancel(true);
            }
            PhotoLoaderAsynchTask task = new PhotoLoaderAsynchTask(photo, resolution, maxSize, view);
            loadingTasks.put(photo, task);
            task.execute();
        }
    }

    class PhotoLoaderAsynchTask extends AsyncTask<Void, Void, Bitmap> {

        private Photo photo;
        private float resolution;
        private int maxSize;
        private View view;

        public PhotoLoaderAsynchTask(Photo photo, float resolution, int maxSize, View view) {
            this.photo = photo;
            this.resolution = resolution;
            this.maxSize = maxSize;
            this.view = view;
        }

        protected Bitmap doInBackground(Void... voids) {
            int calcultedRes = (int)Math.round(maxSize * resolution);
            return photo.bitmapLoader.loadWithRes(calcultedRes);
        }

        protected void onPostExecute(Bitmap bitmap) {
            photo.setBitmap(resolution, bitmap);
            view.invalidate();
        }
    }
}
