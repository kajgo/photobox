package com.photobox.test;

import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;

import com.photobox.world.*;

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

}
