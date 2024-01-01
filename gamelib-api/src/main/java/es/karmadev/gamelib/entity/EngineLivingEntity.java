package es.karmadev.gamelib.entity;

import es.karmadev.gamelib.math.MathUtils;
import es.karmadev.gamelib.pos.AreaPosition;
import es.karmadev.gamelib.pos.Position3D;
import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * Represents a GameLib entity. This
 * represents a generic entity, which
 * can be any kind
 */
public interface EngineLivingEntity extends EngineEntity, HeadedEntity {

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
     * @param other the other entity
     * @return if the entity can see the other
     * entity
     */
    default boolean canSeeDirectly(final EngineEntity other) {
        return canSeeDirectly(other, 1, 1, 1, -1, -1, -1);
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
     * @param other the other entity
     * @param rayAreaOffset the ray area offset
     * @return if the entity can see the other
     * entity
     */
    default boolean canSeeDirectly(final EngineEntity other,
                          final double rayAreaOffset) {
        double pos = Math.abs(rayAreaOffset);
        double neg = MathUtils.toAbsNegative(rayAreaOffset);

        return canSeeDirectly(other, pos, pos, pos, neg, neg, neg);
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
     * @param other the other entity
     * @param rayAreaXOffset the raytrace X area offset
     * @param rayAreaYOffset the raytrace Y area offset
     * @param rayAreaZOffset the raytrace Z area offset
     * @return if the entity can see the other
     * entity
     */
    default boolean canSeeDirectly(final EngineEntity other,
                          final double rayAreaXOffset, final double rayAreaYOffset, final double rayAreaZOffset) {
        double posX = Math.abs(rayAreaXOffset);
        double posY = Math.abs(rayAreaYOffset);
        double posZ = Math.abs(rayAreaZOffset);

        double negX = MathUtils.toAbsNegative(rayAreaXOffset);
        double negY = MathUtils.toAbsNegative(rayAreaYOffset);
        double negZ = MathUtils.toAbsNegative(rayAreaZOffset);

        return canSeeDirectly(other, posX, posY, posZ, negX, negY, negZ);
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
    default boolean canSeeDirectly(final EngineEntity otherEntity,
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

        double yaw = Math.toRadians(current.getYaw());
        double pitch = Math.toRadians(current.getPitch());

        double cosYaw = Math.cos(-yaw);
        double sinYaw = Math.sin(-yaw);
        double cosPitch = Math.cos(-pitch);
        double sinPitch = Math.sin(-pitch);

        iterator:
        for (double i = 0; i < distance; i += 0.1) {
            double x = (x1 + dirX * i);
            double y = (y1 + dirY * i);
            double z = (z1 + dirZ * i);

            double rotatedX = cosYaw * (x - x1) - sinYaw * (z - z1) + x1;
            double rotatedZ = sinYaw * (x - x1) + cosYaw * (z - z1) + z1;

            double rotatedY = cosPitch * (y - y1) - sinPitch * (rotatedZ - z1) + y1;
            rotatedZ = sinPitch * (y - y1) + cosPitch * (rotatedZ - z1) + z1;

            Position3D pos = new Position3D(null, rotatedX, rotatedY, rotatedZ);
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
}