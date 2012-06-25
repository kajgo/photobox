package com.photobox.input;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class DoubleTapDetector {

    public GestureDetector gestureDetector;
    boolean doubleTap = false;

    public DoubleTapDetector(Context context) {
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    public void onTouchEvent(MotionEvent event) {
        resetDoubleTap();
        gestureDetector.onTouchEvent(event);
    }

    public boolean isDoubleTapDetected () {
        return doubleTap;
    }

    private void resetDoubleTap() {
        doubleTap = false;
    }

    private class GestureListener extends
            GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }
        @Override
        public boolean onDoubleTap(MotionEvent event) {
            doubleTap = true;
            return true;
        }
    }

}
