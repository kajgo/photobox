package com.photobox.world;

import android.graphics.Bitmap;

public class Photo {

    public int BORDER = 10;
    public float centerX = 0;
    public float centerY = 0;
    public float width;
    public float height;
    public float angle;
    public Bitmap lowRes;
    public Bitmap highRes;

    public Photo withSize(float w, float h) {
        BORDER = (int)Math.round(0.02 * Math.max(w, h));
        this.width = w + BORDER * 2;
        this.height = h + BORDER * 2;
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

    public Bitmap getBitmap() {
        if (highRes != null) {
            return highRes;
        }
        return lowRes;
    }

}
