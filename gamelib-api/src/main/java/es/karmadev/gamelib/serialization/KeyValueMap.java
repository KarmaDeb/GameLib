package es.karmadev.gamelib.serialization;

import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a key-value map, in where
 * the key is always a string, and the value
 * is an object
 */
public final class KeyValueMap {

    private final Map<String, Object> map;

    /**
     * Initialize the key-value map
     *
     * @param map the map
     */
    public KeyValueMap(final Map<String, Object> map) {
        this.map = map;
    }

    /**
     * Get the map keys
     *
     * @return the map keys
     */
    public Collection<String> getKeys() {
        return map.keySet();
    }

    /**
     * Get a value from the map
     *
     * @param key the value key
     * @return the value
     */
    @Nullable
    public Object getValue(final String key) {
        return map.get(key);
    }

    /**
     * Get a value from the map
     *
     * @param key the value key
     * @param def the default value
     * @return the value
     */
    public Object getValue(final String key, final Object def) {
        Object value = getValue(key);
        if (value == null) return def;

        return value;
    }

    /**
     * Wrap a key-value map into
     * multiple maps
     *
     * @param maps the maps
     * @return the key value map
     */
    @SafeVarargs
    public static KeyValueMap wrap(final Map<String, Object>... maps) {
        Map<String, Object> wrapper = new HashMap<>();
        if (maps != null) {
            for (Map<String, Object> map : maps) {
                if (map == null || map.isEmpty()) continue;
                wrapper.putAll(map);
            }
        }

        return new KeyValueMap(wrapper);
    }
}
