package com.photobox.files;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.photobox.files.BitmapSize;

public class ResourceBitmapLoader implements BitmapLoader {

    private Resources resources;
    private int which;

    public ResourceBitmapLoader(Resources resources, int which) {
        this.resources = resources;
        this.which = which;
    }

    public Bitmap loadWithRes(BitmapSize reqSize) {
        return loadWithRes(SampleSizeCalculator.calculate(getBitmapSize(), reqSize));
    }

    public BitmapSize getBitmapSize() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, which, options);
        return new BitmapSize(options.outWidth, options.outHeight);
    }

    private Bitmap loadWithRes(int res) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = res;
        return BitmapFactory.decodeResource(resources, which, options);
    }

}

