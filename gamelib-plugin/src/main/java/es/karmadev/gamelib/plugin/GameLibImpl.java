package es.karmadev.gamelib.plugin;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import es.karmadev.gamelib.GameLib;
import es.karmadev.gamelib.PlayerManager;
import es.karmadev.gamelib.entity.EngineEntity;
import es.karmadev.gamelib.exception.PlaygroundPositionException;
import es.karmadev.gamelib.plugin.data.EntityData;
import es.karmadev.gamelib.plugin.impl.region.Cuboid;
import es.karmadev.gamelib.plugin.impl.region.Sphere;
import es.karmadev.gamelib.plugin.manager.GamePlayerManager;
import es.karmadev.gamelib.plugin.manager.GameStorageDriver;
import es.karmadev.gamelib.pos.Position3D;
import es.karmadev.gamelib.region.GameShape;
import es.karmadev.gamelib.region.Playground;
import es.karmadev.gamelib.storage.StorageDriver;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class GameLibImpl extends GameLib {

    private final GamePlugin plugin;
    private final EntityData entityData;

    @Inject
    private PlayerManager manager;
    @Inject
    private StorageDriver driver;

    private final Set<EngineEntity> entities = ConcurrentHashMap.newKeySet();

    @Inject
    public GameLibImpl(final GamePlugin plugin, final EntityData data) {
        registerAsInstance();
        this.plugin = plugin;
        this.entityData = data;
    }

    /**
     * Run a task asynchronously
     *
     * @param task the task to run
     */
    @Override
    public void runAsync(final Runnable task) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
    }

    /**
     * Run a task synchronously
     *
     * @param task the task to run
     */
    @Override
    public void runSync(final Runnable task) {
        Bukkit.getScheduler().runTask(plugin, task);
    }

    /**
     * Get the library player manager
     *
     * @return the player manager
     */
    @Override
    public PlayerManager getPlayerManager() {
        return manager;
    }

    /**
     * Get the library storage driver
     *
     * @return the storage driver
     */
    @Override
    public StorageDriver getStorageDriver() {
        return driver;
    }

    /**
     * Get all the plugin-aware entities
     *
     * @return the plugin-aware entities
     */
    @Override
    public Collection<? extends EngineEntity> getEntities() {
        return Collections.unmodifiableCollection(entities);
    }

    public void addEntity(final EngineEntity entity) {
        entities.add(entity);
    }

    public void removeEntity(final EngineEntity entity) {
        entities.remove(entity);
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
    public Playground createGround(final @NotNull GameShape shape, final @NotNull World world, final @NotNull Position3D... positions) throws PlaygroundPositionException {
        Preconditions.requireNotNull(shape, "Shape cannot be null");
        Preconditions.requireNotNull(world, "World cannot be null");
        Preconditions.requireArrayNotNull(positions, "Positions cannot be null. Null found at index: $");

        if (shape.equals(GameShape.TETRAHEDRON)) {
            throw new UnsupportedOperationException("Not implemented");
        }

        switch (shape) {
            case CUBE:
                if (positions.length < 2) {
                    throw new PlaygroundPositionException(shape, 2, positions.length);
                }

                return new Cuboid(this, world, positions[0], positions[1]);
            case SPHERE:
                if (positions.length < 2) {
                    throw new PlaygroundPositionException(shape, 2, positions.length);
                }

                double radius = Math.abs(positions[0].distance(positions[1]));
                return new Sphere(this, world, positions[0], radius);
            default:
                throw new IllegalStateException("Unsupported shape: " + shape.name());
        }
    }

    public EntityData getEntityData() {
        return entityData;
    }
}
