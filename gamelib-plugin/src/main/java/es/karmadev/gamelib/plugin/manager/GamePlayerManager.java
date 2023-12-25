package es.karmadev.gamelib.plugin.manager;

import com.google.inject.Singleton;
import es.karmadev.gamelib.PlayerManager;
import es.karmadev.gamelib.entity.human.HumanOffline;
import es.karmadev.gamelib.entity.human.HumanPlayer;
import es.karmadev.gamelib.plugin.inject.annotations.PostConstruct;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class GamePlayerManager implements PlayerManager {

    private Collection<HumanOffline> offlinePlayers;
    private Collection<HumanPlayer> onlinePlayers;

    @PostConstruct
    public void init() {
        offlinePlayers = ConcurrentHashMap.newKeySet();
        onlinePlayers = ConcurrentHashMap.newKeySet();
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

    public void addPlayer(final HumanPlayer player) {
        this.onlinePlayers.add(player);
        this.offlinePlayers.add(player);
    }

    public void removePlayer(final HumanPlayer player) {
        this.onlinePlayers.remove(player);
    }
}
