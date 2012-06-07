package com.photobox.input;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.photobox.renderer.WorldMapping;

public class ThrowHandler {

    public GestureDetector gestureDetector;
    public WorldMapping mapping;

    public ThrowHandler(Context context, WorldMapping mapping) {
        gestureDetector = new GestureDetector(context, new GestureListener());
        this.mapping = mapping;
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
            mapping.reset();
            return true;
        }
    }

}
