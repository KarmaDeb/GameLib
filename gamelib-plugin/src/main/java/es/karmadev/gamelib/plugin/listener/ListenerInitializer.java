package es.karmadev.gamelib.plugin.listener;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import es.karmadev.gamelib.plugin.GamePlugin;
import es.karmadev.gamelib.plugin.inject.Initializer;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

@Singleton
public class ListenerInitializer implements Initializer {

    private final GamePlugin plugin;
    private final PluginManager manager;

    @Inject
    public ListenerInitializer(final GamePlugin plugin, final PluginManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    @Override
    public void init(@NotNull Injector injector) {
        manager.registerEvents(injector.getInstance(EntityListener.class), plugin);
        manager.registerEvents(injector.getInstance(ConnectionListener.class), plugin);
    }
}
