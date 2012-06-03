package com.photobox;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class GraphicalDebugger {
    
    public final boolean IN_DEBUG_MODE = true;
    
    private List<Point> fingerPoints = new ArrayList<Point>();
    
    private OurCustomView view;
    
    public GraphicalDebugger(OurCustomView view) {
        this.view = view;
    }
    
    public void storeFingerPoints(MotionEvent event) {
        if (!IN_DEBUG_MODE) {
            return;
        }
        fingerPoints.clear();
        for (int i = 0; i < event.getPointerCount(); i++) {
           fingerPoints.add(view.toWorld(new Point(event.getX(i), event.getY(i))));
        }
    }

    public void renderFingers(Canvas canvas) {
        if (!IN_DEBUG_MODE) {
            return;
        }
        Paint fingerPaint = new Paint();
        fingerPaint.setColor(Color.GREEN);
        float RADIUS = 3;
        for (Point p : fingerPoints) {
            canvas.drawCircle(p.x, p.y, RADIUS, fingerPaint);
        }
    }
    
    public void renderAxis(Canvas canvas) {
        if (!IN_DEBUG_MODE) {
            return;
        }
        Paint xAxisPaint = new Paint();
        xAxisPaint.setARGB(255, 0, 0, 255);
        Paint yAxisPaint = new Paint();
        yAxisPaint.setARGB(255, 255, 0, 0);
        float SIZE = 10 + 10;
        float RADIUS = 3;
        canvas.drawLine(-SIZE, 0, SIZE, 0, xAxisPaint);
        canvas.drawCircle(SIZE, 0, RADIUS, xAxisPaint);
        canvas.drawLine(0, -SIZE, 0, SIZE, yAxisPaint);
        canvas.drawCircle(0, SIZE, RADIUS, yAxisPaint);
    }

    public void drawCenterPoint(Canvas canvas) {
        if (IN_DEBUG_MODE) {
            Paint p = new Paint();
            p.setColor(Color.BLUE);
            canvas.drawCircle(0, 0, 10, p);
        }
    }

    public void printAngle(Canvas canvas, Photo photo) {
        if(IN_DEBUG_MODE) {
            Paint textPaint = new Paint();
            textPaint.setColor(Color.BLACK);
            canvas.drawText("angle: " + photo.angle, 0, -photo.height/2, textPaint);
        }
    }


}
