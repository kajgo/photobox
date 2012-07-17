package com.photobox.files;

public class BitmapSize {

    public final int width;
    public final int height;

    public BitmapSize(int w, int h) {
        width = w;
        height = h;
    }

    public BitmapSize scale(float factor) {
        return new BitmapSize(
            (int)Math.round(width * factor),
            (int)Math.round(height * factor));
    }

    public int max() {
        return Math.max(width, height);
    }

    public int min() {
        return Math.min(width, height);
    }

}
