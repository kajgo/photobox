package com.photobox.files;

import com.photobox.files.BitmapSize;

public class SampleSizeCalculator {

    public static int calculate(BitmapSize bitmapSize, BitmapSize reqSize) {
        final int width = bitmapSize.width;
        final int height = bitmapSize.height;
        int inSampleSize = 1;

        if (height > reqSize.height || width > reqSize.width) {
            if (width > height) {
                inSampleSize = Math.round((float)height / (float)reqSize.height);
            } else {
                inSampleSize = Math.round((float)width / (float)reqSize.width);
            }
        }
        return inSampleSize;
    }

}
