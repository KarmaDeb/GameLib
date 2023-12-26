package es.karmadev.gamelib.plugin.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import es.karmadev.gamelib.entity.EngineEntity;
import es.karmadev.gamelib.entity.human.HumanPlayer;
import es.karmadev.gamelib.plugin.GameLibImpl;
import es.karmadev.gamelib.plugin.impl.entity.GameHPlayer;
import es.karmadev.gamelib.plugin.manager.GamePlayerManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@Singleton
public class ConnectionListener implements Listener {

    @Inject
    private GameLibImpl lib;

    @Inject
    private GamePlayerManager playerManager;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        HumanPlayer client = playerManager.getPlayer(player.getUniqueId());

        if (client != null) {
            playerManager.removePlayer(client);
            lib.removeEntity(client);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        HumanPlayer client = new GameHPlayer(player);

        playerManager.addPlayer(client);
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        Entity entity = e.getEntity();
        if (!(entity instanceof Player)) {
            EngineEntity engineEntity = lib.getEntity(entity.getUniqueId());
            lib.removeEntity(engineEntity);
        }
    }
}
