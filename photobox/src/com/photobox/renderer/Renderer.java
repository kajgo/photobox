package com.photobox.world;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

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
        bgPaint.setARGB(255, 255, 255, 0);
        canvas.drawPaint(bgPaint);
    }

    public void renderPhoto(Canvas canvas, Photo photo) {
        Bitmap image = photo.image;
        float w = photo.width;
        float h = photo.height;

        canvas.save();
        canvas.translate(photo.centerX, photo.centerY);
        canvas.rotate(photo.angle);

        Paint paint = new Paint();
        paint.setARGB(255, 255, 255, 255);
        canvas.drawRect(-w / 2, -h / 2, w / 2, h / 2, paint);

        canvas.drawBitmap(image, -w / 2 + photo.BORDER, -h / 2 + photo.BORDER, null);

        debugger.drawCenterPoint(canvas);
        debugger.printAngle(canvas, photo);
        canvas.restore();
    }

    public void onDraw(Canvas canvas) {
        canvas.setMatrix(mapping.setFromWorld(canvas.getMatrix()));
        renderBackground(canvas);
        for (Photo photo : collection.getPhotos()) {
            renderPhoto(canvas, photo);
        }
        debugger.renderAxis(canvas);
        debugger.renderFingers(canvas);
    }
}
