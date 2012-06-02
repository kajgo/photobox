package com.photobox;

import android.graphics.Bitmap;

public class Photo {

    public int BORDER = 10;
    public float centerX = 0;
    public float centerY = 0;
    public float width;
    public float height;
    public float angle;
    public Bitmap image;

    public Photo withSize(float w, float h) {
        this.width = w;
        this.height = h;
        return this;
    }

    public Photo withAngle(float angle) {
        this.angle = angle;
        return this;
    }

    public Photo withCenterAt(float x, float y) {
        this.centerX = x;
        this.centerY = y;
        return this;
    }
    
    public Photo withBitmap(Bitmap bitmap) {
        this.image = bitmap;
        this.width = bitmap.getWidth() + BORDER * 2;
        this.height = bitmap.getHeight() + BORDER * 2;
        return this;
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
