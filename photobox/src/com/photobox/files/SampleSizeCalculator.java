package com.photobox.files;

import com.photobox.files.BitmapSize;

public class SampleSizeCalculator {

    public static int calculate(BitmapSize bitmapSize, BitmapSize reqSize) {

        int boxSize = Math.max(reqSize.width, reqSize.height);

        if (bitmapSize.width > bitmapSize.height) {
            return Math.max(1, Math.round((float)bitmapSize.width/(float)boxSize));
        } else {
            return Math.max(1, Math.round((float)bitmapSize.height/(float)boxSize));
        }


//        final int width = bitmapSize.width;
//        final int height = bitmapSize.height;
//        int inSampleSize = 1;
//
//        if (height > reqSize.height || width > reqSize.width) {
//            if (width > height) {
//                inSampleSize = Math.round((float)height / (float)reqSize.height);
//            } else {
//                inSampleSize = Math.round((float)width / (float)reqSize.width);
//            }
//        }
//        return inSampleSize;
    }

}
