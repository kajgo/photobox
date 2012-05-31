package com.photobox.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.photobox.Photo;
import com.photobox.PhotoCollection;

public class PhotoCollectionTest {

    @Test
    public void containsPhotos() {
        PhotoCollection collection = new PhotoCollection();
        Photo p = new Photo();
        collection.addPhoto(p);

        List<Photo> l = new ArrayList<Photo>();
        l.add(p);
        assertEquals(l, collection.getPhotos());
    }

}
