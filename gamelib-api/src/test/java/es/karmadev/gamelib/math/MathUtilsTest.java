package es.karmadev.gamelib.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathUtilsTest {

    @Test
    void testRotation() {
        double currentDirection = 45;
        double targetDirection = 300;

        double rotation = MathUtils.calcRotation(currentDirection, targetDirection);
        assertTrue(rotation < 0);
    }

    @Test
    void testRotation2() {
        double currentDirection = 300;
        double targetDirection = 45;

        double rotation = MathUtils.calcRotation(currentDirection, targetDirection);
        assertTrue(rotation > 0);
    }

    @Test
    void testRotation3() {
        double currentDirection = 360;
        double targetDirection = 360;

        double rotation = MathUtils.calcRotation(currentDirection, targetDirection);
        assertEquals(0, rotation);
    }
}