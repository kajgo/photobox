package com.photobox.input;

import android.content.Context;
import android.view.MotionEvent;

import com.photobox.world.Point;

public class InputState {

    private OffsetTracker offsetTracker = new OffsetTracker();
    private ScaleHandler scaleHandler;
    private ThrowHandler throwHandler;
    private boolean isDown;
    private boolean isUp;
    private boolean isMove;

    public InputState(Context context) {
        scaleHandler = new ScaleHandler(context);
        throwHandler = new ThrowHandler(context);
    }

    public void onTouchEvent(MotionEvent event) {
        offsetTracker.onTouchEvent(event);
        scaleHandler.onTouchEvent(event);
        throwHandler.onTouchEvent(event);
        isDown = event.getAction() == MotionEvent.ACTION_DOWN;
        isUp = event.getAction() == MotionEvent.ACTION_UP;
        isMove = event.getAction() == MotionEvent.ACTION_MOVE;
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

    public boolean isDown() {
        return isDown;
    }

    public boolean isUp() {
        return isUp;
    }

    public boolean isMove() {
        return isMove;
    }

}
