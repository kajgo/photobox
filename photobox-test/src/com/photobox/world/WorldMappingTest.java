package com.photobox.world;

import com.photobox.renderer.WorldMapping;

import android.test.AndroidTestCase;

public class WorldMappingTest extends AndroidTestCase {

    public void testZoomsAroundOrigin() {
        WorldMapping mapping = new WorldMapping(new Point(50, 50));

        mapping.zoomAround(2, new Point(50, 50));

        assertEquals(mapping.scaleFactor, 2.f);
        assertEquals(mapping.originScreenPosition, new Point(50, 50));
    }

}
