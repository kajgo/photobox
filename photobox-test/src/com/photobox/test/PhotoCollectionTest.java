package com.photobox.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.photobox.Photo;
import com.photobox.PhotoCollection;

public class PhotoCollectionTest {

    @Test
    public void containsPhotos() {
        PhotoCollection collection = new PhotoCollection();
        Photo p = new Photo(0, 0, null);
        collection.addPhoto(p);

        List<Photo> l = new ArrayList<Photo>();
        l.add(p);
        assertEquals(l, collection.getPhotos());
    }

    @Test
    public void remembersWhichPhotoIsActive_isNullWhenNoPhotoActivated() {
        PhotoCollection collection = new PhotoCollection();
        Photo p = new Photo(0, 0, null);
        collection.addPhoto(p);

        assertNull(collection.getActive());
    }

    @Test
    public void remembersWhichPhotoIsActive_isThePhotoWhichWasPressed() {
        PhotoCollection collection = new PhotoCollection();
        Photo p = new Photo(10, 10, null);
        p.centerX = 0;
        p.centerY = 0;
        collection.addPhoto(p);
        collection.fingerDown(0, 0);

        assertEquals(p, collection.getActive());
    }

    @Test
    public void remembersWhichPhotoIsActive_isNullIfPressingOutsiedeAllPhotos() {
        PhotoCollection collection = new PhotoCollection();
        Photo p = new Photo(10, 10, null);
        p.centerX = 0;
        p.centerY = 0;
        collection.addPhoto(p);
        collection.fingerDown(0, 0);
        collection.fingerDown(100, 100);

        assertNull(collection.getActive());
    }

}
