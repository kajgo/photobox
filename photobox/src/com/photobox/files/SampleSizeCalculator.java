package com.photobox.files;

import com.photobox.files.BitmapSize;

public class SampleSizeCalculator {

    public static int calculate(BitmapSize bitmapSize, int maxSize) {
        if (bitmapSize.width > bitmapSize.height) {
            return Math.max(1, Math.round((float)bitmapSize.width/(float)maxSize));
        } else {
            return Math.max(1, Math.round((float)bitmapSize.height/(float)maxSize));
        }
    }

}
