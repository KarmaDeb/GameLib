package es.karmadev.gamelib.plugin.impl.region;

import es.karmadev.gamelib.entity.EngineEntity;
import es.karmadev.gamelib.plugin.GameLibImpl;
import es.karmadev.gamelib.plugin.impl.entity.GameEntity;
import es.karmadev.gamelib.pos.Position3D;
import es.karmadev.gamelib.region.shape.SphereGround;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Sphere extends SphereGround {

    private final GameLibImpl lib;

    /**
     * Initialize the cuboid playground
     *
     * @param world  the playground world
     * @param pos1   playground position 1
     * @param radius playground radius
     */
    public Sphere(final GameLibImpl lib, final @NotNull World world, final @NotNull Position3D pos1, final double radius) {
        super(world, pos1, radius);
        this.lib = lib;
    }

    /**
     * Get all the entities inside the
     * playground area
     *
     * @return the playground entities
     */
    @Override
    public Collection<EngineEntity> getEntities() {
        List<Entity> entities = world.getEntities();
        Position3D center = getCenter();
        double length = getRadius();

        List<EngineEntity> engineEntities = new ArrayList<>();
        for (Entity entity : entities) {
            Position3D entityLocation = Position3D.fromLocation(entity.getLocation());
            double distance = distance(entityLocation);

            if (distance <= length) {
                EngineEntity engineEntity = lib.getEntity(entity.getUniqueId());
                if (engineEntity == null) engineEntity = new GameEntity(lib, entity);

                engineEntities.add(engineEntity);
            }
        }

        return engineEntities;
    }
}