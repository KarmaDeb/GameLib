package es.karmadev.gamelib.plugin.inject;

import com.google.inject.*;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import es.karmadev.gamelib.GameLib;
import es.karmadev.gamelib.PlayerManager;
import es.karmadev.gamelib.plugin.GameLibImpl;
import es.karmadev.gamelib.plugin.GamePlugin;
import es.karmadev.gamelib.plugin.inject.annotations.PostConstruct;
import es.karmadev.gamelib.plugin.manager.GamePlayerManager;
import es.karmadev.gamelib.plugin.manager.GameStorageDriver;
import es.karmadev.gamelib.storage.StorageDriver;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;

@Singleton
public class GameModule extends AbstractModule implements TypeListener {

    private final GamePlugin plugin;

    public GameModule(final GamePlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Configures a {@link Binder} via the exposed methods.
     */
    @Override
    protected void configure() {
        super.bindListener(Matchers.any(), this);

        super.bind(GamePlugin.class).toInstance(plugin);
        super.bind(PluginManager.class).toInstance(plugin.getServer().getPluginManager());
        super.bind(PlayerManager.class).to(GamePlayerManager.class);
        super.bind(StorageDriver.class).to(GameStorageDriver.class);

        super.bind(GameLib.class).to(GameLibImpl.class);
    }

    /**
     * Invoked when Guice encounters a new type eligible for constructor or members injection. Called
     * during injector creation (or afterwards if Guice encounters a type at run time and creates a
     * JIT binding).
     *
     * @param type      encountered by Guice
     * @param encounter context of this encounter, enables reporting errors, registering injection
     *                  listeners and binding method interceptors for {@code type}.
     */
    @Override
    public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
        encounter.register((InjectionListener<I>) i -> Arrays.stream(i.getClass().getMethods())
                .filter((method) -> method.isAnnotationPresent(PostConstruct.class))
                .forEach((method) -> invokeMethod(method, i)));
    }

    private void invokeMethod(final Method method, final Object object) {
        try {
            method.invoke(object);
        } catch (IllegalAccessError | InvocationTargetException | IllegalAccessException ex) {
            plugin.getLogger().log(Level.SEVERE, ex, () -> "Failed to invoke method " + method.getName());
        }
    }
}
