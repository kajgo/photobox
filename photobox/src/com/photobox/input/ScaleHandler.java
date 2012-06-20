package com.photobox.input;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;

public class ScaleHandler {

    public ScaleGestureDetector scaleDetector;
    private float registeredScaleFactor;

    public ScaleHandler(Context context) {
        resetScaleFactor();
        SimpleOnScaleGestureListener scaleListener = new ScaleListener();
        scaleDetector = new ScaleGestureDetector(context, scaleListener);
    }

    public void onTouchEvent(MotionEvent event) {
        resetScaleFactor();
        scaleDetector.onTouchEvent(event);
        if (event.getPointerCount() != 2) {
            resetScaleFactor();
        }
    }

    public float getRegisteredScaleFactor() {
        return registeredScaleFactor;
    }

    private void resetScaleFactor() {
        registeredScaleFactor = 1;
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
