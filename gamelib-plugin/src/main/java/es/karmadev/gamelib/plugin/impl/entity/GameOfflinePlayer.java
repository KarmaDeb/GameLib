package es.karmadev.gamelib.plugin.impl.entity;

import es.karmadev.gamelib.entity.human.HumanOffline;
import es.karmadev.gamelib.entity.human.HumanPlayer;
import es.karmadev.gamelib.plugin.GameLibImpl;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class GameOfflinePlayer implements HumanOffline {

    private final GameLibImpl lib;
    private final String name;
    private final UUID uniqueId;

    public GameOfflinePlayer(final GameLibImpl lib, final String name, final UUID uniqueId) {
        this.lib = lib;
        this.name = name;
        this.uniqueId = uniqueId;
    }

    /**
     * Get the player name
     *
     * @return the player name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Get the player unique id
     *
     * @return the player unique id
     */
    @Override
    public UUID getUniqueId() {
        return this.uniqueId;
    }

    /**
     * Get if the player is online
     *
     * @return if the player is
     * online
     */
    @Override
    public boolean isOnline() {
        return Bukkit.getPlayer(uniqueId) != null;
    }

    /**
     * Get the human player instance
     *
     * @return the human player
     */
    @Override
    public @Nullable HumanPlayer getPlayer() {
        return lib.getPlayerManager().getPlayer(uniqueId);
    }
}
