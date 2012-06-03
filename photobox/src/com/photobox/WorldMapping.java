package com.photobox;

import android.graphics.Matrix;

public class WorldMapping {
    
    public float scaleFactor = 1.f;
    public int SCREEN_CETNER_X;
    public int SCREEN_CETNER_Y;

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
        m.preTranslate(-SCREEN_CETNER_X, -SCREEN_CETNER_Y);
        return m;
    }

    public Matrix setFromWorld(Matrix m) {
        m.preTranslate(SCREEN_CETNER_X, SCREEN_CETNER_Y);
        m.preScale(scaleFactor, -scaleFactor);
        return m;
    }

}
