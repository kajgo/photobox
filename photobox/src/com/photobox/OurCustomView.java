package com.photobox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
    private Renderer renderer;

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
        renderer = new Renderer(debugger, mapping, collection);
        
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
        renderer.onDraw(canvas);
    }

    private void extractScreenCenter(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        
        mapping.SCREEN_CETNER_X = metrics.widthPixels / 2;
        mapping.SCREEN_CETNER_Y = metrics.heightPixels / 2;
    }
}
