package com.photobox.world;

import android.test.AndroidTestCase;

public class PhotoTest extends AndroidTestCase {

    public void testKnowsIfAPointIsInsideOfIt() {
        Photo p = new Photo().withCenterAt(0, 0) .withSize(10, 10);
        assertTrue(p.pointInside(new Point(0, 0)));
        assertFalse(p.pointInside(new Point(0, 100)));
    }

    public void testKnowsIfAPointIsInsideOfItWhenRotated() {
        Photo p = new Photo().withCenterAt(0, 0).withSize(100, 10).withAngle(90);
        assertTrue(p.pointInside(new Point(0, 25)));
        assertFalse(p.pointInside(new Point(25, 0)));
    }

    public void testKnowsIfAPointIsInsideOfItWhenRotatedNotSoEasy() {
        Photo p = new Photo().withCenterAt(0, 0).withSize(100, 10).withAngle(-45);
        assertTrue(p.pointInside(new Point(-25, 25)));
        assertFalse(p.pointInside(new Point(25, 0)));
    }
}
