package com.photobox.input;

import android.content.Context;
import android.view.MotionEvent;

import com.photobox.renderer.WorldMapping;
import com.photobox.world.ActivePhoto;
import com.photobox.world.PhotoCollection;
import com.photobox.world.Point;

public class InputHandler {

    private WorldMapping mapping;
    private PhotoCollection collection;

    private Double previousFingerAngle = null;
    private ScaleHandler scaleHandler;
    private ThrowHandler throwHandler;
    private OffsetTracker offsetTracker = new OffsetTracker();
    private ActivePhoto activePhoto;

    public InputHandler(Context context, WorldMapping mapping, PhotoCollection collection) {
        this.mapping = mapping;
        this.collection = collection;
        scaleHandler = new ScaleHandler(context);
        throwHandler = new ThrowHandler(context);
    }

    public void onTouchEvent(MotionEvent event) {
        offsetTracker.onTouchEvent(event);
        throwHandler.onTouchEvent(event);
        scaleHandler.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setActivePhoto(extractWorldPoint(event, 0));
                break;
            case MotionEvent.ACTION_UP:
                previousFingerAngle = null;
                break;
            case MotionEvent.ACTION_MOVE:
                if (noActivePhoto()) {
                    if (event.getPointerCount() == 1) {
                        mapping.moveOriginScreenPositionBy(offsetTracker.getOffset());
                    }
                } else {
                    movePhoto(event);
                }
                break;
            default:
                break;
        }
        if (noActivePhoto()) {
            scaleWorld();
            resetWorld();
        }
    }

    private void scaleWorld() {
        float newScaleFactor = mapping.scaleFactor * scaleHandler.getRegisteredScaleFactor();
        mapping.scaleFactor = (float)Math.max((double)newScaleFactor, 0.1);
    }

    private void resetWorld() {
        if (throwHandler.getRegistredDoubleTap() == true) {
            mapping.reset();
        }
    }

    private Point extractWorldPoint(MotionEvent event, int which) {
        return mapping.toWorld(new Point(event.getX(which), event.getY(which)));
    }

    private boolean noActivePhoto() {
        return activePhoto == null;
    }

    private void setActivePhoto(Point fingerPoint) {
        collection.fingerDown(fingerPoint);
        if (collection.getActive() == null) {
            activePhoto = null;
        } else {
            activePhoto = ActivePhoto.fromFingerPoint(fingerPoint, collection.getActive());
        }
    }

    private void movePhoto(MotionEvent event) {
        if (event.getPointerCount() > 1) {
            Point p1 = extractWorldPoint(event, 0);
            Point p2 = extractWorldPoint(event, 1);
            Point pDiff = p2.minus(p1);
            double currentFingerAngle = Math.toDegrees(Math.atan2(pDiff.y, pDiff.x));
            if (previousFingerAngle != null) {
                double diffAngle = currentFingerAngle - previousFingerAngle;
                activePhoto.addAngle((float) diffAngle);
            }
            previousFingerAngle = new Double(currentFingerAngle);
        } else {
            previousFingerAngle = null;
        }
        activePhoto.move(mapping.toWorld(new Point(event.getX(), event.getY())));
    }

}
