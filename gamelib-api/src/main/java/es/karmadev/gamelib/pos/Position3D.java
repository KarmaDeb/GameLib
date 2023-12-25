package es.karmadev.gamelib.pos;

import es.karmadev.gamelib.math.MathUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Represents a position in
 * a 3d environment. This implementation
 * can also be used as a bukkit
 * {@link Location location}
 */
public class Position3D extends Location {

    private final boolean immutable;

    /**
     * Create a 3D position from a location
     *
     * @param location the location
     * @return the 3d position
     */
    public static Position3D fromLocation(final Location location) {
        return new Position3D(location.getWorld(),
                location.getX(), location.getY(), location.getZ(),
                location.getYaw(), location.getPitch(), false);
    }

    /**
     * Clone a position 3d
     *
     * @param other the position 3d to clone
     *              from
     */
    private Position3D(final Position3D other) {
        this(other.getWorld(), other.getX(), other.getY(), other.getZ(), other.getYaw(), other.getPitch(), other.immutable);
    }

    /**
     * Constructs a new Location with the given coordinates
     *
     * @param world The world in which this location resides
     * @param x     The x-coordinate of this new location
     * @param y     The y-coordinate of this new location
     * @param z     The z-coordinate of this new location
     */
    public Position3D(final @Nullable World world, final double x, final double y, final double z) {
        this(world, x, y, z, 0f, 0f, false);
    }

    /**
     * Constructs a new Location with the given coordinates and direction
     *
     * @param world The world in which this location resides
     * @param x     The x-coordinate of this new location
     * @param y     The y-coordinate of this new location
     * @param z     The z-coordinate of this new location
     * @param yaw   The absolute rotation on the x-plane, in degrees
     * @param pitch The absolute rotation on the y-plane, in degrees
     */
    public Position3D(final @Nullable World world,
                      final double x, final double y, final double z,
                      final float yaw, final float pitch, final boolean immutable) {
        super(world, x, y, z, yaw, pitch);
        this.immutable = immutable;
    }

    /**
     * Get the direction from this position
     * to the desired position
     *
     * @param other the other position
     * @return the direction
     */
    public Vector getDirection(final Position3D other) {
        double x1 = getX();
        double y1 = getY();
        double z1 = getZ();
        double x2 = other.getX();
        double y2 = other.getY();
        double z2 = other.getZ();

        double distance = distance(other);

        double dirX = (x2 - x1) / distance;
        double dirY = (y2 - y1) / distance;
        double dirZ = (z2 - z1) / distance;

        return new Vector(dirX, dirY, dirZ);
    }

    /**
     * Sets the world that this location resides in
     *
     * @param world New world that this location resides in
     */
    @Override
    public void setWorld(@Nullable World world) {
        if (immutable) return;
        super.setWorld(world);
    }

    /**
     * Sets the x-coordinate of this location
     *
     * @param x X-coordinate
     */
    @Override
    public void setX(double x) {
        if (immutable) return;
        super.setX(x);
    }

    /**
     * Sets the y-coordinate of this location
     *
     * @param y y-coordinate
     */
    @Override
    public void setY(double y) {
        if (immutable) return;
        super.setY(y);
    }

    /**
     * Sets the z-coordinate of this location
     *
     * @param z z-coordinate
     */
    @Override
    public void setZ(double z) {
        if (immutable) return;
        super.setZ(z);
    }

    /**
     * Sets the yaw of this location, measured in degrees.
     * <ul>
     * <li>A yaw of 0 or 360 represents the positive z direction.
     * <li>A yaw of 180 represents the negative z direction.
     * <li>A yaw of 90 represents the negative x direction.
     * <li>A yaw of 270 represents the positive x direction.
     * </ul>
     * Increasing yaw values are the equivalent of turning to your
     * right-facing, increasing the scale of the next respective axis, and
     * decreasing the scale of the previous axis.
     *
     * @param yaw new rotation's yaw
     */
    @Override
    public void setYaw(float yaw) {
        if (immutable) return;
        super.setYaw(yaw);
    }

    /**
     * Sets the pitch of this location, measured in degrees.
     * <ul>
     * <li>A pitch of 0 represents level forward facing.
     * <li>A pitch of 90 represents downward facing, or negative y
     *     direction.
     * <li>A pitch of -90 represents upward facing, or positive y direction.
     * </ul>
     * Increasing pitch values the equivalent of looking down.
     *
     * @param pitch new incline's pitch
     */
    @Override
    public void setPitch(float pitch) {
        if (immutable) return;
        super.setPitch(pitch);
    }

    /**
     * Sets the {@link #getYaw() yaw} and {@link #getPitch() pitch} to point
     * in the direction of the vector.
     *
     * @param vector the direction vector
     * @return the same location
     */
    @NotNull
    @Override
    public Position3D setDirection(@NotNull Vector vector) {
        if (immutable) return this;
        return (Position3D) super.setDirection(vector);
    }

    /**
     * Adds the location by another.
     *
     * @param vec The other location
     * @return the same location
     * @throws IllegalArgumentException for differing worlds
     * @see java.util.Vector
     */
    @NotNull
    @Override
    public Position3D add(@NotNull Location vec) {
        if (immutable) return this;
        return (Position3D) super.add(vec);
    }

    /**
     * Adds the location by a vector.
     *
     * @param vec Vector to use
     * @return the same location
     * @see java.util.Vector
     */
    @NotNull
    @Override
    public Position3D add(@NotNull Vector vec) {
        if (immutable) return this;
        return (Position3D) super.add(vec);
    }

    /**
     * Adds the location by another. Not world-aware.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @return the same location
     * @see java.util.Vector
     */
    @NotNull
    @Override
    public Position3D add(double x, double y, double z) {
        if (immutable) return this;
        return (Position3D) super.add(x, y, z);
    }

    /**
     * Subtracts the location by another.
     *
     * @param vec The other location
     * @return the same location
     * @throws IllegalArgumentException for differing worlds
     * @see java.util.Vector
     */
    @NotNull
    @Override
    public Position3D subtract(@NotNull Location vec) {
        if (immutable) return this;
        return (Position3D) super.subtract(vec);
    }

    /**
     * Subtracts the location by a vector.
     *
     * @param vec The vector to use
     * @return the same location
     * @see java.util.Vector
     */
    @NotNull
    @Override
    public Position3D subtract(@NotNull Vector vec) {
        if (immutable) return this;
        return (Position3D) super.subtract(vec);
    }

    /**
     * Subtracts the location by another. Not world-aware and
     * orientation independent.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     * @return the same location
     * @see java.util.Vector
     */
    @NotNull
    @Override
    public Position3D subtract(double x, double y, double z) {
        if (immutable) return this;
        return (Position3D) super.subtract(x, y, z);
    }

    /**
     * Performs scalar multiplication, multiplying all components with a
     * scalar. Not world-aware.
     *
     * @param m The factor
     * @return the same location
     * @see java.util.Vector
     */
    @NotNull
    @Override
    public Position3D multiply(double m) {
        if (immutable) return this;
        return (Position3D) super.multiply(m);
    }

    /**
     * Zero this location's components. Not world-aware.
     *
     * @return the same location
     * @see java.util.Vector
     */
    @NotNull
    @Override
    public Position3D zero() {
        if (immutable) return this;
        return (Position3D) super.zero();
    }

    /**
     * Clone the position as an immutable
     * position
     *
     * @return the immutable position
     */
    public Position3D asImmutable() {
        if (immutable) return this;
        return new Position3D(getWorld(), getX(), getY(), getZ(), getYaw(), getPitch(), true);
    }

    /**
     * Get the interpolated point between
     * this point and another location(s)
     *
     * @param other the mid-point between
     * @param extra the extra locations to calculate mid-point
     *              with. For instance, if two locations specified,
     *              the mid-point would be the center of a triangle
     * @return the mid-point position
     */
    public Position3D interpolateWith(final Location other, final Location... extra) {
        Set<Location> unique = new HashSet<>();
        unique.add(this);
        unique.add(other);
        unique.addAll(Arrays.asList(extra));
        //We use a set, so it handles for us the uniqueness

        int size = unique.size();
        if (size == 1)
            return (Position3D) this.clone();

        double xSum = 0;
        double ySum = 0;
        double zSum = 0;

        for (Location location : unique) {
            xSum += location.getX();
            ySum += location.getY();
            zSum += location.getZ();
        }

        double x = xSum / size;
        double y = ySum / size;
        double z = zSum / size;

        return new Position3D(getWorld(), x, y, z);
    }

    /**
     * Get the area position of the
     * current position
     *
     * @return the area
     */
    public AreaPosition toArea() {
        return toArea(1, 1, 1, -1, -1, -1);
    }

    /**
     * Get the area position of the
     * current position
     *
     * @param areaSize the area size
     * @return the area
     */
    public AreaPosition toArea(final double areaSize) {
        double pos = Math.abs(areaSize / 2);
        double neg = MathUtils.toAbsNegative(areaSize / 2);

        return toArea(pos, pos, pos, neg, neg, neg);
    }

    /**
     * Get the area position of the current
     * position
     *
     * @param areaXSize the area X size
     * @param areaYSize the area Y size
     * @param areaZSize the area Z size
     * @return the area
     */
    public AreaPosition toArea(final double areaXSize, final double areaYSize, final double areaZSize) {
        double posX = Math.abs(areaXSize);
        double posY = Math.abs(areaYSize);
        double posZ = Math.abs(areaZSize);

        double negX = MathUtils.toAbsNegative(areaXSize);
        double negY = MathUtils.toAbsNegative(areaYSize);
        double negZ = MathUtils.toAbsNegative(areaZSize);

        return toArea(posX, posY, posZ, negX, negY, negZ);
    }

    /**
     * Get the area position of the
     * current position
     *
     * @param areaXSize the positive X grow
     * @param areaYSize the positive Y grow
     * @param areaZSize the positive Z grow
     * @param areaXSize2 the negative X grow
     * @param areaYSize2 the negative Y grow
     * @param areaZSize2 the negative Z grow
     * @return the area
     */
    public AreaPosition toArea(final double areaXSize, final double areaYSize, final double areaZSize,
                               final double areaXSize2, final double areaYSize2, final double areaZSize2) {
        return new AreaPosition(this.asImmutable(), areaXSize, areaYSize, areaZSize,
                areaXSize2, areaYSize2, areaZSize2);
    }

    /**
     * Clone the current position
     *
     * @return the cloned position
     */
    @NotNull
    @Override
    public Position3D clone() {
        if (immutable) return this;
        return new Position3D(this);
    }
}
