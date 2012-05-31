package com.photobox;

import android.graphics.Bitmap;

public class Photo {

    public int BORDER = 10;
    public float centerX;
    public float centerY;
    public float angle;
    public Bitmap image;

    public Photo(Bitmap anImage) {
        centerX = 0;
        centerY = 0;
        angle = 0;
        image = anImage;
    }

    public float getWidth() {
        return image.getWidth() + BORDER * 2;
    }

    public float getHeight() {
        return image.getHeight() + BORDER * 2;
    }
}
