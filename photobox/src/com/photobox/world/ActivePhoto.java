package com.photobox.world;

public class ActivePhoto {

    public Photo photo;
    private Point fingerOffset;

    public ActivePhoto(Photo photo, Point fingerOffset) {
        this.photo = photo;
        this.fingerOffset = fingerOffset;
    }

    public static ActivePhoto fromFingerPoint(Point fingerPoint, Photo photo) {
        Point offset = fingerPoint.minus(photo.getCenterPoint());
        return new ActivePhoto(photo, offset);
    }

    public void move(Point fingerAt) {
        photo.setCenterPoint(fingerAt.minus(fingerOffset));
    }

    public void addAngle(float diffAngle) {
        photo.angle += diffAngle;
    }

    public Photo getPhoto() {
        return photo;
    }

}
