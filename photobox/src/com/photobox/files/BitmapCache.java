package com.photobox.files;

import java.util.ArrayList;
import java.util.List;

import com.photobox.world.Photo;

import android.graphics.Bitmap;

public class BitmapCache {

    private Photo highResPhoto;
    private int maxSize;

    public BitmapCache(int maxSize) {
        this.maxSize = maxSize;
    }

    public void setHighRes(Photo p) {
        if (highResPhoto != null) {
            highResPhoto.clearBitmap(1);
        }
        highResPhoto = p;
        loadWithRes(1, p);
    }

    public void add(Photo p, BitmapLoader b) {
        p.bitmapLoader = b;
        loadWithRes(0.25f, p);
    }

    private void loadWithRes(float res, Photo p) {
        Bitmap b = p.bitmapLoader.loadWithRes((int)Math.round(maxSize * res));
        p.setBitmap(res, b);
    }

}
