package com.photobox.renderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.photobox.world.Photo;
import com.photobox.world.PhotoCollection;

import android.graphics.RectF;

public class Renderer {

    private GraphicalDebugger debugger;
    private WorldMapping mapping;
    private PhotoCollection collection;

    public Renderer(GraphicalDebugger debugger, WorldMapping mapping, PhotoCollection collection) {
        this.debugger = debugger;
        this.mapping = mapping;
        this.collection = collection;
    }

    public void renderBackground(Canvas canvas) {
        Paint bgPaint = new Paint();
        bgPaint.setARGB(255, 180, 180, 180);
        canvas.drawPaint(bgPaint);
    }

    public void renderPhoto(Canvas canvas, Photo photo) {
        renderPhotoBorder(canvas, photo);
        if (photo.hasBitmap()) {
            renderLoadedPhoto(canvas, photo);
        } else {
            renderUnloadedPhoto(canvas, photo);
        }
        debugger.debugPhoto(canvas, photo);
    }

    private void renderPhotoBorder(Canvas canvas, Photo photo) {
        Paint borderPaint = new Paint();
        borderPaint.setARGB(255, 255, 255, 255);
        borderPaint.setAntiAlias(true);
        canvas.drawRect(photo.photoRect(), borderPaint);
    }

    private void renderLoadedPhoto(Canvas canvas, Photo photo) {
        Paint photoPaint = new Paint();
        photoPaint.setAntiAlias(true);
        photoPaint.setFilterBitmap(true);
        canvas.drawBitmap(photo.getBitmap(), null, photo.bitmapRect(), photoPaint);
    }

    private void renderUnloadedPhoto(Canvas canvas, Photo photo) {
        Paint emptyPhotoPaint = new Paint();
        emptyPhotoPaint.setARGB(255, 210, 210, 210);
        emptyPhotoPaint.setAntiAlias(true);
        canvas.drawRect(photo.bitmapRect(), emptyPhotoPaint);
    }

    public void onDraw(Canvas canvas) {
        mapping.setWorldToScreenTransformation(canvas);
        renderBackground(canvas);
        for (Photo photo : collection.getPhotos()) {
            canvas.save();
            mapping.setPhotoToViewportTransformation(canvas, photo);
            renderPhoto(canvas, photo);
            canvas.restore();
        }
        debugger.debugWorld(canvas);
    }
}
