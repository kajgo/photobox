package com.photobox.app;

import java.io.File;

import com.photobox.files.BitmapCache;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.photobox.files.BitmapSize;
import com.photobox.files.ImportHandler;
import com.photobox.input.InputActor;
import com.photobox.input.InputState;
import com.photobox.renderer.GraphicalDebugger;
import com.photobox.renderer.Renderer;
import com.photobox.renderer.WorldMapping;
import com.photobox.world.PhotoCollection;
import com.photobox.world.Point;

public class PhotoView extends View {

    private GraphicalDebugger debugger;
    private PhotoCollection collection;
    private WorldMapping mapping;
    private Renderer renderer;
    private InputState inputState;
    private InputActor inputActor;
    private BitmapCache bitmapCache;

    public PhotoView(Context context) {
        this(context, null, 0);
    }

    public PhotoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mapping = new WorldMapping(extractScreenCenter(context));
        collection = new PhotoCollection();
        debugger = new GraphicalDebugger(mapping);
        bitmapCache = new BitmapCache(getScreenSize(context).max());
        renderer = new Renderer(debugger, mapping, collection, bitmapCache);
        inputState = new InputState(context, mapping);
        inputActor = new InputActor(mapping, collection, bitmapCache);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        inputState.onTouchEvent(event);
        inputActor.takeAction(inputState);
        debugger.storeFingerPoints(event);
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        renderer.onDraw(canvas);
    }

    public void loadDemoPhotos() {
        ImportHandler importHandler = new ImportHandler(bitmapCache);
        importHandler.importDemoPhotos(collection, getResources());
    }

    public void loadPhotosFromDir(File photoDir) {
        ImportHandler importHandler = new ImportHandler(bitmapCache);
        importHandler.importPhotosFromDir(collection, photoDir);
    }

    private Point extractScreenCenter(Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context);
        return new Point(metrics.widthPixels / 2, metrics.heightPixels / 2);
    }

    private BitmapSize getScreenSize(Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context);
        return new BitmapSize(metrics.widthPixels, metrics.heightPixels);
    }

    private DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics;
    }

}
