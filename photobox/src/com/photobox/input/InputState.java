package com.photobox.input;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.MotionEvent;

import com.photobox.renderer.WorldMapping;
import com.photobox.world.Point;

public class InputState {

    private FingerMoveDetector fingerMoveDetector = new FingerMoveDetector();
    private PinchDetector pinchDetector;
    private DoubleTapDetector doubleTapDetector;
    private boolean isDown;
    private boolean isMove;
    private WorldMapping mapping;
    private List<Point> points;
    private Double fingerRotation;
    private Double fingerRotationDelta;

    public InputState(Context context, WorldMapping mapping) {
        pinchDetector = new PinchDetector(context);
        doubleTapDetector = new DoubleTapDetector(context);
        this.mapping = mapping;
    }

    public void onTouchEvent(MotionEvent event) {
        points = new ArrayList<Point>();
        for (int i=0; i<event.getPointerCount(); i++) {
            points.add(extractWorldPoint(event, i));
        }
        fingerMoveDetector.onTouchEvent(event);
        pinchDetector.onTouchEvent(event);
        doubleTapDetector.onTouchEvent(event);
        isDown = event.getAction() == MotionEvent.ACTION_DOWN;
        isMove = event.getAction() == MotionEvent.ACTION_MOVE;
        if (event.getAction() == MotionEvent.ACTION_UP) {
            resetFingerRotation();
        }
        calculateFingerRotation();
    }

    public Point getMoveOffset() {
        return fingerMoveDetector.getOffset();
    }

    public float getRegisteredScaleFactor() {
        return pinchDetector.getRegisteredScaleFactor();
    }

    public Point getScalePoint() {
        return pinchDetector.getScalePoint();
    }

    public boolean getRegistredDoubleTap() {
        return doubleTapDetector.isDoubleTapDetected();
    }

    public boolean isDown() {
        return isDown;
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

    public void calculateFingerRotation() {
        fingerRotationDelta = null;
        if (worldFingerPoints().size() == 2) {
            Point p1 = worldFingerPoints().get(0);
            Point p2 = worldFingerPoints().get(1);
            Point pDiff = p2.minus(p1);
            double currentFingerAngle = Math.toDegrees(Math.atan2(pDiff.y, pDiff.x));
            if (fingerRotation != null) {
                fingerRotationDelta = currentFingerAngle - fingerRotation;
            }
            fingerRotation = new Double(currentFingerAngle);
        } else {
            fingerRotation = null;
        }
    }

    public boolean hasFingerRotationDelta() {
        return fingerRotationDelta != null;
    }

    public Double getFingerRotationDelta() {
        return fingerRotationDelta;
    }

    public void resetFingerRotation () {
        fingerRotation = null;
    }

    public int getNumberOfFingers() {
        return worldFingerPoints().size();
    }

    private Point extractWorldPoint(MotionEvent event, int which) {
        return mapping.toWorld(new Point(event.getX(which), event.getY(which)));
    }

}
