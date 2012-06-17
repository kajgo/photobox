package com.photobox.input;

import android.content.Context;
import android.view.MotionEvent;

import com.photobox.renderer.WorldMapping;
import com.photobox.world.ActivePhoto;
import com.photobox.world.PhotoCollection;
import com.photobox.world.Point;

public class InputHandler {

    private WorldMapping mapping;
    private PhotoCollection collection;

    private InputState inputState;
    private ActivePhoto activePhoto;

    public InputHandler(Context context, WorldMapping mapping, PhotoCollection collection) {
        inputState = new InputState(context, mapping);
        this.mapping = mapping;
        this.collection = collection;
    }

    public void onTouchEvent(MotionEvent event) {
        inputState.onTouchEvent(event);
        new InputActor(mapping, collection).act(inputState);
    }

}
