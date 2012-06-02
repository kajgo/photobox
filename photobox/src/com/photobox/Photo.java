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

    public boolean pointInside(Point p) {
        Point p1 = Rotator.rotatePoint(p, angle);
        Point p2 = Rotator.rotatePoint(new Point(centerX, centerY), angle);
        if (p1.x < p2.x - width / 2)
            return false;
        else if (p1.x > p2.x + width / 2)
            return false;
        else if (p1.y < p2.y - height / 2)
            return false;
        else if (p1.y > p2.y + height / 2)
            return false;
        else
            return true;
    }
}
