package com.photobox;

import com.photobox.input.ImportHandler;
import com.photobox.input.InputActor;
import com.photobox.input.InputState;
import com.photobox.renderer.Renderer;
import com.photobox.renderer.WorldMapping;
import com.photobox.world.GraphicalDebugger;
import com.photobox.world.PhotoCollection;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class OurCustomView extends View {

    private GraphicalDebugger debugger;
    private PhotoCollection collection;
    private WorldMapping mapping;
    private Renderer renderer;
    private InputState inputState;
    private InputActor inputActor;

    public OurCustomView(Context context) {
        this(context, null, 0);
    }

    public OurCustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OurCustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mapping = new WorldMapping(context);
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
        inputActor.act(inputState);
        debugger.storeFingerPoints(event);
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        renderer.onDraw(canvas);
    }

}
