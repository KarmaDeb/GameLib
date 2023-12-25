package es.karmadev.gamelib.pos;

import es.karmadev.gamelib.math.MathUtils;
import org.bukkit.World;

/**
 * Represents a virtual area
 * based on a position
 */
public final class AreaPosition {

    /**
     * The area position check error threshold
     */
    public final static double DEFAULT_ERROR_THRESHOLD = 0.00000000000000000000000000;

    private final Position3D[] positions;

    private final Position3D center;
    private final double maxX, maxY, maxZ, minX, minY, minZ;

    /**
     * Initialize the area position
     *
     * @param center the position center
     * @param maxX the max X
     * @param maxY the max Y
     * @param maxZ the max Z
     * @param minX the min X
     * @param minY the min Y
     * @param minZ the min Z
     */
    AreaPosition(final Position3D center, final double maxX, final double maxY, final double maxZ,
                 final double minX, final double minY, final double minZ) {
        this.center = center;

        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;

        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;

        int size = calculatePositions();
        this.positions = new Position3D[size];
        mapPositions(center.getWorld());
    }

    /**
     * Get the area contents
     *
     * @return the area contents
     */
    public Position3D[] getContents() {
        return positions.clone();
    }

    /**
     * Get the area center
     *
     * @return the center
     */
    public Position3D getCenter() {
        return center;
    }

    /**
     * Get the area max X position
     *
     * @return the max X position
     */
    public double getMaxX() {
        return maxX;
    }

    /**
     * Get the area max Y position
     *
     * @return the max Y position
     */
    public double getMaxY() {
        return maxY;
    }

    /**
     * Get the area max Z position
     *
     * @return the max Z position
     */
    public double getMaxZ() {
        return maxZ;
    }

    /**
     * Get the area min X position
     *
     * @return the min X position
     */
    public double getMinX() {
        return minX;
    }

    /**
     * Get the area min Y position
     *
     * @return the min Y position
     */
    public double getMinY() {
        return minY;
    }

    /**
     * Get the area min Z position
     *
     * @return the min Z position
     */
    public double getMinZ() {
        return minZ;
    }

    /**
     * Get if the area position contains
     * the specified position
     *
     * @param position the position
     * @return if the area contains the position
     */
    public boolean contains(final Position3D position) {
        return contains(position, DEFAULT_ERROR_THRESHOLD);
    }

    /**
     * Get if the area position contains
     * the specified position
     *
     * @param position the position
     * @param errorThreshold the error threshold
     * @return if the area contains the position
     */
    public boolean contains(final Position3D position, final double errorThreshold) {
        double x = position.getX();
        double y = position.getY();
        double z = position.getZ();

        return checkCollision(x, y, z, errorThreshold);
    }

    /**
     * Get if the area position contains any
     * of the other area position positions
     *
     * @param other the other area position
     * @return if the area position collides with the
     * other position
     */
    public boolean contains(final AreaPosition other) {
        return contains(other, DEFAULT_ERROR_THRESHOLD);
    }

    /**
     * Get if the area position contains any
     * of the other area position positions
     *
     * @param other the other area position
     * @param errorThreshold the error threshold
     * @return if the area position contains the
     * other position
     */
    public boolean contains(final AreaPosition other, final double errorThreshold) {
        double otherXTop = other.getMaxX();
        double otherYTop = other.getMaxY();
        double otherZTop = other.getMaxZ();
        double otherXBot = other.getMinX();
        double otherYBot = other.getMinY();
        double otherZBot = other.getMinZ();

        return (checkCollision(otherXTop, otherYTop, otherZTop, errorThreshold) &&
                checkCollision(otherXBot, otherYBot, otherZBot, errorThreshold));
    }

    /**
     * Get if the area position collides with
     * the other area position
     *
     * @param other the other area position
     * @return if the area position collides with the other
     * position
     */
    public boolean collidesWith(final AreaPosition other) {
        return collidesWith(other, DEFAULT_ERROR_THRESHOLD);
    }

    /**
     * Get if the area position collides with
     * the other area position
     *
     * @param other the other area position
     * @param errorThreshold the error threshold
     * @return if the area position collides with the other
     * position
     */
    public boolean collidesWith(final AreaPosition other, final double errorThreshold) {
        double otherXTop = other.getMaxX();
        double otherYTop = other.getMaxY();
        double otherZTop = other.getMaxZ();
        double otherXBot = other.getMinX();
        double otherYBot = other.getMinY();
        double otherZBot = other.getMinZ();

        return checkCollision(otherXTop, otherYTop, otherZTop, errorThreshold) ||
                checkCollision(otherXBot, otherYBot, otherZBot, errorThreshold) ||
                other.checkCollision(getMaxX(), getMaxY(), getMaxZ(), errorThreshold) ||
                other.checkCollision(getMinX(), getMinY(), getMinZ(), errorThreshold);
    }

    /**
     * Check for collision with coordinates
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     * @param z the Z coordinate
     * @param errorThreshold the error threshold
     * @return if the coordinates collide
     */
    private boolean checkCollision(final double x, final double y, final double z,
                                   final double errorThreshold) {
        double xOffsetTop = maxX + errorThreshold;
        double yOffsetTop = maxY + errorThreshold;
        double zOffsetTop = maxZ + errorThreshold;

        double xOffsetBot = minX - errorThreshold;
        double yOffsetBot = minY - errorThreshold;
        double zOffsetBot = minZ - errorThreshold;

        return x <= xOffsetTop &&
                x >= xOffsetBot &&
                y <= yOffsetTop &&
                y >= yOffsetBot &&
                z <= zOffsetTop &&
                z >= zOffsetBot;
    }

    /**
     * Calculate the area positions
     */
    private int calculatePositions() {
        return MathUtils.calcCubeArea(
                maxX, maxY, maxZ,
                minX, minY, minZ
        );
    }

    /**
     * Map the area positions
     * @param world the world to map for
     */
    private void mapPositions(final World world) {
        int index = 0;
        for (int x = (int) minX; x < (int) maxX; x++) {
            for (int y = (int) minY; y < (int) maxY; y++) {
                for (int z = (int) minZ; z < (int) maxZ; z++) {
                    Position3D position = new Position3D(world, x, y, z);
                    positions[index++] = position.asImmutable();
                }
            }
        }
    }
}