package com.photobox.app;

import com.photobox.files.ImportHandler;
import com.photobox.input.InputActor;
import com.photobox.input.InputState;
import com.photobox.renderer.GraphicalDebugger;
import com.photobox.renderer.Renderer;
import com.photobox.renderer.WorldMapping;
import com.photobox.world.PhotoCollection;
import com.photobox.world.Point;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class PhotoView extends View {

    private GraphicalDebugger debugger;
    private PhotoCollection collection;
    private WorldMapping mapping;
    private Renderer renderer;
    private InputState inputState;
    private InputActor inputActor;

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
        renderer = new Renderer(debugger, mapping, collection);
        inputState = new InputState(context, mapping);
        inputActor = new InputActor(mapping, collection);

        ImportHandler importHandler = new ImportHandler();
        Resources resources = getResources();
        importHandler.importPhotos(collection, resources);
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

    private Point extractScreenCenter(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return new Point(metrics.widthPixels / 2, metrics.heightPixels / 2);
    }

}
