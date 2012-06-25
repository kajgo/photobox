package com.photobox.world;

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
        centerX = x;
        centerY = y;
        return this;
    }

    public Photo withBitmap(Bitmap bitmap) {
        image = bitmap;
        width = bitmap.getWidth() + BORDER * 2;
        height = bitmap.getHeight() + BORDER * 2;
        return this;
    }

    public boolean pointInside(Point p) {
        Point p1 = p.rotate(angle);
        Point p2 = new Point(centerX, centerY).rotate(angle);
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

    public void setCenterPoint(Point p) {
        centerX = p.x;
        centerY = p.y;
    }

    public Point getCenterPoint() {
        return new Point(centerX, centerY);
    }

}
