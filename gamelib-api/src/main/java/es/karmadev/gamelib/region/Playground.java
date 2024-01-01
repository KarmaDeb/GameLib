package es.karmadev.gamelib.region;

import es.karmadev.gamelib.entity.EngineEntity;
import es.karmadev.gamelib.pos.Position3D;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Collection;

/**
 * The playground represents the
 * playable area of a game. Usually
 * a square, but the playground shape
 * can be any
 */
public interface Playground {

    /**
     * Get the playground game
     * shape
     *
     * @return the playground shape
     */
    GameShape getShape();

    /**
     * Get the playground world
     *
     * @return the world
     */
    World getWorld();

    /**
     * Get the center of the
     * playground
     *
     * @return the center position
     */
    Position3D getCenter();

    /**
     * Get all the playground corner
     * positions
     *
     * @return the playground corners
     */
    Collection<Position3D> getCorners();

    /**
     * Get all the blocks inside the
     * playground area
     *
     * @return the playground blocks
     */
    Collection<Block> getBlocks();

    /**
     * Get all the entities inside the
     * playground area
     *
     * @return the playground entities
     */
    Collection<EngineEntity> getEntities();

    /**
     * Get the distance between the
     * coordinates and the center
     *
     * @param x the x position
     * @param y the y position
     * @param z the z position
     * @return the distance between
     */
    double distance(final double x, final double y, final double z);

    /**
     * Get the distance between the
     * position and the center
     *
     * @param position the position
     * @return the distance between
     */
    default double distance(final Position3D position) {
        return distance(position.getX(), position.getY(), position.getZ());
    }

    /**
     * Get the distance between the
     * block and the center
     *
     * @param block the block
     * @return the distance between
     */
    default double distance(final Block block) {
        return distance(block.getX(), block.getY(), block.getZ());
    }

    /**
     * Get the distance between the
     * entity and the center
     *
     * @param entity the entity
     * @return the distance between
     */
    default double distance(final EngineEntity entity) {
        return distance(entity.getPosition());
    }
}
