package com.photobox.renderer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.photobox.world.Photo;
import com.photobox.world.Point;

public class WorldMapping {

    public float scaleFactor = 1.f;
    public Point originScreenPosition = new Point(0, 0);
    public Point screenCenterPoint = new Point(0, 0);

    public WorldMapping(Context context) {
        extractScreenCenter(context);
    }

    public void moveOriginScreenPositionBy(Point offset) {
        originScreenPosition = originScreenPosition.plus(offset);
    }

    public Point toWorld(Point point) {
        Matrix m = new Matrix();
        m = setToWorld(m);

        float[] dst = new float[] { 0, 0 };
        float[] src = new float[] { point.x, point.y };
        m.mapPoints(dst, src);
        float newX = dst[0];
        float newY = dst[1];

        return new Point(newX, newY);
    }

    public Matrix setToWorld(Matrix m) {
        m.preScale(1/scaleFactor, -1/scaleFactor);
        m.preTranslate(-originScreenPosition.x, -originScreenPosition.y);
        return m;
    }

    public Matrix setFromWorld(Matrix m) {
        m.preTranslate(originScreenPosition.x, originScreenPosition.y);
        m.preScale(scaleFactor, -scaleFactor);
        return m;
    }

    public void setPhotoToViewportTransformation(Canvas c, Photo p) {
        c.translate(p.centerX, p.centerY);
        c.rotate(p.angle);
        c.scale(1, -1);
    }

    public void setWorldToScreenTransformation(Canvas c) {
        c.setMatrix(setFromWorld(c.getMatrix()));
    }

    public void reset() {
        scaleFactor = 1.f;
        originScreenPosition = screenCenterPoint;
    }

    public void extractScreenCenter(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        originScreenPosition = new Point(metrics.widthPixels / 2, metrics.heightPixels / 2);
        screenCenterPoint = originScreenPosition;
    }

}
