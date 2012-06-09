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
    private Double previousFingerAngle = null;
    private ActivePhoto activePhoto;

    public InputHandler(Context context, WorldMapping mapping, PhotoCollection collection) {
        inputState = new InputState(context, mapping);
        this.mapping = mapping;
        this.collection = collection;
    }

    public void onTouchEvent(MotionEvent event) {
        inputState.onTouchEvent(event);

        if (inputState.isDown()) {
            setActivePhoto(inputState.worldFingerPoints().get(0));
        }
        if (inputState.isUp()) {
            previousFingerAngle = null;
        }
        if (inputState.isMove()) {
            if (noActivePhoto()) {
                if (event.getPointerCount() == 1) {
                    mapping.moveOriginScreenPositionBy(inputState.getMoveOffset());
                }
            } else {
                movePhoto();
            }
        }
        if (noActivePhoto()) {
            scaleWorld();
            resetWorld();
        }
    }

    private void scaleWorld() {
        float newScaleFactor = mapping.scaleFactor * inputState.getRegisteredScaleFactor();
        mapping.scaleFactor = (float)Math.max((double)newScaleFactor, 0.1);
    }

    private void resetWorld() {
        if (inputState.getRegistredDoubleTap() == true) {
            mapping.reset();
        }
    }

    private boolean noActivePhoto() {
        return activePhoto == null;
    }

    private void setActivePhoto(Point fingerPoint) {
        collection.fingerDown(fingerPoint);
        if (collection.getActive() == null) {
            activePhoto = null;
        } else {
            activePhoto = ActivePhoto.fromFingerPoint(fingerPoint, collection.getActive());
        }
    }

    private void movePhoto() {
        if (inputState.worldFingerPoints().size() > 1) {
            Point p1 = inputState.worldFingerPoints().get(0);
            Point p2 = inputState.worldFingerPoints().get(1);
            Point pDiff = p2.minus(p1);
            double currentFingerAngle = Math.toDegrees(Math.atan2(pDiff.y, pDiff.x));
            if (previousFingerAngle != null) {
                double diffAngle = currentFingerAngle - previousFingerAngle;
                activePhoto.addAngle((float) diffAngle);
            }
            previousFingerAngle = new Double(currentFingerAngle);
        } else {
            previousFingerAngle = null;
        }
        activePhoto.move(inputState.worldFingerPoints().get(0));
    }

}
