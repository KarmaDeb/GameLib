package es.karmadev.gamelib.plugin.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import es.karmadev.gamelib.entity.human.HumanPlayer;
import es.karmadev.gamelib.plugin.impl.entity.GameHPlayer;
import es.karmadev.gamelib.plugin.manager.GamePlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@Singleton
public class ConnectionListener implements Listener {

    @Inject
    private GamePlayerManager playerManager;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        HumanPlayer client = playerManager.getPlayer(player.getUniqueId());
        if (client != null) {
            playerManager.removePlayer(client);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        HumanPlayer client = new GameHPlayer(player);

        playerManager.addPlayer(client);
    }
}
