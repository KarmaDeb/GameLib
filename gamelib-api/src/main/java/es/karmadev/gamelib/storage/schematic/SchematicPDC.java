package es.karmadev.gamelib.storage.schematic;

import org.bukkit.persistence.PersistentDataType;

import java.util.Collection;

/**
 * Represents a persistent data container
 * for an element of a schematic
 */
public interface SchematicPDC {

    /**
     * Get the PDC keys
     *
     * @return the PDC keys
     */
    Collection<String> getKeys();

    /**
     * Get the persistent data type of
     * the key value
     *
     * @param key the key
     * @return the value data type
     */
    PersistentDataType<?, ?> getType(final String key);

    /**
     * Get a PDC value
     *
     * @param key the key
     * @return the value
     * @param <T> the value type
     */
    <T> T getValue(final String key);
}
