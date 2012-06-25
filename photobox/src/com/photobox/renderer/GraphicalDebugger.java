package com.photobox.renderer;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.photobox.world.Photo;
import com.photobox.world.Point;

public class GraphicalDebugger {

    public static final boolean IN_DEBUG_MODE = false;

    private static final float SMALL_RADIUS = 3;
    private static final float BIG_RADIUS = 10;
    private static final float HALF_AXIS_LENGTH = 20;

    private WorldMapping mapper;

    private List<Point> fingerPoints = new ArrayList<Point>();

    public GraphicalDebugger(WorldMapping mapper) {
        this.mapper = mapper;
    }

    public void storeFingerPoints(MotionEvent event) {
        if (!IN_DEBUG_MODE) {
            return;
        }
        fingerPoints.clear();
        for (int i = 0; i < event.getPointerCount(); i++) {
           fingerPoints.add(mapper.toWorld(new Point(event.getX(i), event.getY(i))));
        }
    }

    public void debugWorld(Canvas canvas) {
        if (!IN_DEBUG_MODE) {
            return;
        }
        renderAxis(canvas);
        renderFingers(canvas);
    }

    public void debugPhoto(Canvas canvas, Photo photo) {
        if (!IN_DEBUG_MODE) {
            return;
        }
        drawCenterPoint(canvas);
        printAngle(canvas, photo);
    }

    private void renderAxis(Canvas canvas) {
        Paint xAxisPaint = new Paint();
        xAxisPaint.setARGB(255, 0, 0, 255);
        Paint yAxisPaint = new Paint();
        yAxisPaint.setARGB(255, 255, 0, 0);
        canvas.drawLine(-HALF_AXIS_LENGTH, 0, HALF_AXIS_LENGTH, 0, xAxisPaint);
        canvas.drawCircle(HALF_AXIS_LENGTH, 0, SMALL_RADIUS, xAxisPaint);
        canvas.drawLine(0, -HALF_AXIS_LENGTH, 0, HALF_AXIS_LENGTH, yAxisPaint);
        canvas.drawCircle(0, HALF_AXIS_LENGTH, SMALL_RADIUS, yAxisPaint);
    }

    private void renderFingers(Canvas canvas) {
        Paint fingerPaint = new Paint();
        fingerPaint.setColor(Color.GREEN);
        for (Point p : fingerPoints) {
            canvas.drawCircle(p.x, p.y, BIG_RADIUS, fingerPaint);
        }
    }

    private void drawCenterPoint(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        canvas.drawCircle(0, 0, SMALL_RADIUS, p);
    }

    private void printAngle(Canvas canvas, Photo photo) {
        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        canvas.drawText("angle: " + photo.angle, 0, photo.height/2, textPaint);
    }

}
