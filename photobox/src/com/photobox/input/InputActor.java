package com.photobox.input;

import com.photobox.renderer.WorldMapping;
import com.photobox.world.ActivePhoto;
import com.photobox.world.PhotoCollection;
import com.photobox.world.Point;

public class InputActor {

    private WorldMapping mapping;
    private PhotoCollection collection;
    private ActivePhoto activePhoto;

    public InputActor(WorldMapping mapping, PhotoCollection collection) {
        this.mapping = mapping;
        this.collection = collection;
    }

    public void takeAction(InputState inputState) {
        scaleWorld(inputState);
        if (inputState.isDown()) {
            setActivePhoto(inputState.worldFingerPoints().get(0));
        }
        if (inputState.isMove()) {
            if (noActivePhoto()) {
                if (inputState.isOneFingerDown()) {
                    mapping.moveOriginScreenPositionBy(inputState.getMoveOffset());
                }
            } else {
                movePhoto(inputState);
            }
        }
        if (noActivePhoto() && inputState.getRegistredDoubleTap() == true) {
            mapping.reset();
        }
    }

    private void scaleWorld(InputState inputState) {
        if (inputState.getScalePoint() == null) {
            return;
        }
        float newScaleFactor =
           mapping.scaleFactor * inputState.getRegisteredScaleFactor();
        newScaleFactor = (float)Math.max((double)newScaleFactor, 0.1);
        mapping.zoomAround(newScaleFactor, inputState.getScalePoint());
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

    private void movePhoto(InputState inputState) {
        if (inputState.hasThreeFingerRotationDelta()) {
            activePhoto.addAngle(inputState.getThreeFingerRotationDelta().floatValue());
        }
        if (inputState.getNumberOfFingers() == 1 ||
            inputState.getNumberOfFingers() == 3) {
            activePhoto.move(inputState.worldFingerPoints().get(0));
        }
    }

}
