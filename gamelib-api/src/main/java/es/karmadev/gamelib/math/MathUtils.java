package es.karmadev.gamelib.math;

import java.util.function.Function;

/**
 * Math utilities
 */
public final class MathUtils {

    /**
     * Get the negative value of the
     * provided number. If the number is
     * already negative, do not make any
     * conversion
     *
     * @param number the number to converse
     * @return the negative number
     */
    public static byte toAbsNegative(final byte number) {
        return absNegative(number, Double::byteValue);
    }

    /**
     * Get the negative value of the
     * provided number. If the number is
     * already negative, do not make any
     * conversion
     *
     * @param number the number to converse
     * @return the negative number
     */
    public static short toAbsNegative(final short number) {
        return absNegative(number, Double::shortValue);
    }

    /**
     * Get the negative value of the
     * provided number. If the number is
     * already negative, do not make any
     * conversion
     *
     * @param number the number to converse
     * @return the negative number
     */
    public static int toAbsNegative(final int number) {
        return absNegative(number, Double::intValue);
    }

    /**
     * Get the negative value of the
     * provided number. If the number is
     * already negative, do not make any
     * conversion
     *
     * @param number the number to converse
     * @return the negative number
     */
    public static long toAbsNegative(final long number) {
        return absNegative(number, Double::longValue);
    }

    /**
     * Get the negative value of the
     * provided number. If the number is
     * already negative, do not make any
     * conversion
     *
     * @param number the number to converse
     * @return the negative number
     */
    public static float toAbsNegative(final float number) {
        return absNegative(number, Double::floatValue);
    }

    /**
     * Get the negative value of the
     * provided number. If the number is
     * already negative, do not make any
     * conversion
     *
     * @param number the number to converse
     * @return the negative number
     */
    public static double toAbsNegative(final double number) {
        return absNegative(number, d -> d);
    }

    /**
     * Get the distance from a point to the
     * center point of a sphere
     *
     * @param centerX the sphere center X
     * @param centerY the sphere center Y
     * @param centerZ the sphere center Z
     * @param currentX the target X
     * @param currentY the target Y
     * @param currentZ the target Z
     * @return the distance from the center
     * to the target
     */
    public static double calcDistToCenter(final double centerX, final double centerY, final double centerZ,
                                          final double currentX, final double currentY, final double currentZ) {
        double x = Math.pow(currentX - centerX, 2);
        double y = Math.pow(currentY - centerY, 2);
        double z = Math.pow(currentZ - centerZ, 2);

        return Math.sqrt(x + y + z);
    }

    /**
     * Calculate the area of a cube. The
     * area is represented on the number
     * of cubes which forms the final cube.
     * The coordinates should represent
     * a top left corner and bottom right corner
     * (or vice versa)
     *
     * @param x1 corner coordinate 1 X
     * @param y1 corner coordinate 1 Y
     * @param z1 corner coordinate 1 Z
     * @param x2 corner coordinate 2 X
     * @param y2 corner coordinate 2 Y
     * @param z2 corner coordinate 2 Z
     * @return the cube area
     */
    public static int calcCubeArea(final double x1, final double y1, final double z1,
                                      final  double x2, final double y2, final double z2) {
        double areaX = (x1 - x2);
        double areaY = (y1 - y2);
        double areaZ = (z1 - z2);

        return (int) (areaX * areaY * areaZ);
    }

    /**
     * Calculate the area of a square. The
     * area is represented in the number
     * of squares which forms the final square.
     * The coordinates should represent
     * a front left corner and back right corner
     * (or vice versa)
     *
     * @param x1 corner coordinate 1 X
     * @param z1 corner coordinate 1 Z
     * @param x2 corner coordinate 2 X
     * @param z2 corner coordinate 2 Z
     * @return the cube area
     */
    public static int calcSquareArea(final double x1, final double z1,
                                   final  double x2, final double z2) {
        double areaX = (x1 - x2);
        double areaZ = (z1 - z2);

        return (int) (areaX * areaZ);
    }

    /**
     * Calculate the closest direction to
     * the target
     *
     * @param currentDirection the current direction
     * @param targetDirection the target direction
     * @return the closest rotation direction
     */
    public static double calcRotation(final double currentDirection, final double targetDirection) {
        double angleDiff = normalizeAngle(targetDirection - currentDirection);
        double complement = 360 - angleDiff;

        return (angleDiff < complement ? angleDiff : -complement);
    }

    /**
     * Normalize an angle to convert it
     * to ranges of 180 and 0 or -180 and 0
     *
     * @param angle the angle
     * @return the normalized angle
     */
    public static double normalizeAngle(final double angle) {
        return (angle % 360 + 360) % 360;
    }

    /**
     * Get the absolute negative value of
     * a number
     *
     * @param number the number
     * @param numCreator the expected number
     *                   provider
     * @return the negative number
     * @param <T> the number type
     */
    private static <T extends Number> T absNegative(final T number, final Function<Double, T> numCreator) {
        if (number.doubleValue() > 0) {
            return numCreator.apply(-number.doubleValue());
        }

        return numCreator.apply(number.doubleValue());
    }
}
