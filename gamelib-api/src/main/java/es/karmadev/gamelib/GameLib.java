package es.karmadev.gamelib;

import es.karmadev.gamelib.entity.EngineEntity;
import es.karmadev.gamelib.exception.PlaygroundPositionException;
import es.karmadev.gamelib.pos.Position3D;
import es.karmadev.gamelib.region.GameShape;
import es.karmadev.gamelib.region.Playground;
import es.karmadev.gamelib.storage.StorageDriver;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Represents the game lib
 */
public abstract class GameLib {

    private static GameLib instance;

    /**
     * Register the game library instance
     *
     * @throws SecurityException if there's an instance
     * already registered
     */
    protected final void registerAsInstance() throws SecurityException {
        if (instance != null) throw new SecurityException("Cannot define game lib instance as it has been already defined");
        GameLib.instance = this;
    }

    /**
     * Get the game lib instance
     *
     * @return the instance
     */
    public static GameLib getInstance() {
        return instance;
    }

    /**
     * Run a task asynchronously
     *
     * @param task the task to run
     */
    public abstract void runAsync(final Runnable task);

    /**
     * Run a task synchronously
     *
     * @param task the task to run
     */
    public abstract void runSync(final Runnable task);

    /**
     * Get the library player manager
     *
     * @return the player manager
     */
    public abstract PlayerManager getPlayerManager();

    /**
     * Get the library storage driver
     *
     * @return the storage driver
     */
    public abstract StorageDriver getStorageDriver();

    /**
     * Get all the plugin-aware entities
     *
     * @return the plugin-aware entities
     */
    public abstract Collection<? extends EngineEntity> getEntities();

    /**
     * Get all the plugin-aware entities on the
     * specified world
     *
     * @param world the world to get entities at
     * @return the world entities
     */
    public Collection<? extends EngineEntity> getEntities(final World world) {
        if (world == null) return Collections.emptyList();
        return getEntities().stream().filter((entity) -> entity.getWorld().equals(world))
                .collect(Collectors.toList());
    }

    /**
     * Get all the entities whose ID match
     *
     * @param id the id to search by
     * @return the entities with the ID
     */
    @NotNull
    public Collection<? extends EngineEntity> getEntities(final int id) {
        return getEntities().stream().filter((entity) -> entity.getId() == id)
                .collect(Collectors.toList());
    }

    /**
     * Get all the entities with the name
     *
     * @param name the name to search by
     * @return the entities with the name
     */
    @NotNull
    public Collection<? extends EngineEntity> getEntities(final String name) {
        return getEntities().stream().filter((entity) -> Objects.equals(name, entity.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Get an entity by its unique ID
     *
     * @param uniqueId the entity unique ID
     * @return the entity
     */
    @Nullable
    public EngineEntity getEntity(final UUID uniqueId) {
        if (uniqueId == null) return null;
        return getEntities().stream().filter((entity) -> entity.getUniqueId().equals(uniqueId))
                .findAny().orElse(null);
    }

    /**
     * Create a new ground based on the
     * shape and the positions
     *
     * @param shape the playground shape
     * @param world the playground world
     * @param positions the playground positions
     * @return the playground
     * @throws PlaygroundPositionException if the number of positions
     * provided does not meet the shape minimum
     */
    public abstract Playground createGround(final GameShape shape, final World world, final Position3D... positions) throws PlaygroundPositionException;
}
