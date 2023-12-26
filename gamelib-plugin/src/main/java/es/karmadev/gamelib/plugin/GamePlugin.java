package es.karmadev.gamelib.plugin;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import es.karmadev.gamelib.GameLib;
import es.karmadev.gamelib.plugin.inject.GameModule;
import es.karmadev.gamelib.plugin.listener.ListenerInitializer;
import es.karmadev.gamelib.plugin.manager.communications.CertManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.security.cert.X509Certificate;
import java.util.UUID;
import java.util.logging.Level;

public class GamePlugin extends JavaPlugin {

    @Inject
    private ListenerInitializer listenerInitializer;
    @Inject
    private GameLibImpl gameLib;

    @Override
    public void onEnable() {
        final GameModule module = new GameModule(this);
        final Injector injector = Guice.createInjector(module);
        injector.injectMembers(this);

        try {
            CertManager manager = new CertManager(this);
            X509Certificate certificate = manager.generateCertificate();

            if (isInvalidCertificate(certificate)) {
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
        } catch (Throwable ex) {
            getLogger().log(Level.SEVERE, ex, () -> "Failed to generate GameLib certificate. Plugin won't start!");

            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        module.setLibBinding(gameLib);
        listenerInitializer.init(injector);
    }

    /**
     * Get the current game lib instance
     *
     * @return the game lib instance
     */
    public GameLib getGameLib() {
        return gameLib;
    }

    private boolean isInvalidCertificate(final X509Certificate certificate) {
        if (certificate == null) {
            getLogger().log(Level.WARNING, "GameLib generated an invalid certificate. Plugin won't start!");
            return true;
        }

        String name = certificate.getSubjectX500Principal().getName();
        String[] data = name.split(",");

        String invalid = null;
        for (String block : data) {
            block = block.trim();
            if (!block.contains("=")) continue;

            String[] blockData = block.split("=");
            String key = blockData[0];
            String value = block.substring(key.length() + 1);

            if (isInvalidEntry(key, value)) {
                invalid = key;
                break;
            }
        }

        if (invalid != null) {
            getLogger().log(Level.WARNING, "GameLib loaded or generated an invalid certificate (" + invalid + "). Plugin won't start!");
            return true;
        }

        return false;
    }

    private boolean isInvalidEntry(final String key, final String value) {
        switch (key.toLowerCase()) {
            case "cn":
                try {
                    UUID.fromString(value);
                    return false;
                } catch (IllegalArgumentException ex) {
                    return true;
                }
            case "ou":
            case "o":
                return !value.equals("RedDo");
            case "l":
            case "st":
                return !value.equals("Madrid");
            case "c":
                return !value.equals("Spain");
            default:
                return false;
        }
    }
}
