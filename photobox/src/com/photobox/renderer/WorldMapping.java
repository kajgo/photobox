package com.photobox.renderer;

import android.graphics.Canvas;
import android.graphics.Matrix;

import com.photobox.world.Photo;
import com.photobox.world.Point;

public class WorldMapping {

    public float scaleFactor;
    public Point originScreenPosition;
    public Point screenCenterPoint;

    public WorldMapping(Point screenCenterPoint) {
        this.screenCenterPoint = screenCenterPoint;
        reset();
    }

    public void moveOriginScreenPositionBy(Point offset) {
        originScreenPosition = originScreenPosition.plus(offset);
    }

    public void zoomAround(float newScaleFactor, Point aroundScreenPoint) {
        Point aroundInWorld = toWorld(aroundScreenPoint);
        scaleFactor = newScaleFactor;
        Point aroundNewScreenPoint = fromWorld(aroundInWorld);
        Point aroundMoveDelta = aroundNewScreenPoint.minus(aroundScreenPoint);
        originScreenPosition = originScreenPosition.minus(aroundMoveDelta);
    }

    public Point toWorld(Point point) {
        Matrix m = new Matrix();
        m = setToWorld(m);
        return point.translate(m);
    }

    public Point fromWorld(Point point) {
        Matrix m = new Matrix();
        m = setFromWorld(m);
        return point.translate(m);
    }

    public Matrix setToWorld(Matrix m) {
        m.preScale(1/scaleFactor, -1/scaleFactor);
        m.preTranslate(-originScreenPosition.x, -originScreenPosition.y);
        return m;
    }

    public Matrix setFromWorld(Matrix m) {
        m.preTranslate(originScreenPosition.x, originScreenPosition.y);
        m.preScale(scaleFactor, -scaleFactor);
        return m;
    }

    public void setPhotoToViewportTransformation(Canvas c, Photo p) {
        c.translate(p.centerX, p.centerY);
        c.rotate(p.angle);
        c.scale(1, -1);
    }

    public void setWorldToScreenTransformation(Canvas c) {
        c.setMatrix(setFromWorld(c.getMatrix()));
    }

    public void reset() {
        scaleFactor = 0.4f;
        originScreenPosition = screenCenterPoint;
    }

}
