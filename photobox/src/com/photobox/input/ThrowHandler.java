package com.photobox.input;

import com.photobox.world.*;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;

public class ThrowHandler {

    public GestureDetector gestureDetector;

    public ThrowHandler(Context context) {
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    public void onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
    }

    private class GestureListener extends
            GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }
        @Override
        public boolean onDoubleTap(MotionEvent event) {
            Log.d("Pip!", "DoubleTap detected.");
            return true;
        }
    }

}
