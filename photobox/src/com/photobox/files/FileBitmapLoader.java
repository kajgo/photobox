package com.photobox.files;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class FileBitmapLoader implements BitmapLoader {

    private File file;

    public FileBitmapLoader(File file) {
        this.file = file;
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
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    public BitmapSize getSize() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        return new BitmapSize(options.outWidth, options.outHeight);
    }
}

