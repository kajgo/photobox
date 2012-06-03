package com.photobox;

public class Point {

    public final float x;
    public final float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point minus(Point point) {
        return new Point(x - point.x, y - point.y);
    }

}
