package es.karmadev.gamelib.plugin.manager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import es.karmadev.gamelib.GameLib;
import es.karmadev.gamelib.PlayerManager;
import es.karmadev.gamelib.entity.human.HumanOffline;
import es.karmadev.gamelib.entity.human.HumanPlayer;
import es.karmadev.gamelib.plugin.GameLibImpl;
import es.karmadev.gamelib.plugin.Preconditions;
import es.karmadev.gamelib.plugin.impl.entity.GameHPlayer;
import es.karmadev.gamelib.plugin.impl.entity.GameOfflinePlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Singleton
public class GamePlayerManager implements PlayerManager {

    private final GameLibImpl lib;

    private final Collection<HumanOffline> offlinePlayers = ConcurrentHashMap.newKeySet();
    private final Collection<HumanPlayer> onlinePlayers = ConcurrentHashMap.newKeySet();

    @Inject
    public GamePlayerManager(final GameLibImpl lib) {
        this.lib = lib;
        for (OfflinePlayer offline : Bukkit.getOfflinePlayers()) {
            createPlayer(offline);
        }
    }

    /**
     * Get all the offline players
     *
     * @return the offline players
     */
    @Override
    public Collection<? extends HumanOffline> getOfflinePlayers() {
        return Collections.unmodifiableCollection(offlinePlayers);
    }

    /**
     * Get all the online players
     *
     * @return the online players
     */
    @Override
    public Collection<? extends HumanPlayer> getOnlinePlayers() {
        return Collections.unmodifiableCollection(onlinePlayers);
    }

    /**
     * Create a player from the player
     * object
     *
     * @param player the player
     * @return the player
     */
    @Override
    public @NotNull HumanPlayer createPlayer(final Player player) {
        Preconditions.requireNotNull(player, "Cannot create player from null bukkit player");

        HumanOffline offline = createPlayer((OfflinePlayer) player);
        HumanPlayer online = offline.getPlayer();

        if (online == null) {
            online = new GameHPlayer(lib, player);
            onlinePlayers.add(online);
        }

        return online;
    }

    /**
     * Create an offline player from
     * the offline player object
     *
     * @param player the player
     * @return the offline player
     */
    @Override
    public @Nullable HumanOffline createPlayer(final OfflinePlayer player) {
        Preconditions.requireNotNull(player, "Cannot create player from null bukkit offline player");

        Optional<HumanOffline> instance = Stream.concat(
                        onlinePlayers.stream(), offlinePlayers.stream())
                .filter((p) -> p.getUniqueId().equals(player.getUniqueId()))
                .findAny();

        if (instance.isPresent()) {
            return instance.get();
        }

        HumanOffline p = new GameOfflinePlayer(lib, player.getName(), player.getUniqueId());
        offlinePlayers.add(p);

        return p;
    }

    public void removePlayer(final HumanPlayer player) {
        this.onlinePlayers.remove(player);
    }
}
