package es.karmadev.gamelib.entity.human;

import org.jetbrains.annotations.Nullable;

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

    /**
     * Get if the player is online
     *
     * @return if the player is
     * online
     */
    boolean isOnline();

    /**
     * Get the human player instance
     *
     * @return the human player
     */
    @Nullable
    HumanPlayer getPlayer();
}
