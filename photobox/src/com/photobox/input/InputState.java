package com.photobox.input;

import android.content.Context;
import android.view.MotionEvent;

import com.photobox.world.Point;

public class InputState {

    private OffsetTracker offsetTracker = new OffsetTracker();
    private ScaleHandler scaleHandler;
    private ThrowHandler throwHandler;

    public InputState(Context context) {
        scaleHandler = new ScaleHandler(context);
        throwHandler = new ThrowHandler(context);
    }

    public Point getMoveOffset() {
        return offsetTracker.getOffset();
    }

    public float getRegisteredScaleFactor() {
        return scaleHandler.getRegisteredScaleFactor();
    }

    public boolean getRegistredDoubleTap() {
        return throwHandler.getRegistredDoubleTap();
    }

    public void onTouchEvent(MotionEvent event) {
        offsetTracker.onTouchEvent(event);
        scaleHandler.onTouchEvent(event);
        throwHandler.onTouchEvent(event);
    }

}
