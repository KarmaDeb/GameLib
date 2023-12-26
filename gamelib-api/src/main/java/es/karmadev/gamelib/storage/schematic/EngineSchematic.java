package es.karmadev.gamelib.storage.schematic;

import es.karmadev.gamelib.storage.schematic.world.SchematicChunk;
import org.bukkit.World;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Represents a schematic. The schematic contains
 * information about blocks and entities in a
 * world area that can be loaded and put in the
 * world later
 */
public interface EngineSchematic {

    /**
     * Get the schematic name
     *
     * @return the schematic name
     */
    String getName();

    /**
     * Get the schematic unique ID
     *
     * @return the schematic unique ID
     */
    UUID getUniqueId();

    /**
     * Get the schematic world
     *
     * @return the schematic world
     */
    World getWorld();

    /**
     * Get all the schematic chunks
     *
     * @return the schematic chunks
     */
    Collection<? extends SchematicChunk> getChunks();

    /**
     * Load the schematic, asynchronously
     *
     * @return the schematic load task
     */
    CompletableFuture<Void> loadSchematic();

    /**
     * Save the schematic
     *
     * @return if the schematic was
     * successfully saved
     */
    boolean save();
}