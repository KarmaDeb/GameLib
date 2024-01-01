package es.karmadev.gamelib.serialization;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents a collection of
 * {@link GenericField fields} with
 * values. The values are always
 * not null
 */
public class FieldCollection implements Iterable<GenericField<?, ?>> {

    private final Map<GenericField<?, ?>, Object> fieldMap = new HashMap<>();

    public FieldCollection() {}

    /**
     * Add a field to the collection
     *
     * @param field the field to add
     * @param value the field value
     * @param <T> the field type
     */
    public <T, A> void set(final GenericField<T, A> field, final T value) {
        fieldMap.put(field, value);
    }

    /**
     * Remove a field from the
     * collection
     *
     * @param field the field to remove
     * @param <T> the field type
     */
    public <T, A> void remove(final GenericField<T, A> field) {
        fieldMap.remove(field);
    }

    /**
     * Get a field value
     *
     * @param field the field
     * @return the field value
     * @param <T> the field type
     */
    @Nullable
    public <T, A> T get(final GenericField<T, A> field) {
        if (fieldMap.containsKey(field)) {
            Object value = fieldMap.get(field);
            return field.getType().cast(value);
        }

        return null;
    }

    /**
     * Get if the collection contains
     * the field
     *
     * @param field the field
     * @return if the field is present
     * @param <T> the field type
     */
    public <T, A> boolean contains(final GenericField<T, A> field) {
        return fieldMap.containsKey(field);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @NotNull
    @Override
    public Iterator<GenericField<?, ?>> iterator() {
        return fieldMap.keySet().iterator();
    }
}
