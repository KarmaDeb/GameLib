package es.karmadev.gamelib.plugin.manager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import es.karmadev.gamelib.storage.StorageDriver;
import es.karmadev.gamelib.storage.schema.SchemaDriver;
import es.karmadev.gamelib.storage.schema.StorageSchema;

@Singleton
public class GameStorageDriver implements StorageDriver {

    @Inject
    public GameStorageDriver() {}

    /**
     * Register a schema into the
     * storage driver
     *
     * @param schema the schema to register
     * @param driver the driver
     */
    @Override
    public void registerSchema(final StorageSchema<?> schema, final SchemaDriver driver) {

    }

    /**
     * Unregister a schema from the
     * storage driver
     *
     * @param schema the schema to unregister
     */
    @Override
    public void unregisterSchema(final StorageSchema<?> schema) {

    }

    /**
     * Get the storage of a schema
     *
     * @param owner the schema owner
     * @return the schema storage
     */
    @Override
    public <T extends SchemaDriver> T getStorage(final Class<? extends StorageSchema<T>> owner) {
        return null;
    }
}
