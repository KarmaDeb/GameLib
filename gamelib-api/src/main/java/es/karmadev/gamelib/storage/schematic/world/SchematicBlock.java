package es.karmadev.gamelib.storage.schematic.world;

import es.karmadev.gamelib.storage.schematic.SchematicPDC;
import org.bukkit.Material;

/**
 * Represents a schematic world
 */
public interface SchematicBlock {

    /**
     * Get the block type
     *
     * @return the block type
     */
    Material getType();

    /**
     * Get if the block is
     * air
     *
     * @return if the block is air
     */
    default boolean isAir() {
        return getType().isAir();
    }

    /**
     * Get the block X position
     *
     * @return the X position
     */
    int getX();

    /**
     * Get the block Y position
     *
     * @return the Y position
     */
    int getY();

    /**
     * Get the block Z position
     *
     * @return the Z position
     */
    int getZ();

    /**
     * Get the block data
     *
     * @return the block data
     */
    String getData();

    /**
     * Get the block persistent data
     * container
     *
     * @return the block PDC
     */
    SchematicPDC getPersistentDataContainer();
}
