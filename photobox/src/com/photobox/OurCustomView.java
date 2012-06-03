package com.photobox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import android.view.WindowManager;

public class OurCustomView extends View {

    private GraphicalDebugger debugger;
    
    private ScaleGestureDetector scaleDetector;

    private PhotoCollection collection;
    private WorldMapping mapping = new WorldMapping();

    private Float previousFingerAngle = null;

    public OurCustomView(Context context) {
        this(context, null, 0);
    }

    public OurCustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OurCustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        debugger = new GraphicalDebugger(mapping);
        SimpleOnScaleGestureListener scaleListener = new ScaleListener();
        scaleDetector = new ScaleGestureDetector(context, scaleListener);
        collection = new PhotoCollection();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.testimage_x);
        collection.addPhoto(new Photo().withBitmap(bitmap));
        
        extractScreenCenter(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        debugger.storeFingerPoints(event);
        Log.d("panic", "someone is touching me... help!!");
        scaleDetector.onTouchEvent(event);
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            Log.d("iiiih!", "don't push it...");
            collection.fingerDown(mapping.toWorld(new Point(event.getX(), event.getY())));
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setMatrix(mapping.setFromWorld(canvas.getMatrix()));
        renderBackground(canvas);
        for (Photo photo : collection.getPhotos()) {
            renderPhoto(canvas, photo);
        }
        debugger.renderAxis(canvas);
        debugger.renderFingers(canvas);
    }

    private void extractScreenCenter(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        
        mapping.SCREEN_CETNER_X = metrics.widthPixels / 2;
        mapping.SCREEN_CETNER_Y = metrics.heightPixels / 2;
    }

    private void movePhoto(MotionEvent event) {
        if (collection.getActive() == null) {
            return;
        }
        if (event.getPointerCount() > 1) {
            Point p1 = mapping.toWorld(new Point(event.getX(0), event.getY(0)));
            Point p2 = mapping.toWorld(new Point(event.getX(1), event.getY(1)));
            Point pDiff = p2.minus(p1);
            double currentFingerAngle = Math.toDegrees(Math.atan2(pDiff.y, pDiff.x));
            if (previousFingerAngle != null) {
                double diffAngle = currentFingerAngle - previousFingerAngle;
                collection.getActive().angle += (float) diffAngle;
            }
            previousFingerAngle = new Float(currentFingerAngle);
        } else {
            previousFingerAngle = null;
        }
        collection.getActive().setCenterPoint(mapping.toWorld(new Point(event.getX(), event.getY())));
    }

    private void renderBackground(Canvas canvas) {
        Paint bgPaint = new Paint();
        bgPaint.setARGB(255, 255, 255, 0);
        canvas.drawPaint(bgPaint);
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

        debugger.drawCenterPoint(canvas);
        debugger.printAngle(canvas, photo);
        canvas.restore();
    }

    private class ScaleListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleDetector) {
            mapping.scaleFactor *= scaleDetector.getScaleFactor();
            return true;
        }
    }

}
