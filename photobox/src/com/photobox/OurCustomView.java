package com.photobox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.photobox.input.InputHandler;
import com.photobox.renderer.Renderer;
import com.photobox.renderer.WorldMapping;
import com.photobox.world.GraphicalDebugger;
import com.photobox.world.Photo;
import com.photobox.world.PhotoCollection;

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
        mapping = new WorldMapping(context);
        collection = new PhotoCollection();
        inputHandler = new InputHandler(context, mapping, collection);
        debugger = new GraphicalDebugger(mapping);
        renderer = new Renderer(debugger, mapping, collection);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.testimage_x);
        collection.addPhoto(new Photo().withBitmap(bitmap));
        collection.addPhoto(new Photo().withBitmap(bitmap));
        collection.addPhoto(new Photo().withBitmap(bitmap));
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

}
