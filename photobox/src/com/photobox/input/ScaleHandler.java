package com.photobox.input;

import com.photobox.world.*;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;

public class ScaleHandler {

    public ScaleGestureDetector scaleDetector;
    public WorldMapping mapping;

    public ScaleHandler(Context context, WorldMapping mapping) {
        this.mapping = mapping;
        SimpleOnScaleGestureListener scaleListener = new ScaleListener();
        scaleDetector = new ScaleGestureDetector(context, scaleListener);
    }

    public void onTouchEvent(MotionEvent event) {
        scaleDetector.onTouchEvent(event);
    }

    private class ScaleListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleDetector) {
            float newScaleFactor = mapping.scaleFactor * scaleDetector.getScaleFactor();
            mapping.scaleFactor = (float)Math.max((double)newScaleFactor, 0.1);
            return true;
        }
    }

}
