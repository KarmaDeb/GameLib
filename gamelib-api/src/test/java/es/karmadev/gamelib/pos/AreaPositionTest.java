package es.karmadev.gamelib.pos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AreaPositionTest {

    @Test
    void testArea() {
        Position3D position = new Position3D(null, 0, 0, 0);
        AreaPosition area = position.toArea(20);

        assertEquals(area.getMaxX(), 10);
        assertEquals(area.getMaxY(), 10);
        assertEquals(area.getMaxZ(), 10);
        assertEquals(area.getMinX(), -10);
        assertEquals(area.getMinY(), -10);
        assertEquals(area.getMinZ(), -10);

        Position3D[] positions = area.getContents();
        assertNotNull(positions[positions.length - 1]);
    }

    @Test
    void testPosContains() {
        Position3D position = new Position3D(null, 0, 0, 0);
        Position3D other = new Position3D(null, -5, 7, -2);
        AreaPosition area = position.toArea(20);

        assertTrue(area.contains(other));
    }

    @Test
    void testAreaContains() {
        Position3D position = new Position3D(null, 0, 0, 0);
        Position3D other = new Position3D(null, 0, 0, 0);
        AreaPosition area = position.toArea(20);
        AreaPosition otherArea = other.toArea(10);

        assertTrue(area.contains(otherArea));
        assertTrue(area.collidesWith(otherArea));
    }

    @Test
    void testAreaCollides() {
        Position3D position = new Position3D(null, 0, 0, 0);
        Position3D other = new Position3D(null, 0, 0, 0);
        AreaPosition area = position.toArea(20);
        AreaPosition otherArea = other.toArea(40);

        assertFalse(area.contains(otherArea));
        assertTrue(area.collidesWith(otherArea));
    }
}