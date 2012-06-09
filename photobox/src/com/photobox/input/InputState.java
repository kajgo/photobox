package com.photobox.input;

import android.content.Context;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

import com.photobox.world.Point;
import com.photobox.renderer.WorldMapping;

public class InputState {

    private OffsetTracker offsetTracker = new OffsetTracker();
    private ScaleHandler scaleHandler;
    private ThrowHandler throwHandler;
    private boolean isDown;
    private boolean isUp;
    private boolean isMove;
    private WorldMapping mapping;
    private List<Point> points;

    public InputState(Context context, WorldMapping mapping) {
        scaleHandler = new ScaleHandler(context);
        throwHandler = new ThrowHandler(context);
        this.mapping = mapping;
    }

    public void onTouchEvent(MotionEvent event) {
        points = new ArrayList<Point>();
        for (int i=0; i<event.getPointerCount(); i++) {
            points.add(extractWorldPoint(event, i));
        }
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

    public List<Point> worldFingerPoints() {
        return points;
    }

    public boolean isOneFingerDown() {
        return worldFingerPoints().size() == 1;
    }

    private Point extractWorldPoint(MotionEvent event, int which) {
        return mapping.toWorld(new Point(event.getX(which), event.getY(which)));
    }

}
