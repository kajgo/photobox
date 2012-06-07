package com.photobox.renderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.photobox.world.GraphicalDebugger;
import com.photobox.world.Photo;
import com.photobox.world.PhotoCollection;

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
        Bitmap image = photo.image;
        float w = photo.width;
        float h = photo.height;

        Paint borderPaint = new Paint();
        borderPaint.setARGB(255, 255, 255, 255);
        borderPaint.setAntiAlias(true);

        Paint photoPaint = new Paint();
        photoPaint.setAntiAlias(true);
        photoPaint.setFilterBitmap(true);

        canvas.drawRect(-w / 2, -h / 2, w / 2, h / 2, borderPaint);
        canvas.drawBitmap(image, -w / 2 + photo.BORDER, -h / 2 + photo.BORDER, photoPaint);
        debugger.debugPhoto(canvas, photo);
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
