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

    public Bitmap loadWithRes(BitmapSize reqSize) {
        return loadWithRes(SampleSizeCalculator.calculate(getBitmapSize(), reqSize));
    }

    public BitmapSize getBitmapSize() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        return new BitmapSize(options.outWidth, options.outHeight);
    }

    private Bitmap loadWithRes(int res) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = res;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

}
