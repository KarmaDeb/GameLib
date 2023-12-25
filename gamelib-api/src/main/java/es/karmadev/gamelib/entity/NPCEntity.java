package es.karmadev.gamelib.entity;

import java.util.UUID;

/**
 * Represents a non-playable
 * entity. Of any kind
 */
public abstract class NPCEntity extends EngineEntity {

    /**
     * Create a new engine entity
     *
     * @param name     the entity name
     * @param uniqueId the entity unique ID
     */
    public NPCEntity(final String name, final UUID uniqueId) {
        super(name, uniqueId);
    }
}