package com.photobox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class OurCustomView extends View {

    private GraphicalDebugger debugger;
    private PhotoCollection collection;
    private WorldMapping mapping;
    private InputHandler inputHandler;

    public OurCustomView(Context context) {
        this(context, null, 0);
    }

    public OurCustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OurCustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mapping = new WorldMapping();
        collection = new PhotoCollection();
        inputHandler = new InputHandler(context, mapping, collection);
        debugger = new GraphicalDebugger(mapping);
        
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.testimage_x);
        collection.addPhoto(new Photo().withBitmap(bitmap));
        
        extractScreenCenter(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        inputHandler.onTouchEvent(event);
        debugger.storeFingerPoints(event);
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

}
