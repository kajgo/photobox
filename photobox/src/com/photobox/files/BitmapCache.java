package com.photobox.files;

import java.util.ArrayList;
import java.util.List;

import com.photobox.world.Photo;

import android.graphics.Bitmap;

public class BitmapCache {

    class Pair {
        public Photo photo;
        public Bitmap bitmap;
        public BitmapLoader loader;
        public Pair(Photo p, Bitmap b, BitmapLoader l) {
            photo = p;
            bitmap = b;
            loader = l;
        }
    }

    private List<Pair> bitmaps;
    private Photo highResPhoto;
    private Bitmap highResBitmap;

    public BitmapCache() {
        bitmaps = new ArrayList<Pair>();
    }

    public void setHighRes(Photo p) {
        if (highResPhoto != p) {
            highResPhoto = p;
            highResBitmap = getHighRes(p);
        }
    }

    public void add(Photo p, BitmapLoader b) {
        bitmaps.add(new Pair(p, b.loadLowRes(), b));
    }

    public Bitmap getHighRes(Photo p) {
        for (Pair pair : bitmaps) {
            if (pair.photo == p) {
                return pair.loader.loadHighRes();
            }
        }
        return null;
    }

    public Bitmap get(Photo p) {
        if (p == highResPhoto && highResBitmap != null) {
            return highResBitmap;
        }
        for (Pair pair : bitmaps) {
            if (pair.photo == p) {
                return pair.bitmap;
            }
        }
        return null;
    }

}
