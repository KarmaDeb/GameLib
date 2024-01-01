package es.karmadev.gamelib.plugin.impl.entity;

import es.karmadev.gamelib.entity.EngineLivingEntity;
import es.karmadev.gamelib.plugin.GameLibImpl;
import es.karmadev.gamelib.pos.Position3D;
import org.bukkit.Location;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class GameLivingEntity extends GameEntity implements EngineLivingEntity {

    private final GameLibImpl lib;
    private final LivingEntity entity;

    /**
     * Create a new engine entity
     *
     * @param entity the entity representing
     *               the engine entity
     */
    public GameLivingEntity(final GameLibImpl lib, final LivingEntity entity) {
        super(lib, entity);
        this.lib = lib;
        this.entity = entity;

        lib.addEntity(this);
    }

    /**
     * Get the entity head width
     *
     * @return the entity head width
     */
    @Override
    public double getHeadWidth() {
        EntityType type = entity.getType();
        double mod = 0;

        if (entity instanceof Ageable) {
            Ageable aged = (Ageable) entity;
            for (int i = aged.getAge(); i > 0; i--) {
                mod += 0.1;
            }
        }

        double value = lib.getEntityData().getHeadWidth(type);
        if (value == -1d) {
            value = 0.75;
        }

        value -= mod;
        return Math.abs(value);
    }

    /**
     * Get the entity head height
     *
     * @return the head height
     */
    @Override
    public double getHeadHeight() {
        EntityType type = entity.getType();
        double mod = 0;

        if (entity instanceof Ageable) {
            Ageable aged = (Ageable) entity;
            for (int i = aged.getAge(); i > 0; i--) {
                mod += 0.1;
            }
        }

        double value = lib.getEntityData().getHeadHeight(type);
        if (value == -1d) {
            value = 0.75;
        }

        value -= mod;
        return Math.abs(value);
    }

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
    @Override
    public Position3D getHeadCenter() {
        Location location = entity.getEyeLocation().clone();

        double xOffset = lib.getEntityData().getXOffset(entity.getType());
        double yOffset = lib.getEntityData().getYOffset(entity.getType());

        if (xOffset == 0 && yOffset == 0) return Position3D.fromLocation(location);
        if (entity instanceof Ageable) {
            double mod = 0;

            Ageable aged = (Ageable) entity;
            for (int i = aged.getAge(); i > 0; i--) {
                mod += 0.1;
            }

            xOffset -= mod;
            yOffset -= mod;
        }

        Vector other = new Vector(xOffset, yOffset, xOffset).normalize();
        Vector direction = location.getDirection().setY(0).normalize().multiply(other);

        return Position3D.fromLocation(location.clone().add(direction));
    }
}
