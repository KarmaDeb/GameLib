package es.karmadev.gamelib.plugin.impl.region;

import es.karmadev.gamelib.entity.EngineEntity;
import es.karmadev.gamelib.plugin.GameLibImpl;
import es.karmadev.gamelib.plugin.impl.entity.GameEntity;
import es.karmadev.gamelib.pos.Position3D;
import es.karmadev.gamelib.region.shape.CuboidGround;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Cuboid extends CuboidGround {

    private final GameLibImpl lib;

    /**
     * Initialize the cuboid playground
     *
     * @param world the playground world
     * @param pos1  playground position 1
     * @param pos2  playground position 2
     */
    public Cuboid(final GameLibImpl lib, final @NotNull World world, final @NotNull Position3D pos1, final @NotNull Position3D pos2) {
        super(world, pos1, pos2);
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
        double length = getVolume();

        List<EngineEntity> engineEntities = new ArrayList<>();
        for (Entity entity : entities) {
            Location entityLocation = entity.getLocation();
            double distance = distance(Position3D.fromLocation(entityLocation));

            if (distance <= length) {
                EngineEntity engineEntity = lib.getEntity(entity.getUniqueId());
                if (engineEntity == null) engineEntity = new GameEntity(lib, entity);

                engineEntities.add(engineEntity);
            }
        }

        return engineEntities;
    }
}
