package com.photobox;

import com.photobox.world.*;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;

public class InputHandler {

    public ScaleGestureDetector scaleDetector;
    public WorldMapping mapping;
    public Float previousFingerAngle = null;
    public PhotoCollection collection;

    public Point photoOffset;

    public InputHandler(Context context, WorldMapping mapping, PhotoCollection collection) {
        SimpleOnScaleGestureListener scaleListener = new ScaleListener();
        scaleDetector = new ScaleGestureDetector(context, scaleListener);
        this.mapping = mapping;
        this.collection = collection;
    }

    public void onTouchEvent(MotionEvent event) {
        scaleDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Point fingerPoint = mapping.toWorld(new Point(event.getX(), event.getY()));
                collection.fingerDown(fingerPoint);
                if (collection.getActive() != null) {
                    photoOffset = fingerPoint.minus(collection.getActive().getCenterPoint());
                }
                break;
            case MotionEvent.ACTION_UP:
                previousFingerAngle = null;
                break;
            case MotionEvent.ACTION_MOVE:
                movePhoto(event);
                break;
            default:
                break;
        }
    }

    private void movePhoto(MotionEvent event) {
        if (collection.getActive() == null) {
            return;
        }
        if (event.getPointerCount() > 1) {
            Point p1 = mapping.toWorld(new Point(event.getX(0), event.getY(0)));
            Point p2 = mapping.toWorld(new Point(event.getX(1), event.getY(1)));
            Point pDiff = p2.minus(p1);
            double currentFingerAngle = Math.toDegrees(Math.atan2(pDiff.y, pDiff.x));
            if (previousFingerAngle != null) {
                double diffAngle = currentFingerAngle - previousFingerAngle;
                collection.getActive().angle += (float) diffAngle;
            }
            previousFingerAngle = new Float(currentFingerAngle);
        } else {
            previousFingerAngle = null;
        }
        collection.getActive().setCenterPoint(mapping.toWorld(new Point(event.getX(), event.getY())).minus(photoOffset));
    }

    private class ScaleListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleDetector) {
            mapping.scaleFactor *= scaleDetector.getScaleFactor();
            return true;
        }
    }

}
