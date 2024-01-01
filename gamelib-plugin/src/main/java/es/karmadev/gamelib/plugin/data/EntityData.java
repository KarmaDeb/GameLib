package es.karmadev.gamelib.plugin.data;

import com.google.gson.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import es.karmadev.gamelib.plugin.GamePlugin;
import org.bukkit.entity.EntityType;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

@Singleton
public class EntityData {

    private final JsonObject data;

    @Inject
    public EntityData(final GamePlugin plugin) {
        JsonObject tempData = new JsonObject();
        try (InputStream entityData = plugin.getResource("entity_data.json")) {
            if (entityData == null) throw new IllegalStateException("Missing entity data");

            try (InputStreamReader isr = new InputStreamReader(entityData)) {
                Gson gson = new GsonBuilder().create();
                tempData = gson.fromJson(isr, JsonObject.class);
            }
        } catch (IOException ex) {
            plugin.getLogger().log(Level.INFO, ex, () -> "An exception occurred while reading entity data");
        }

        data = tempData;
    }

    /**
     * Get an entity head width
     *
     * @param type the entity type
     * @return the entity head width
     */
    public double getHeadWidth(final EntityType type) {
        JsonObject entityData = getData(type);
        return getDouble(entityData, "width", -1d);
    }

    /**
     * Get an entity head height
     *
     * @param type the entity type
     * @return the entity head height
     */
    public double getHeadHeight(final EntityType type) {
        JsonObject entityData = getData(type);
        return getDouble(entityData, "height", -1d);
    }

    /**
     * Get the entity head X offset
     *
     * @param type the entity type
     * @return the entity head X offset
     */
    public double getXOffset(final EntityType type) {
        JsonObject entityData = getData(type);
        if (entityData == null) return 0d;
        if (isNotObject(entityData, "offset")) return 0d;

        JsonObject offset = entityData.getAsJsonObject("offset");
        return getDouble(offset, "x", 0d);
    }

    /**
     * Get the entity head Y offset
     *
     * @param type the entity type
     * @return the entity head Y offset
     */
    public double getYOffset(final EntityType type) {
        JsonObject entityData = getData(type);
        if (entityData == null) return 0d;
        if (isNotObject(entityData, "offset")) return 0d;

        JsonObject offset = entityData.getAsJsonObject("offset");
        return getDouble(offset, "y", 0d);
    }

    private JsonObject getData(final EntityType type) {
        String key = type.name();
        if (isNotObject(data, key)) return null;

        return data.get(key).getAsJsonObject();
    }

    private boolean isNotObject(final JsonObject from, final String key) {
        if (from != null && from.has(key)) {
            return !from.get(key).isJsonObject();
        }

        return true;
    }

    private double getDouble(final JsonObject from, final String key, final double def) {
        if (from != null && from.has(key)) {
            JsonElement element = from.get(key);
            if (!element.isJsonPrimitive()) return def;

            JsonPrimitive primitive = from.getAsJsonPrimitive(key);
            if (!primitive.isNumber()) return def;

            return primitive.getAsDouble();
        }

        return def;
    }
}
