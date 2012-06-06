package com.photobox.input;

import com.photobox.world.*;

import android.view.MotionEvent;

public class OffsetTracker {

    private Point previousPoint = null;
    private Point offset = null;

    public void onTouchEvent(MotionEvent event) {
        Point currentPoint = new Point(event.getX(), event.getY());
        if (previousPoint != null) {
            offset = currentPoint.minus(previousPoint);
        }
        previousPoint = currentPoint;
    }

    public Point getOffset() {
        return offset;
    }

}
