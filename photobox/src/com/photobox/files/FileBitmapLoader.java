package com.photobox.files;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.photobox.files.BitmapSize;

public class FileBitmapLoader implements BitmapLoader {

    private File file;

    public FileBitmapLoader(File file) {
        this.file = file;
    }

    public Bitmap loadWithRes(int maxSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = SampleSizeCalculator.calculate(getBitmapSize(), maxSize);
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    public BitmapSize getBitmapSize() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        return new BitmapSize(options.outWidth, options.outHeight);
    }

}
