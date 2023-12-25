package es.karmadev.gamelib.plugin;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import es.karmadev.gamelib.plugin.inject.GameModule;
import es.karmadev.gamelib.plugin.listener.ListenerInitializer;
import org.bukkit.plugin.java.JavaPlugin;

public class GamePlugin extends JavaPlugin {

    @Inject
    private ListenerInitializer listenerInitializer;

    @Override
    public void onEnable() {
        final GameModule module = new GameModule(this);
        final Injector injector = Guice.createInjector(module);
        injector.injectMembers(this);

        listenerInitializer.init(injector);
    }
}
