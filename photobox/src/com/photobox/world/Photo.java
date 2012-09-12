package com.photobox.world;

import java.util.*;

import android.graphics.Bitmap;
import android.graphics.RectF;

import com.photobox.files.BitmapLoader;

public class Photo {

    public int BORDER = 10;
    public float centerX = 0;
    public float centerY = 0;
    public float width;
    public float height;
    public float angle;
    public BitmapLoader bitmapLoader;
    private Map<Float, Bitmap> bitmaps = new HashMap<Float, Bitmap>();

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

    public void setBitmap(float resolution, Bitmap bitmap) {
        if (!bitmaps.containsKey(resolution)) {
            bitmaps.put(resolution, bitmap);
        }
    }

    public void clearBitmap(float resolution) {
        if (bitmaps.containsKey(resolution)) {
            bitmaps.get(resolution).recycle();
            bitmaps.remove(resolution);
        }
    }

    public Bitmap getBitmap() {
        if (bitmaps.size() == 0) {
            return null;
        }
        return bitmaps.get(maxKey());
    }

    public boolean hasBitmap() {
        return getBitmap() != null;
    }

    public RectF bitmapRect() {
        return new RectF(
            -width / 2 + BORDER,
            -height / 2 + BORDER,
             width / 2 - BORDER,
             height / 2 - BORDER);
    }

    public RectF photoRect() {
        return new RectF(
            -width / 2,
            -height / 2,
             width / 2,
             height / 2);
    }

    private float maxKey() {
        float max = 0;
        for (float key : bitmaps.keySet()) {
            if (key > max) {
                max = key;
            }
        }
        return max;
    }

}
