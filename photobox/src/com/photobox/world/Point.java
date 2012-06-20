package com.photobox.world;

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

    @Override
    public boolean equals(Object other) {
        return (other instanceof Point) && ((Point)other).x == this.x && ((Point)other).y == this.y;
    }

    @Override
    public String toString() {
        return "Point<" + x + ", " + y + ">";
    }

}
