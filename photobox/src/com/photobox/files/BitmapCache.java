package com.photobox.files;

import java.util.ArrayList;
import java.util.List;

import com.photobox.world.Photo;

import android.graphics.Bitmap;

public class BitmapCache {

    class Pair {
        public Photo photo;
        public BitmapLoader loader;
        public Pair(Photo p, BitmapLoader l) {
            photo = p;
            loader = l;
        }
    }

    private List<Pair> bitmaps;
    private Photo highResPhoto;
    private int maxSize;

    public BitmapCache(int maxSize) {
        bitmaps = new ArrayList<Pair>();
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
        bitmaps.add(new Pair(p, b));
        p.lowRes = b.loadWithRes((int)Math.round(maxSize * 0.25f));
    }

    private Bitmap getHighRes(Photo p) {
        for (Pair pair : bitmaps) {
            if (pair.photo == p) {
                return pair.loader.loadWithRes(maxSize);
            }
        }
        return null;
    }

}
