package com.photobox.test;

import android.test.AndroidTestCase;

import com.photobox.Photo;

public class PhotoTest extends AndroidTestCase {

    public void testKnowsIfAPointIsInsideOfIt() {
        Photo p = new Photo(10, 10, null);
        p.centerX = 0;
        p.centerY = 0;
        assertTrue(p.pointInside(0, 0));
        assertFalse(p.pointInside(0, 100));
    }

    public void testKnowsIfAPointIsInsideOfItWhenRotated() {
        Photo p = new Photo(100, 10, null);
        p.centerX = 0;
        p.centerY = 0;
        p.angle = 90;
        assertTrue(p.pointInside(0, 25));
        assertFalse(p.pointInside(25, 0));
    }

    public void testKnowsIfAPointIsInsideOfItWhenRotatedNotSoEasy() {
        Photo p = new Photo(100, 10, null);
        p.centerX = 0;
        p.centerY = 0;
        p.angle = -45;
        assertTrue(p.pointInside(-25, 25));
        assertFalse(p.pointInside(25, 0));
    }
}
