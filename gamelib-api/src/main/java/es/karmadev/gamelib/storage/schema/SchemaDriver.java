package es.karmadev.gamelib.storage.schema;

import es.karmadev.gamelib.storage.PropertyType;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Represents a schema driver. The driver
 * is the one who actually stores and retrieves
 * data from the data driver
 */
public interface SchemaDriver {

    /**
     * Get a value from the driver
     *
     * @param key the key of the value
     *            to obtain
     * @param type the value type
     * @return the value
     * @param <T> the value type
     */
    @Nullable
    default <T> T getValue(final String key, final PropertyType<T> type) {
        return getValue(key, type, null);
    }

    /**
     * Get a value from the driver
     *
     * @param key the key of the value
     *            to obtain
     * @param type the value type
     * @param def the default value if the
     *            value is missing
     * @return the value
     * @param <T> the value type
     */
    <T> T getValue(final String key, final PropertyType<T> type, final T def);

    /**
     * Set a value on the driver
     *
     * @param key the key of the value
     * @param type the value type
     * @param value the value
     * @return if the operation was successful
     * @param <T> the value type
     */
    <T> boolean setValue(final String key, final PropertyType<T> type, final T value);

    /**
     * Remove a value from the
     * driver
     *
     * @param key the value key
     * @param type the value type
     * @return the removed value
     * @param <T> the value type
     */
    <T> T removeValue(final String key, final PropertyType<T> type);

    /**
     * Get all the driver keys
     *
     * @return the keys
     */
    Collection<String> getKeys();

    /**
     * Save the data if needed
     *
     * @return if the operation was successful
     */
    boolean save();
}
