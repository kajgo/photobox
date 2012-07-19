package com.photobox.files;

import android.graphics.Bitmap;

import com.photobox.files.BitmapSize;

public interface BitmapLoader {
    Bitmap loadWithRes(int maxSize);
    BitmapSize getBitmapSize();
}
