package com.photobox.world;

import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;

public class PhotoCollectionTest extends AndroidTestCase {

    public void testContainsPhotos() {
        PhotoCollection collection = new PhotoCollection();
        Photo p = new Photo();
        collection.addPhoto(p);

        List<Photo> l = new ArrayList<Photo>();
        l.add(p);
        assertEquals(l, collection.getPhotos());
    }

    public void testRemembersWhichPhotoIsActive_isNullWhenNoPhotoActivated() {
        PhotoCollection collection = new PhotoCollection();
        Photo p = new Photo();
        collection.addPhoto(p);

        assertNull(collection.getActive());
    }

    public void testRemembersWhichPhotoIsActive_isThePhotoWhichWasPressed() {
        PhotoCollection collection = new PhotoCollection();
        Photo p = new Photo().withCenterAt(0, 0).withSize(10, 10);
        collection.addPhoto(p);
        collection.fingerDown(new Point(0, 0));

        assertEquals(p, collection.getActive());
    }

    public void testRemembersWhichPhotoIsActive_isNullIfPressingOutsiedeAllPhotos() {
        PhotoCollection collection = new PhotoCollection();
        Photo p = new Photo().withCenterAt(0, 0).withSize(10, 10);
        collection.addPhoto(p);
        collection.fingerDown(new Point(0, 0));
        collection.fingerDown(new Point(100, 100));

        assertNull(collection.getActive());
    }

    public void testFingerDownLocatesTopmostPhoto() {
        PhotoCollection collection = new PhotoCollection();
        Photo bottommostPhoto = new Photo().withCenterAt(0, 0).withSize(10, 10);
        Photo topmostPhoto = new Photo().withCenterAt(0, 0).withSize(10, 10);
        collection.addPhoto(bottommostPhoto);
        collection.addPhoto(topmostPhoto);

        collection.fingerDown(new Point(0, 0));
        assertEquals(collection.getActive(), topmostPhoto);
    }

    public void testSetActivePhotoOnTop() {
        PhotoCollection collection = new PhotoCollection();
        Photo bottommostPhoto = new Photo().withCenterAt(0, 0).withSize(10, 10);
        Photo topmostPhoto = new Photo().withCenterAt(5, 0).withSize(10, 10);
        collection.addPhoto(bottommostPhoto);
        collection.addPhoto(topmostPhoto);

        collection.fingerDown(new Point(-2, 0));
        assertEquals(collection.getActive(), bottommostPhoto);
        collection.fingerDown(new Point(2, 0));
        assertEquals(collection.getActive(), bottommostPhoto);
    }
}
