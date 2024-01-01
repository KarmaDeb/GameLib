package es.karmadev.gamelib.region.shape;

import es.karmadev.gamelib.pos.Position3D;
import es.karmadev.gamelib.region.GameShape;
import es.karmadev.gamelib.region.Playground;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Represents a cuboid playground
 */
public abstract class CuboidGround implements Playground {

    protected final World world;
    protected final double minX, minY, minZ, maxX, maxY, maxZ;

    /**
     * Initialize the cuboid playground
     *
     * @param world the playground world
     * @param pos1 playground position 1
     * @param pos2 playground position 2
     */
    public CuboidGround(final @NotNull World world, @NotNull Position3D pos1, final @NotNull Position3D pos2) {
        this.world = world;

        this.minX = Math.min(pos1.getX(), pos2.getX());
        this.minY = Math.min(pos1.getY(), pos2.getY());
        this.minZ = Math.min(pos1.getZ(), pos2.getZ());

        this.maxX = Math.max(pos1.getX(), pos2.getX());
        this.maxY = Math.max(pos1.getY(), pos2.getY());
        this.maxZ = Math.max(pos1.getZ(), pos2.getZ());
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
     * Get the playground min X
     * position
     *
     * @return the X position
     */
    public double getMinX() {
        return minX;
    }

    /**
     * Get the playground min Y
     * position
     *
     * @return the Y position
     */
    public double getMinY() {
        return minY;
    }

    /**
     * Get the playground min Z
     * position
     *
     * @return the Z position
     */
    public double getMinZ() {
        return minZ;
    }

    /**
     * Get the playground max X
     * position
     *
     * @return the X position
     */
    public double getMaxX() {
        return maxX;
    }

    /**
     * Get the playground max Y
     * position
     *
     * @return the Y position
     */
    public double getMaxY() {
        return maxY;
    }

    /**
     * Get the playground max Z
     * position
     *
     * @return the Z position
     */
    public double getMaxZ() {
        return maxZ;
    }

    /**
     * Get the playground volume
     *
     * @return the playground volume
     */
    public double getVolume() {
        double xSize = Math.abs(maxX - minX);
        double ySize = Math.abs(maxY - minY);
        double zSize = Math.abs(maxZ - minY);

        return xSize * ySize * zSize;
    }

    /**
     * Get the center of the
     * playground
     *
     * @return the center position
     */
    @Override
    public Position3D getCenter() {
        double centerX = (minX + maxX) / 2;
        double centerY = (minY + maxY) / 2;
        double centerZ = (minZ + maxZ) / 2;

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
        Position3D top = new Position3D(world, maxX, maxY, maxZ);
        Position3D bottom = new Position3D(world, minX, minY, minZ);

        return Arrays.asList(top, bottom);
    }

    /**
     * Get all the blocks inside the
     * playground area
     *
     * @return the playground blocks
     */
    @Override
    public Collection<Block> getBlocks() {
        Collection<Block> blocks = new ArrayList<>();

        for (int x = (int) minX; x < maxX; x++) {
            for (int y = (int) minY; y < maxY; y++) {
                for (int z = (int) minZ; z < maxZ; z++) {
                    Block block = world.getBlockAt(x, y, z);
                    blocks.add(block);
                }
            }
        }

        return blocks;
    }

    /**
     * Get the distance between the
     * coordinates and the center
     *
     * @param x the x position
     * @param y the y position
     * @param z the z position
     * @return the distance between
     */
    @Override
    public double distance(final double x, final double y, final double z) {
        Position3D center = getCenter();
        double vX = center.getX() - x;
        double vY = center.getY() - y;
        double vZ = center.getZ() - z;

        return Math.sqrt(vX + vY + vZ);
    }
}
