package es.karmadev.gamelib.entity;

import es.karmadev.gamelib.LibSerializable;
import es.karmadev.gamelib.entity.human.HumanPlayer;
import es.karmadev.gamelib.math.MathUtils;
import es.karmadev.gamelib.pos.AreaPosition;
import es.karmadev.gamelib.pos.Position3D;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a GameLib entity. This
 * represents a generic entity, which
 * can be any kind
 */
public abstract class EngineEntity implements LibSerializable<EngineEntity> {

    protected String name;
    protected UUID uniqueId;

    /**
     * Create a new engine entity
     *
     * @param name the entity name
     * @param uniqueId the entity unique ID
     */
    public EngineEntity(final String name, final UUID uniqueId) {
        this.name = name;
        this.uniqueId = uniqueId;
    }

    /**
     * Get the entity ID
     *
     * @return the entity ID
     */
    public abstract int getId();

    /**
     * Get the entity name
     *
     * @return the entity name
     */
    public final String getName() {
        return name;
    }

    /**
     * Get the entity unique ID
     *
     * @return the entity unique ID
     */
    public final UUID getUniqueId() {
        return uniqueId;
    }

    /**
     * Get if the entity has the default
     * game AI enabled. This is usually
     * set to false on {@link NPCEntity} and
     * always false on {@link HumanPlayer}
     *
     * @return if the entity has AI
     */
    public abstract boolean hasAI();

    /**
     * Get the entity position
     *
     * @return the entity position
     */
    public abstract Position3D getPosition();

    /**
     * Get the entity world
     *
     * @return the entity world
     */
    public abstract World getWorld();

    /**
     * Move the entity to the specified
     * position
     *
     * @param other the position to move to
     * @return if the movement was allowed
     */
    public abstract boolean moveTo(final Location other);

    /**
     * Move the entity to another
     * entity position
     *
     * @param other the other entity
     * @return if the movement was allowed
     */
    public boolean moveWith(final EngineEntity other) {
        if (other == null || other.equals(this)) return false;

        Position3D otherPos = other.getPosition();
        World world = otherPos.getWorld();
        if (world == null) return false;

        return moveTo(otherPos);
    }

    /**
     * Swap positions between this entity
     * and the other one, meaning the other
     * position will be on the current's entity
     * position, and the current position will
     * be the other's entity position.
     *
     * @param other the entity to swap positions
     *              with
     * @return if the swap was allowed
     */
    public boolean swapPositions(final EngineEntity other) {
        if (other == null || other.equals(this)) return false;

        Position3D current = getPosition();
        Position3D otherPos = other.getPosition();

        World world = otherPos.getWorld();
        if (world == null) return false;

        if (other.moveTo(current)) {
            if (!this.moveTo(otherPos)) {
                if (!other.moveTo(otherPos)) { //Tries to reset position as nothing has happened
                    //TODO: Log a warning about the situation
                }

                return false;
            }

            return true;
        }

        return false;
    }

    /**
     * Get if the entity is able to see
     * the other entity, regardless of the
     * position the entity is looking at.
     * How it works?
     * Unlike {@link EngineLivingEntity#canSeeDirectly(EngineEntity)}, this method
     * will ignore the pitch and yaw of the entity, and
     * instead will simply calculate the direction
     * based on both entity positions. This method
     * might be faster on the first step, rather than
     * the {@link EngineLivingEntity#canSeeDirectly(EngineEntity)} method, but
     * at the end it performs the same operation
     *
     * @param other the other entity
     * @return if the entity can see the other
     * entity
     */
    public boolean canSee(final EngineEntity other) {
        return canSee(other, 1, 1, 1, -1, -1, -1);
    }

    /**
     * Get if the entity is able to see
     * the other entity, regardless of the
     * position the entity is looking at.
     * How it works?
     * Unlike {@link EngineLivingEntity#canSeeDirectly(EngineEntity)}, this method
     * will ignore the pitch and yaw of the entity, and
     * instead will simply calculate the direction
     * based on both entity positions. This method
     * might be faster on the first step, rather than
     * the {@link EngineLivingEntity#canSeeDirectly(EngineEntity)} method, but
     * at the end it performs the same operation. The default
     * raytrace area is of 1x1x1 (one block).
     *
     * @param other the other entity
     * @param rayAreaOffset the ray area offset
     * @return if the entity can see the other
     * entity
     */
    public boolean canSee(final EngineEntity other,
                          final double rayAreaOffset) {
        double pos = Math.abs(rayAreaOffset);
        double neg = MathUtils.toAbsNegative(rayAreaOffset);

        return canSee(other, pos, pos, pos, neg, neg, neg);
    }

    /**
     * Get if the entity is able to see
     * the other entity, regardless of the
     * position the entity is looking at.
     * How it works?
     * Unlike {@link EngineLivingEntity#canSeeDirectly(EngineEntity)}, this method
     * will ignore the pitch and yaw of the entity, and
     * instead will simply calculate the direction
     * based on both entity positions. This method
     * might be faster on the first step, rather than
     * the {@link EngineLivingEntity#canSeeDirectly(EngineEntity)} method, but
     * at the end it performs the same operation. The default
     * raytrace area is of 1x1x1 (one block).
     *
     * @param other the other entity
     * @param rayAreaXOffset the raytrace X area offset
     * @param rayAreaYOffset the raytrace Y area offset
     * @param rayAreaZOffset the raytrace Z area offset
     * @return if the entity can see the other
     * entity
     */
    public boolean canSee(final EngineEntity other,
                          final double rayAreaXOffset, final double rayAreaYOffset, final double rayAreaZOffset) {
        double posX = Math.abs(rayAreaXOffset);
        double posY = Math.abs(rayAreaYOffset);
        double posZ = Math.abs(rayAreaZOffset);

        double negX = MathUtils.toAbsNegative(rayAreaXOffset);
        double negY = MathUtils.toAbsNegative(rayAreaYOffset);
        double negZ = MathUtils.toAbsNegative(rayAreaZOffset);

        return canSee(other, posX, posY, posZ, negX, negY, negZ);
    }

    /**
     * Get if the entity is able to see
     * the other entity, regardless of the
     * position the entity is looking at.
     * How it works?
     * Unlike {@link EngineLivingEntity#canSeeDirectly(EngineEntity)}, this method
     * will ignore the pitch and yaw of the entity, and
     * instead will simply calculate the direction
     * based on both entity positions. This method
     * might be faster on the first step, rather than
     * the {@link EngineLivingEntity#canSeeDirectly(EngineEntity)} method, but
     * at the end it performs the same operation. The default
     * raytrace area is of 1x1x1 (one block).
     *
     * @param otherEntity the other entity
     * @param rayAreaXOffset the raytrace X area offset
     * @param rayAreaYOffset the raytrace Y area offset
     * @param rayAreaZOffset the raytrace Z area offset
     * @param rayAreaXOffset2 the raytrace X area offset 2
     * @param rayAreaYOffset2 the raytrace Y area offset 2
     * @param rayAreaZOffset2 the raytrace Z area offset 2
     * @return if the entity can see the other
     * entity
     */
    public boolean canSee(final EngineEntity otherEntity,
                          final double rayAreaXOffset, final double rayAreaYOffset, final double rayAreaZOffset,
                          final double rayAreaXOffset2, final double rayAreaYOffset2, final double rayAreaZOffset2) {
        Position3D current = getPosition();
        Position3D other = otherEntity.getPosition();

        double x1 = current.getX();
        double y1 = current.getY();
        double z1 = current.getZ();
        double x2 = other.getX();
        double y2 = other.getY();
        double z2 = other.getZ();

        double distance = current.distance(other);
        if (distance <= 1) return true;

        double dirX = (x2 - x1) / distance;
        double dirY = (y2 - y1) / distance;
        double dirZ = (z2 - z1) / distance;

        iterator:
        for (double i = 0; i < distance; i += 0.1) {
            double x = (x1 + dirX) * i;
            double y = (y1 + dirY) * i;
            double z = (z1 + dirZ) * i;

            Position3D pos = new Position3D(null, x, y, z);
            AreaPosition area = pos.toArea(rayAreaXOffset, rayAreaYOffset, rayAreaZOffset,
                    rayAreaXOffset2, rayAreaYOffset2, rayAreaZOffset2);

            boolean isBlocked = true;
            for (Position3D container : area.getContents()) {
                Block block = container.getBlock();
                Material type = block.getType();

                if (type.isBlock() && type.isSolid()) {
                    if (!type.isInteractable() && type.isOccluding())
                        continue iterator;
                }

                if (type.isAir() || !type.isOccluding() ||
                    type.isInteractable() || type.equals(Material.WATER) ||
                    type.equals(Material.LAVA)) {
                    isBlocked = false;
                    break;
                }
            }

            if (isBlocked) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link HashMap}.
     * <p>
     * The general contract of {@code hashCode} is:
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during
     *     an execution of a Java application, the {@code hashCode} method
     *     must consistently return the same integer, provided no information
     *     used in {@code equals} comparisons on the object is modified.
     *     This integer need not remain consistent from one execution of an
     *     application to another execution of the same application.
     * <li>If two objects are equal according to the {@link
     *     #equals(Object) equals} method, then calling the {@code
     *     hashCode} method on each of the two objects must produce the
     *     same integer result.
     * <li>It is <em>not</em> required that if two objects are unequal
     *     according to the {@link #equals(Object) equals} method, then
     *     calling the {@code hashCode} method on each of the two objects
     *     must produce distinct integer results.  However, the programmer
     *     should be aware that producing distinct integer results for
     *     unequal objects may improve the performance of hash tables.
     * </ul>
     *
     * @return a hash code value for this object.
     * @implSpec As far as is reasonably practical, the {@code hashCode} method defined
     * by class {@code Object} returns distinct integers for distinct objects.
     * @see Object#equals(Object)
     * @see System#identityHashCode
     */
    @Override
    public final int hashCode() {
        return Objects.hashCode(uniqueId);
    }
}