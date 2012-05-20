package com.photobox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;

public class OurCustomView extends View {

    private float px = 20;
    private float py = 20;
    private float angle = 0;
    private Float previousFingerAngle = null;
    private ScaleGestureDetector scaleDetector;
    private float scaleFactor = 1.f;

    public OurCustomView(Context context) {
        super(context);
        ScaleListener scaleListener = new ScaleListener();
        scaleDetector = new ScaleGestureDetector(context, scaleListener);
    }

    public OurCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        SimpleOnScaleGestureListener scaleListener = new ScaleListener();
        scaleDetector = new ScaleGestureDetector(context, scaleListener);
    }

    public OurCustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        SimpleOnScaleGestureListener scaleListener = new ScaleListener();
        scaleDetector = new ScaleGestureDetector(context, scaleListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("panic", "someone is touching me... help!!");
        scaleDetector.onTouchEvent(event);
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            Log.d("iiiih!", "don't push it...");
            break;
        case MotionEvent.ACTION_UP:
            Log.d("phiew!", "back again!");
            previousFingerAngle = null;
            break;
        case MotionEvent.ACTION_MOVE:
            if (event.getPointerCount() > 1) {
                double dy = event.getY(1) - event.getY(0);
                double dx = event.getX(1) - event.getX(0);
                double currentFingerAngle = Math.toDegrees(Math.atan2(dy, dx));
                if (previousFingerAngle != null) {
                    double diffAngle = currentFingerAngle - previousFingerAngle;
                    angle += (float) diffAngle;
                }
                previousFingerAngle = new Float(currentFingerAngle);
            } else {
                previousFingerAngle = null;
            }
            px = event.getX();
            py = event.getY();
            Log.d("coords", "px=" + px + ", py=" + py);
            invalidate();
            break;
        default:
            break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.scale(scaleFactor, scaleFactor);

        Paint bgPaint = new Paint();
        bgPaint.setARGB(255, 255, 255, 0);
        canvas.drawPaint(bgPaint);

        Bitmap image = BitmapFactory.decodeResource(getResources(),
                R.drawable.testimage_x);

        int border = 10;
        int w = image.getWidth() + border * 2;
        int h = image.getHeight() + border * 2;

        canvas.translate(px, py);
        canvas.rotate(angle);

        Paint paint = new Paint();
        paint.setARGB(255, 255, 255, 255);
        canvas.drawRect(-w / 2, -h / 2, w / 2, h / 2, paint);

        canvas.drawBitmap(image, -w / 2 + border, -h / 2 + border, null);

        Paint p = new Paint();
        p.setColor(Color.BLUE);
        canvas.drawCircle(0, 0, 10, p);
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
