package es.karmadev.mock.bukkit.impl;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.plugin.PluginManagerMock;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class MockServer extends ServerMock {

    @Override
    public @NotNull File getPluginsFolder() {
        return new File("D:\\TEMP\\Mock");
    }

    @Override
    public @NotNull PluginManagerMock getPluginManager() {
        return new MockPluginManager(this);
    }
}
