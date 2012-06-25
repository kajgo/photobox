package com.photobox.input;

import com.photobox.world.Point;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;

public class PinchDetector {

    private ScaleGestureDetector scaleDetector;
    private float registeredScaleFactor;
    private Point scalePoint;

    public PinchDetector(Context context) {
        resetScaleFactor();
        SimpleOnScaleGestureListener scaleListener = new ScaleListener();
        scaleDetector = new ScaleGestureDetector(context, scaleListener);
    }

    public void onTouchEvent(MotionEvent event) {
        resetScaleFactor();
        scaleDetector.onTouchEvent(event);
        if (event.getPointerCount() == 2) {
            float x1 = event.getX(0);
            float y1 = event.getY(0);
            float x2 = event.getX(1);
            float y2 = event.getY(1);
            scalePoint = new Point(x1, y1).halfWayTo(new Point(x2, y2));
        } else {
            resetScaleFactor();
        }
    }

    public float getRegisteredScaleFactor() {
        return registeredScaleFactor;
    }

    public Point getScalePoint() {
        return scalePoint;
    }

    private void resetScaleFactor() {
        registeredScaleFactor = 1;
        scalePoint = null;
    }

    private class ScaleListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleDetector) {
            registeredScaleFactor = scaleDetector.getScaleFactor();
            return true;
        }
    }

}
