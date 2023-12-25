package es.karmadev.gamelib.entity.human;

import java.util.UUID;

/**
 * Represents an offline human
 * player
 */
public interface HumanOffline {

    /**
     * Get the player name
     *
     * @return the player name
     */
    String getName();

    /**
     * Get the player unique id
     *
     * @return the player unique id
     */
    UUID getUniqueId();
}
