package es.karmadev.gamelib.storage;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.UUID;

/**
 * Represents a property type for the
 * storage
 */
public final class PropertyType<T> {

    /**
     * Byte number type
     */
    public final static PropertyType<Byte> BYTE = new PropertyType<>(Byte.TYPE);
    /**
     * Short number type
     */
    public final static PropertyType<Short> SHORT = new PropertyType<>(Short.TYPE);
    /**
     * Int number type
     */
    public final static PropertyType<Integer> INTEGER = new PropertyType<>(Integer.TYPE);
    /**
     * Long number type
     */
    public final static PropertyType<Long> LONG = new PropertyType<>(Long.TYPE);
    /**
     * Float number type
     */
    public final static PropertyType<Float> FLOAT = new PropertyType<>(Float.TYPE);
    /**
     * Double number type
     */
    public final static PropertyType<Double> DOUBLE = new PropertyType<>(Double.TYPE);
    /**
     * Boolean type
     */
    public final static PropertyType<Boolean> BOOLEAN = new PropertyType<>(Boolean.TYPE);
    /**
     * String type
     */
    public final static PropertyType<String> STRING = new PropertyType<>(String.class);
    /**
     * UUID type
     */
    public final static PropertyType<UUID> UUID = new PropertyType<>(java.util.UUID.class);
    /**
     * Timestamp (instant) type
     */
    public final static PropertyType<Instant> TIMESTAMP = new PropertyType<>(Instant.class);

    private final Type type;

    /**
     * Initialize the property type
     *
     * @param type the type
     */
    private PropertyType(final Type type) {
        this.type = type;
    }

    /**
     * Get the property type
     *
     * @return the type
     */
    public Type getType() {
        return type;
    }
}
