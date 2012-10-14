package com.photobox.files;

import java.util.HashMap;

import com.photobox.queues.*;
import com.photobox.world.Photo;

import android.os.AsyncTask;
import android.graphics.Bitmap;
import android.view.View;

public class AsyncPhotoLoader {

    private HashMap<Photo, PhotoLoaderAsyncTask> loadingTasks = new HashMap<Photo, PhotoLoaderAsyncTask>();
    private View view;
    private int maxSize;

    public AsyncPhotoLoader(View view, int maxSize) {
        this.view = view;
        this.maxSize = maxSize;
    }

    public void addLoadingTask(Photo photo, float resolution) {
        if (loadingTasks.containsKey(photo)) {
            loadingTasks.get(photo).cancel(true);
        }
        PhotoLoaderAsyncTask task = new PhotoLoaderAsyncTask(photo, resolution, view);
        loadingTasks.put(photo, task);
        task.execute();
    }

    class PhotoLoaderAsyncTask extends AsyncTask<Void, Void, Bitmap> {

        private Photo photo;
        private float resolution;
        private View view;

        public PhotoLoaderAsyncTask(Photo photo, float resolution, View view) {
            this.photo = photo;
            this.resolution = resolution;
            this.view = view;
        }

        protected Bitmap doInBackground(Void... voids) {
            int calcultedRes = (int)Math.round(maxSize * resolution);
            return photo.bitmapLoader.loadWithRes(calcultedRes);
        }

        protected void onPostExecute(Bitmap bitmap) {
            photo.clearAllBitmaps();
            photo.setBitmap(resolution, bitmap);
            view.invalidate();
        }
    }

}
