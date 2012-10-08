package com.photobox.files;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import com.photobox.R;

import com.photobox.files.ResolutionLadder;

import com.photobox.queues.MultiplePopQueue;
import com.photobox.world.Photo;
import com.photobox.world.PhotoCollection;

import android.content.res.Resources;

public class ImportHandler {

    private ResolutionLadder ladder;

    public ImportHandler(ResolutionLadder ladder) {
        this.ladder = ladder;
    }

    public void importDemoPhotos(PhotoCollection collection, Resources resources) {
        List<Photo> photos = new ArrayList<Photo>();
        for (Integer which : photosInResources()) {
            ResourceBitmapLoader loader = new ResourceBitmapLoader(resources, which);
            photos.add(loadPhoto(loader, collection));
        }
        ladder.fillFrom(new MultiplePopQueue<Photo>(photos));
    }

    public void importPhotosFromDir(PhotoCollection collection, File dir) {
        List<Photo> photos = new ArrayList<Photo>();
        for (File f : photosInDir(dir)) {
            FileBitmapLoader loader = new FileBitmapLoader(f);
            photos.add(loadPhoto(loader, collection));
        }
        ladder.fillFrom(new MultiplePopQueue<Photo>(photos));
    }

    public static boolean hasPhotos(File dir) {
        return photosInDir(dir).size() > 0;
    }

    private static List<File> photosInDir(File dir) {
        ArrayList<File> photoFiles = new ArrayList<File>();
        for (File f : dir.listFiles()) {
            if (isPhoto(f)) {
                photoFiles.add(f);
            }
        }
        return photoFiles;
    }

    private static boolean isPhoto(File f) {
        return f.getName().toLowerCase().endsWith(".jpg")
            || f.getName().toLowerCase().endsWith(".jpeg")
            || f.getName().toLowerCase().endsWith(".png");
    }

    private Photo loadPhoto(BitmapLoader loader, PhotoCollection collection) {
        BitmapSize size = loader.getBitmapSize();
        Photo p = new Photo().withSize(size.width, size.height);
        collection.addPhoto(p);
        p.bitmapLoader = loader;
        return p;
    }

    private List<Integer> photosInResources() {
        List<Integer> imageList = new ArrayList<Integer>();
        imageList.add(R.drawable.a);
        imageList.add(R.drawable.b);
        imageList.add(R.drawable.c);
        imageList.add(R.drawable.d);
        imageList.add(R.drawable.e);
        return imageList;
    }
}
