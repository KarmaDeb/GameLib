package es.karmadev.gamelib.region.shape;

import es.karmadev.gamelib.math.MathUtils;
import es.karmadev.gamelib.pos.Position3D;
import es.karmadev.gamelib.region.GameShape;
import es.karmadev.gamelib.region.Playground;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Represents a spherical playground
 */
public abstract class SphereGround implements Playground {

    protected final World world;
    protected final double centerX, centerY, centerZ, radius;

    /**
     * Initialize the cuboid playground
     *
     * @param world the playground world
     * @param pos1 playground position 1
     * @param radius playground radius
     */
    public SphereGround(final @NotNull World world, @NotNull Position3D pos1, final double radius) {
        this.world = world;
        this.centerX = pos1.getX();
        this.centerY = pos1.getY();
        this.centerZ = pos1.getZ();

        this.radius = radius;
    }

    /**
     * Get the playground game
     * shape
     *
     * @return the playground shape
     */
    @Override
    public GameShape getShape() {
        return GameShape.CUBE;
    }

    /**
     * Get the playground world
     *
     * @return the world
     */
    @Override
    public World getWorld() {
        return world;
    }

    /**
     * Get the playground X
     * position
     *
     * @return the X position
     */
    public double getX() {
        return centerX;
    }

    /**
     * Get the playground Y
     * position
     *
     * @return the Y position
     */
    public double getY() {
        return centerY;
    }

    /**
     * Get the playground Z
     * position
     *
     * @return the Z position
     */
    public double getZ() {
        return centerZ;
    }

    /**
     * Get the center of the
     * playground
     *
     * @return the center position
     */
    @Override
    public Position3D getCenter() {
        return new Position3D(world, centerX, centerY, centerZ);
    }

    /**
     * Get all the playground corner
     * positions
     *
     * @return the playground corners
     */
    @Override
    public Collection<Position3D> getCorners() {
        Position3D center = new Position3D(world, centerX, centerY, centerZ);
        return Collections.singleton(center);
    }

    /**
     * Get all the blocks inside the
     * playground area
     *
     * @return the playground blocks
     */
    @Override
    public Collection<Block> getBlocks() {
        Set<Block> blocks = new HashSet<>();

        int maxX = (int) (centerX + radius);
        int maxY = (int) (centerY + radius);
        int maxZ = (int) (centerZ + radius);

        int minX = (int) (centerX - radius);
        int minY = (int) (centerY - radius);
        int minZ = (int) (centerZ - radius);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    double distance = MathUtils.calcDistToCenter(
                            centerX, centerY, centerZ,
                            x, y, z
                    );

                    if (distance <= radius) {
                        Block block = world.getBlockAt(x, y, z);
                        blocks.add(block);
                    }
                }
            }
        }

        return blocks;
    }
}
