package es.karmadev.gamelib.entity;

import es.karmadev.gamelib.pos.Position3D;
import org.bukkit.World;

import java.util.UUID;

/**
 * Represents a GameLib entity. This
 * represents a generic entity, which
 * can be any kind
 */
public abstract class EngineEntity {

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
     * Move the entity to the specified
     * position
     *
     * @param other the position to move to
     * @return if the movement was allowed
     */
    public abstract boolean moveTo(final Position3D other);

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
     * the other entity directly.
     * How it works?
     * It takes the current entity position and
     * the facing direction (yaw and pitch), and finds
     * the interpolation direction between to create a
     * virtual raytrace to the entity. If the raytrace
     * collides with a solid non-translucent block, the
     * operation will fail.
     *
     * @param other the entity to check with
     * @return if the entity can directly see the other entity
     */
    public boolean canSeeDirectly(final EngineEntity other) {
        Position3D position = getPosition();
        float yaw = position.getYaw();
        float pitch = position.getPitch();

        //TODO: Logic
        return false;
    }

    /**
     * Get if the entity is able to see
     * the other entity, regardless of the
     * position the entity is looking at.
     * How it works?
     * Unlike {@link #canSeeDirectly(EngineEntity)}, this method
     * will ignore the pitch and yaw of the entity, and
     * instead will simply calculate the direction
     * based on both entity positions. This method
     * might be faster on the first step, rather than
     * the {@link #canSeeDirectly(EngineEntity)} method, but
     * at the end it performs the same operation
     *
     * @param other the other entity
     * @return if the entity can see the other
     * entity
     */
    public boolean canSee(final EngineEntity other) {
        //TODO: Logic
        return false;
    }
}