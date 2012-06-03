package com.photobox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;

public class OurCustomView extends View {

    public boolean IN_DEBUG_MODE = true;
    
    private ScaleGestureDetector scaleDetector;
    private float scaleFactor = 1.f;

    private PhotoCollection collection;

    private Float previousFingerAngle = null;

    public OurCustomView(Context context) {
        this(context, null, 0);
    }

    public OurCustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OurCustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        SimpleOnScaleGestureListener scaleListener = new ScaleListener();
        scaleDetector = new ScaleGestureDetector(context, scaleListener);
        collection = new PhotoCollection();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.testimage_x);
        collection.addPhoto(new Photo().withBitmap(bitmap));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("panic", "someone is touching me... help!!");
        scaleDetector.onTouchEvent(event);
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            Log.d("iiiih!", "don't push it...");
            collection.fingerDown(toWorld(new Point(event.getX(), event.getY())));
            break;
        case MotionEvent.ACTION_UP:
            Log.d("phiew!", "back again!");
            previousFingerAngle = null;
            break;
        case MotionEvent.ACTION_MOVE:
            movePhoto(event);
            break;
        default:
            break;
        }
        invalidate();
        return true;
    }

    private Point toWorld(Point point) {
        Matrix m = new Matrix();
        m = setToWorld(m);

        float[] dst = new float[] { 0, 0 };
        float[] src = new float[] { point.x, point.y };
        m.mapPoints(dst, src);
        float newX = dst[0];
        float newY = dst[1];

        return new Point(newX, newY);
    }

    private Matrix setToWorld(Matrix m) {
        m.preScale(1/scaleFactor, 1/scaleFactor);
        m.preTranslate(-50, -50);
        return m;
    }

    private Matrix setFromWorld(Matrix m) {
        m.preTranslate(50, 50);
        m.preScale(scaleFactor, scaleFactor);
        return m;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setMatrix(setFromWorld(canvas.getMatrix()));
        renderBackground(canvas);
        for (Photo photo : collection.getPhotos()) {
            renderPhoto(canvas, photo);
        }
        renderAxis(canvas);
    }

    private void movePhoto(MotionEvent event) {
        if (collection.getActive() == null) {
            return;
        }
        if (event.getPointerCount() > 1) {
            double dy = event.getY(1) - event.getY(0);
            double dx = event.getX(1) - event.getX(0);
            double currentFingerAngle = Math.toDegrees(Math.atan2(dy, dx));
            if (previousFingerAngle != null) {
                double diffAngle = currentFingerAngle - previousFingerAngle;
                collection.getActive().angle += (float) diffAngle;
            }
            previousFingerAngle = new Float(currentFingerAngle);
        } else {
            previousFingerAngle = null;
        }
        Point worldPoint = toWorld(new Point(event.getX(), event.getY()));
        collection.getActive().centerX = worldPoint.x;
        collection.getActive().centerY = worldPoint.y;
    }

    private void renderBackground(Canvas canvas) {
        Paint bgPaint = new Paint();
        bgPaint.setARGB(255, 255, 255, 0);
        canvas.drawPaint(bgPaint);
    }
    
    private void renderAxis(Canvas canvas) {
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

    private void renderPhoto(Canvas canvas, Photo photo) {
        Bitmap image = BitmapFactory.decodeResource(getResources(),
                R.drawable.testimage_x);

        float w = photo.width;
        float h = photo.height;

        canvas.save();
        canvas.translate(photo.centerX, photo.centerY);
        canvas.rotate(photo.angle);

        Paint paint = new Paint();
        paint.setARGB(255, 255, 255, 255);
        canvas.drawRect(-w / 2, -h / 2, w / 2, h / 2, paint);

        canvas.drawBitmap(image, -w / 2 + photo.BORDER, -h / 2 + photo.BORDER,
                null);

        Paint p = new Paint();
        p.setColor(Color.BLUE);
        canvas.drawCircle(0, 0, 10, p);
        canvas.restore();
    }

    private class ScaleListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleDetector) {
            scaleFactor *= scaleDetector.getScaleFactor();
            return true;
        }
    }

}
