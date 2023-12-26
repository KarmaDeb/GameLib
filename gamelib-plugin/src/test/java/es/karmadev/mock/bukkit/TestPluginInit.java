package es.karmadev.mock.bukkit;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import es.karmadev.gamelib.plugin.GamePlugin;
import es.karmadev.mock.bukkit.impl.MockServer;
import org.junit.jupiter.api.BeforeEach;

public class TestPluginInit {

    private ServerMock server;
    private GamePlugin plugin;

    @BeforeEach
    public void setup() {
        server = MockBukkit.mock(new MockServer());
        System.out.println(server.getPluginsFolder());

        plugin = MockBukkit.load(GamePlugin.class);
        new Thread(() -> {
            while (true) {
                synchronized (Thread.currentThread()) {
                    try {
                        Thread.currentThread().wait(1);
                    } catch (Throwable ignored) {}
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        TestPluginInit test = new TestPluginInit();
        test.setup();
    }
}
