package com.photobox;

import android.graphics.Bitmap;
import android.graphics.Matrix;

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
        Matrix m = new Matrix();
        m.setRotate(-angle, 0, 0);

        float[] dst = new float[] { 0, 0, 0, 0 };
        float[] src = new float[] { x, y, centerX, centerY };
        m.mapPoints(dst, src);
        float x1 = dst[0];
        float y1 = dst[1];
        float x2 = dst[2];
        float y2 = dst[3];

        if (x1 < x2 - width / 2)
            return false;
        else if (x1 > x2 + width / 2)
            return false;
        else if (y1 < y2 - height / 2)
            return false;
        else if (y1 > y2 + height / 2)
            return false;
        else
            return true;
    }
}
