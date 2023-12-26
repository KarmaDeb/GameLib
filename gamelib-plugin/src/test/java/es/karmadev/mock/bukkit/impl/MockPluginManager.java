package es.karmadev.mock.bukkit.impl;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.plugin.PluginManagerMock;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Paths;

public class MockPluginManager extends PluginManagerMock {

    /**
     * Constructs a new {@link PluginManagerMock} for the provided {@link ServerMock}.
     *
     * @param server The server this is for.
     */
    public MockPluginManager(@NotNull ServerMock server) {
        super(server);

        try {
            Field tempDir = PluginManagerMock.class.getDeclaredField("parentTemporaryDirectory");
            tempDir.setAccessible(true);

            tempDir.set(this, new File("D:\\TEMP\\Mock\\plugins"));
        } catch (ReflectiveOperationException ignored) {}
    }
}
