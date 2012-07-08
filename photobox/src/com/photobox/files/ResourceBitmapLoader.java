package com.photobox.files;

import android.content.res.Resources;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ResourceBitmapLoader implements BitmapLoader {

    private Resources ressources;
    private int which;

    public ResourceBitmapLoader(Resources ressources, int which) {
        this.ressources = ressources;
        this.which = which;
    }

    public Bitmap loadLowRes() {
        return loadWithRes(16);
    }

    public Bitmap loadHighRes() {
        return loadWithRes(1);
    }

    private Bitmap loadWithRes(int res) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = res;
        return BitmapFactory.decodeResource(ressources, which, options);
    }

    public BitmapSize getSize() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(ressources, which, options);
        return new BitmapSize(options.outWidth, options.outHeight);
    }

}

