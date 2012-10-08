package com.photobox.app;

import java.io.File;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.photobox.files.*;
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
    private AsynchPhotoLoader asynchPhotoLoader;
    private InputActor inputActor;
    private ResolutionLadder ladder;

    public PhotoView(Context context) {
        this(context, null, 0);
    }

    public PhotoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mapping = new WorldMapping(extractScreenCenter());
        collection = new PhotoCollection();
        debugger = new GraphicalDebugger(mapping);
        renderer = new Renderer(debugger, mapping, collection);
        inputState = new InputState(context, mapping);
        int maxSize = getScreenSize().max();
        asynchPhotoLoader = new AsynchPhotoLoader(this, maxSize);
        ladder = new ResolutionLadder(1, 1, asynchPhotoLoader,
                new ResolutionLadder(3, 0.5f, asynchPhotoLoader,
                    new ResolutionLadder(10, 0.15f, asynchPhotoLoader, null)));
        inputActor = new InputActor(mapping, collection, ladder);
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
        ImportHandler importHandler = new ImportHandler(ladder);
        importHandler.importDemoPhotos(collection, getResources());
        throwPhotos();
    }

    public void loadPhotosFromDir(File photoDir) {
        ImportHandler importHandler = new ImportHandler(ladder);
        importHandler.importPhotosFromDir(collection, photoDir);
        throwPhotos();
    }

    private void throwPhotos() {
        BitmapSize screenSize = getScreenSize();
        int throwBoxSize = (int)Math.round((float)screenSize.min()/mapping.scaleFactor/2);
        collection.throwPhotos(throwBoxSize);
    }

    private Point extractScreenCenter() {
        DisplayMetrics metrics = getDisplayMetrics();
        return new Point(metrics.widthPixels / 2, metrics.heightPixels / 2);
    }

    private BitmapSize getScreenSize() {
        DisplayMetrics metrics = getDisplayMetrics();
        return new BitmapSize(metrics.widthPixels, metrics.heightPixels);
    }

    private DisplayMetrics getDisplayMetrics() {
        WindowManager wm = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics;
    }

}
