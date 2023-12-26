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
}
