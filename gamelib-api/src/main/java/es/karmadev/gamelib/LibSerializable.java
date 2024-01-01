package es.karmadev.gamelib;

import es.karmadev.gamelib.serialization.FieldCollection;

/**
 * Represents an element which can
 * be serialized by the GameLib.
 * Regardless an instance implementing this
 * method, they can still throw {@link UnsupportedOperationException}
 * if they choose to not allow serialization.
 * For instance, {@link es.karmadev.gamelib.entity.EngineEntity} is
 * serializable, but usually {@link es.karmadev.gamelib.entity.human.HumanPlayer} which
 * extends from EngineEntity is not.
 *
 * @param <A> the loadData method return type
 */
public interface LibSerializable<A> {

    /**
     * Serialize the object
     *
     * @return the serialized object
     */
    FieldCollection serialize();

    /**
     * Load the data and put it in
     * the current object
     *
     * @param data the object data
     * @return the object with the loaded
     * data
     */
    A loadData(final FieldCollection data);
}
