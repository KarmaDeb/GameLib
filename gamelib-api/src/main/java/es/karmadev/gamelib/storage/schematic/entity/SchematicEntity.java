package es.karmadev.gamelib.storage.schematic.entity;

import es.karmadev.gamelib.pos.Position3D;
import org.bukkit.entity.EntityType;

/**
 * Represents a schematic
 * entity
 */
public interface SchematicEntity {

    /**
     * Get the entity type
     *
     * @return the entity type
     */
    EntityType getType();

    /**
     * Get the entity position
     *
     * @return the entity position
     */
    Position3D getPosition();

    /**
     * Get the entity data
     *
     * @return the entity data
     */
    String getData();
}
