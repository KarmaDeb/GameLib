package es.karmadev.gamelib.storage.schema;

/**
 * Represents a storage schema. The schema
 * contains the "skeleton" of the data to
 * store.
 *
 * @param <T> the schema driver type
 */
public interface StorageSchema<T extends SchemaDriver> {

    /**
     * Provide the schema driver
     *
     * @return a schema driver instance
     */
    T provide();

    /**
     * Setup the schema. This method
     * should be able to set up the needed
     * files or tables in a database to make
     * {@link SchemaDriver} work
     *
     * @return if the setup was successful
     */
    boolean setup();

    /**
     * Uninstall the schema. This method
     * should remove the files or tables which
     * were created during {@link #setup()}
     *
     * @return if the uninstallation was success
     */
    boolean uninstall();
}
