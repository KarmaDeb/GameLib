package es.karmadev.gamelib;

import es.karmadev.gamelib.entity.human.HumanOffline;
import es.karmadev.gamelib.entity.human.HumanPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

/**
 * Represents the game lib general
 * player manager. The player manager
 * allows fetching all offline clients
 * and connected clients.
 */
public interface PlayerManager {

    /**
     * Get all the offline players
     *
     * @return the offline players
     */
    Collection<? extends HumanOffline> getOfflinePlayers();

    /**
     * Get all the online players
     *
     * @return the online players
     */
    Collection<? extends HumanPlayer> getOnlinePlayers();

    /**
     * Get an offline player by its unique
     * id
     *
     * @param uniqueId the player unique id
     * @return the offline player
     */
    @Nullable
    default HumanOffline getOfflinePlayer(final @NotNull UUID uniqueId) {
        return getOfflinePlayers().stream().filter((player) -> player.getUniqueId().equals(uniqueId))
                .findAny().orElse(null);
    }

    /**
     * Get a player by its unique
     * id
     *
     * @param uniqueId the player unique id
     * @return the player
     */
    @Nullable
    default HumanPlayer getPlayer(final @NotNull UUID uniqueId) {
        return getOnlinePlayers().stream().filter((player) -> player.getUniqueId().equals(uniqueId))
                .findAny().orElse(null);
    }

    /**
     * Get a player by its name
     *
     * @param name the player name
     * @return the player
     */
    @Nullable
    default HumanPlayer getPlayer(final @NotNull String name) {
        return getOnlinePlayers().stream().filter((player) -> player.getName().equals(name))
                .findAny().orElse(null);
    }
}
