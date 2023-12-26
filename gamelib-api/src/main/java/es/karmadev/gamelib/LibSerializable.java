package es.karmadev.gamelib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.Map;

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
    Map<String, Object> serialize();

    /**
     * Serialize the element into
     * a json object
     *
     * @return the serialized object
     */
    default JsonObject serializeToJson() {
        Map<String, Object> serialized = serialize();
        if (serialized == null) return new JsonObject();

        Gson gson = new GsonBuilder().create();
        String jsonString = gson.toJson(serialized);

        return gson.fromJson(jsonString, JsonObject.class);
    }

    /**
     * Load the data and put it in
     * the current object
     *
     * @param data the object data
     * @return the object with the loaded
     * data
     */
    A loadData(final Map<String, Object> data);
}
