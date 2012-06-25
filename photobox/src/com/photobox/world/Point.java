package com.photobox.world;

import android.graphics.Matrix;

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

    public Point plus(Point point) {
        return new Point(x + point.x, y + point.y);
    }

    public Point halfWayTo(Point point) {
        return new Point((x + point.x) / 2, (y + point.y) / 2);
    }

    public Point rotate(float angle) {
        Matrix m = new Matrix();
        m.setRotate(-angle, 0, 0);
        return translate(m);
    }

    public Point translate(Matrix m) {
        float[] dst = new float[] { 0, 0 };
        float[] src = new float[] { x, y };
        m.mapPoints(dst, src);
        float newX = dst[0];
        float newY = dst[1];
        return new Point(newX, newY);
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof Point) &&
            ((Point)other).x == this.x && ((Point)other).y == this.y;
    }

    @Override
    public String toString() {
        return "Point<" + x + ", " + y + ">";
    }

}
