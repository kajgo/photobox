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
            highResPhoto.highRes.recycle();
            highResPhoto.highRes = null;
        }
        highResPhoto = p;
        p.highRes = getHighRes(p);
    }

    public void add(Photo p, BitmapLoader b) {
        p.bitmapLoader = b;
        p.lowRes = b.loadWithRes((int)Math.round(maxSize * 0.25f));
    }

    private Bitmap getHighRes(Photo p) {
        return p.bitmapLoader.loadWithRes(maxSize);
    }

}
