package es.karmadev.gamelib.entity;

import es.karmadev.gamelib.pos.AreaPosition;
import es.karmadev.gamelib.pos.Position3D;

/**
 * Represents an entity which has
 * a head, which also usually means
 * the entity has eyes
 */
public interface HeadedEntity {

    /**
     * Default error threshold for head area calculations.
     */
    double DEFAULT_ERROR_THRESHOLD = 0.05d;

    /**
     * Get the entity head size
     *
     * @return the entity head size
     */
    double getHeadSize();

    /**
     * Get the center of the head position
     * for this entity. Please note this
     * method ignores entity rotation, so in
     * some cases, this might return inaccurate
     * results, which should not affect much.
     *
     * @return the entity head center
     * position
     */
    Position3D getHeadCenter();

    /**
     * Get if the position interpolates with
     * the head position
     *
     * @param position the head position
     * @return if the position is inside the
     * head position
     */
    default boolean isInHeadArea(final Position3D position) {
        return isInHeadArea(position, DEFAULT_ERROR_THRESHOLD);
    }

    /**
     * Get if the position interpolates with
     * the head position
     *
     * @param position the head position
     * @param errorThreshold the error threshold
     *                       to allow in the operations
     * @return if the position is inside the
     * head position
     */
    default boolean isInHeadArea(final Position3D position, final double errorThreshold) {
        return isInHeadArea(position, errorThreshold, getHeadSize());
    }

    /**
     * Get if the position interpolates with
     * the head position
     *
     * @param position the head position
     * @param errorThreshold the error threshold
     *                       to allow in the operations
     * @param headSizeOverride the entity head size override
     *                         if any. The default value is {@link #getHeadSize()}
     * @return if the position is inside the
     * head position
     */
    default boolean isInHeadArea(final Position3D position, final double errorThreshold, final double headSizeOverride) {
        Position3D head = getHeadCenter();
        AreaPosition area = head.toArea(headSizeOverride);

        return area.contains(position, errorThreshold);
    }
}
