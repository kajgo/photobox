package com.photobox.world;

import com.photobox.renderer.WorldMapping;

import android.test.AndroidTestCase;

public class WorldMappingTest extends AndroidTestCase {

    public void testZoomsAroundOrigin() {
        WorldMapping mapping = new WorldMapping(new Point(50, 50));

        mapping.zoomAround(2, new Point(50, 50));

        assertEquals(2.f, mapping.scaleFactor);
        assertEquals(new Point(50, 50), mapping.originScreenPosition);
    }

    public void testZoomsAroundADifferentPointFromOrigin() {
        WorldMapping mapping = new WorldMapping(new Point(50, 50));
        mapping.scaleFactor = 1;

        mapping.zoomAround(2, new Point(100, 100));

        assertEquals(2.f, mapping.scaleFactor);
        assertEquals(new Point(0, 0), mapping.originScreenPosition);
    }
}
