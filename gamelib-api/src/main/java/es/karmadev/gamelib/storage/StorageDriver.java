package es.karmadev.gamelib.storage;

import es.karmadev.gamelib.storage.schema.SchemaDriver;
import es.karmadev.gamelib.storage.schema.StorageSchema;

/**
 * Represents a storage driver.
 */
public interface StorageDriver {

    /**
     * Register a schema into the
     * storage driver
     *
     * @param schema the schema to register
     * @param driver the driver
     */
    void registerSchema(final StorageSchema<?> schema, final SchemaDriver driver);

    /**
     * Unregister a schema from the
     * storage driver
     *
     * @param schema the schema to unregister
     */
    void unregisterSchema(final StorageSchema<?> schema);

    /**
     * Get the storage of a schema
     *
     * @param owner the schema owner
     * @return the schema storage
     */
    <T extends SchemaDriver> T getStorage(final Class<? extends StorageSchema<T>> owner);
}
