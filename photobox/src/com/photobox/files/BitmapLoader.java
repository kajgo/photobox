package com.photobox.files;

import android.graphics.Bitmap;

public interface BitmapLoader {
    public Bitmap loadLowRes();
    public Bitmap loadHighRes();
}
