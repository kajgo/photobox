package com.photobox.files;

import java.util.ArrayList;
import java.io.File;

import java.util.List;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.photobox.R;
import com.photobox.world.Photo;
import com.photobox.world.PhotoCollection;

import android.util.Log;

public class ImportHandler {

    public void importDemoPhotos(PhotoCollection collection, Resources resources) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 16;
        ArrayList<Bitmap> imageList = new ArrayList<Bitmap>();
        imageList.add(BitmapFactory.decodeResource(resources, R.drawable.a, options));
        imageList.add(BitmapFactory.decodeResource(resources, R.drawable.b, options));
        imageList.add(BitmapFactory.decodeResource(resources, R.drawable.c, options));
        imageList.add(BitmapFactory.decodeResource(resources, R.drawable.d, options));
        imageList.add(BitmapFactory.decodeResource(resources, R.drawable.e, options));
        imageList.add(BitmapFactory.decodeResource(resources, R.drawable.f, options));
        imageList.add(BitmapFactory.decodeResource(resources, R.drawable.g, options));
        imageList.add(BitmapFactory.decodeResource(resources, R.drawable.h, options));
        for(int i = 0; i<imageList.size(); i++) {
            collection.addPhoto(new Photo().withBitmap(imageList.get(i)));
        }
    }

    public void importPhotosFromDir(PhotoCollection collection, File dir) {
        Log.d("HEJ HOPP", "Adding files");
        for (File f : photosInDir(dir)) {
            Log.d("HEJ HOPP", "In Loop!");
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 16;
            Bitmap b = BitmapFactory.decodeFile(f.getAbsolutePath(), options);
            collection.addPhoto(new Photo().withBitmap(b));
        }
    }

    private List<File> photosInDir(File dir) {
        ArrayList<File> photoFiles = new ArrayList<File>();
        for (File f : dir.listFiles()) {
            if (isPhoto(f)) {
                Log.d("HEJ HOPP", "Adding file: " + f.getAbsolutePath());
                photoFiles.add(f);
            }
        }
        return photoFiles;
    }

    private boolean isPhoto(File f) {
        return f.getName().toLowerCase().endsWith(".jpg");
    }

}
