package com.photobox.files;

import java.util.ArrayList;
import java.io.File;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.photobox.R;
import com.photobox.world.Photo;
import com.photobox.world.PhotoCollection;

public class ImportHandler {

    public void importPhotos(PhotoCollection collection, Resources resources) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
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

    public void importPhotosFromFiles(PhotoCollection collection, File[] photos) {
        for (File f : photos) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 16;
            Bitmap b = BitmapFactory.decodeFile(f.getAbsolutePath(), options);
            collection.addPhoto(new Photo().withBitmap(b));
        }
    }

}
