package es.karmadev.gamelib;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Objects;

/**
 * The GLCond is an object that
 * declares the existence of a condition
 * under a player. The condition behavior
 * must be handled per implementation, even
 * though there are some defaults, the
 * implementation of those are per-implementation
 */
public class Condition<T> {

    public final static Condition<Double> GL_COND_HEALTH = new Condition<>("HEALTH", Double.class);

    /**
     * Create a new condition
     *
     * @param name the condition name
     * @param type the condition type
     * @return the condition
     * @param <T> the condition type
     */
    public static <T> Condition<T> create(final String name, final Class<T> type) {
        return new Condition<>(name, type);
    }

    private final String cond;
    private final Class<T> type;

    /**
     * Initialize the GameLib condition
     *
     * @param cond the condition name
     * @param type the condition type
     */
    private Condition(final String cond, final Class<T> type) {
        this.cond = cond;
        this.type = type;
    }

    /**
     * Get the condition name
     *
     * @return the condition name
     */
    public String getName() {
        return cond;
    }

    /**
     * Get if the element is a type
     * of the condition type
     *
     * @param element the element
     * @return if the element is a condition
     * type
     */
    public boolean isType(final Object element) {
        if (element == null) return false;

        Class<?> elementClass = element.getClass();
        return type.isInstance(element) || elementClass.isAssignableFrom(type) ||
                type.isAssignableFrom(elementClass);
    }

    /**
     * Cast the condition type to the element
     *
     * @param element the element
     * @return the conversed element
     */
    @Nullable
    public T cast(final Object element) {
        if (isType(element)) return type.cast(element);
        return null;
    }

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link HashMap}.
     * <p>
     * The general contract of {@code hashCode} is:
     * <ul>
     * <li>Whenever it is invoked on the same object more than once during
     *     an execution of a Java application, the {@code hashCode} method
     *     must consistently return the same integer, provided no information
     *     used in {@code equals} comparisons on the object is modified.
     *     This integer need not remain consistent from one execution of an
     *     application to another execution of the same application.
     * <li>If two objects are equal according to the {@link
     *     #equals(Object) equals} method, then calling the {@code
     *     hashCode} method on each of the two objects must produce the
     *     same integer result.
     * <li>It is <em>not</em> required that if two objects are unequal
     *     according to the {@link #equals(Object) equals} method, then
     *     calling the {@code hashCode} method on each of the two objects
     *     must produce distinct integer results.  However, the programmer
     *     should be aware that producing distinct integer results for
     *     unequal objects may improve the performance of hash tables.
     * </ul>
     *
     * @return a hash code value for this object.
     * @implSpec As far as is reasonably practical, the {@code hashCode} method defined
     * by class {@code Object} returns distinct integers for distinct objects.
     * @see Object#equals(Object)
     * @see System#identityHashCode
     */
    @Override
    public int hashCode() {
        int rs = Objects.hashCode(cond.trim().toLowerCase());
        rs = (rs * 31) + Objects.hashCode(type);

        return rs;
    }
}