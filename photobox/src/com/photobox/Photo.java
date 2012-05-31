package com.photobox;

import android.graphics.Bitmap;

public class Photo {

    public int BORDER = 10;
    public float centerX;
    public float centerY;
    public float width;
    public float height;
    public float angle;
    public Bitmap image;

    public Photo(float width, float height, Bitmap anImage) {
        this.width = width;
        this.height = height;
        centerX = 0;
        centerY = 0;
        angle = 0;
        image = anImage;
    }

    public float getWidth() {
        return width + BORDER * 2;
    }

    public float getHeight() {
        return height + BORDER * 2;
    }

    public boolean pointInside(float x, float y) {
        if (x < centerX - width / 2)
            return false;
        else if (x > centerX + width / 2)
            return false;
        else if (y < centerY - height / 2)
            return false;
        else if (y > centerY + height / 2)
            return false;
        else
            return true;
    }
}
