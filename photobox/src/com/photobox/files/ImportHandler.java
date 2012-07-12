package com.photobox.files;

import java.util.ArrayList;
import java.io.File;

import java.util.List;

import com.photobox.files.BitmapCache;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.photobox.R;
import com.photobox.world.Photo;
import com.photobox.world.PhotoCollection;

public class ImportHandler {

    private BitmapCache bitmapCache;

    public ImportHandler(BitmapCache bitmapCache) {
        this.bitmapCache = bitmapCache;
    }

    public void importDemoPhotos(PhotoCollection collection, Resources resources) {
        List<Integer> imageList = new ArrayList<Integer>();
        imageList.add(R.drawable.a);
        imageList.add(R.drawable.b);
        imageList.add(R.drawable.c);
        imageList.add(R.drawable.d);
        imageList.add(R.drawable.e);
        Photo latestPhoto = new Photo();
        for (Integer which : imageList) {
            ResourceBitmapLoader loader = new ResourceBitmapLoader(resources, which);
            BitmapSize size = loader.getBitmapSize();
            Photo p = new Photo().withSize(size.width, size.height);
            collection.addPhoto(p);
            bitmapCache.add(p, loader);
            latestPhoto = p;
        }
        bitmapCache.setHighRes(latestPhoto);
    }

    public void importPhotosFromDir(PhotoCollection collection, File dir) {
        Photo latestPhoto = new Photo();
        for (File f : photosInDir(dir)) {
            FileBitmapLoader loader = new FileBitmapLoader(f);
            BitmapSize size = loader.getBitmapSize();
            Photo p = new Photo().withSize(size.width, size.height);
            collection.addPhoto(p);
            bitmapCache.add(p, loader);
        }
        bitmapCache.setHighRes(latestPhoto);
    }

    private List<File> photosInDir(File dir) {
        ArrayList<File> photoFiles = new ArrayList<File>();
        for (File f : dir.listFiles()) {
            if (isPhoto(f)) {
                photoFiles.add(f);
            }
        }
        return photoFiles;
    }

    private boolean isPhoto(File f) {
        return f.getName().toLowerCase().endsWith(".jpg");
    }

}
