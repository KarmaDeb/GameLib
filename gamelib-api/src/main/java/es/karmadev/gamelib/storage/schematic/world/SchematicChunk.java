package es.karmadev.gamelib.storage.schematic.world;

import es.karmadev.gamelib.storage.schematic.entity.SchematicEntity;

import java.util.Collection;

/**
 * Represents a schematic chunk. In
 * GameLib, schematics are stored in
 * world chunks. This helps make the
 * storing and read process faster, as
 * each chunk can be processed asynchronously
 */
public interface SchematicChunk {

    /**
     * Get the chunk X position
     *
     * @return the chunk X
     */
    int getX();

    /**
     * Get chunk Z position
     *
     * @return the chunk Z
     */
    int getZ();

    /**
     * Get the chunk blocks
     *
     * @return the chunk blocks
     */
    Collection<? extends SchematicBlock> getBlocks();

    /**
     * Get the chunk entities
     *
     * @return the chunk entities
     */
    Collection<? extends SchematicEntity> getEntities();
}
