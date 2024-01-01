package es.karmadev.gamelib.serialization;

import org.bukkit.Location;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Objects;

/**
 * Represents an entity field
 * @param <T> the field type
 * @param <A> the setter field type
 */
@SuppressWarnings("unused")
public final class GenericField<T, A> {


    public final static GenericField<String, Entity> CUSTOM_NAME = textField("custom_name", "setCustomName");
    public final static GenericField<Boolean, Entity> CUSTOM_NAME_VISIBLE = booleanField("custom_name_visible", "setCustomNameVisible");
    public final static GenericField<Float, Entity> FALL_DISTANCE = floatField("fall_distance", "setFallDistance");
    public final static GenericField<Boolean, Entity> GLOWING = booleanField("glowing", "setGlowing");
    public final static GenericField<Boolean, Entity> GRAVITY = booleanField("gravity", "setGravity");
    public final static GenericField<Integer, Entity> FREEZE_TICKS = integerField("freeze_ticks", "setFreezeTicks");
    public final static GenericField<Integer, Entity> FIRE_TICKS = integerField("fire_ticks", "setFireTicks");
    public final static GenericField<Integer, Entity> MAX_FREEZE_TICKS = integerField("max_freeze_ticks", "setMaxFreezeTicks");
    public final static GenericField<Integer, Entity> MAX_FIRE_TICKS = integerField("max_fire_ticks", "setMaxFireTicks");
    public final static GenericField<Boolean, Entity> INVULNERABLE = booleanField("invulnerable", "setInvulnerable");
    public final static GenericField<Boolean, Entity> VISIBLE_BY_DEFAULT = booleanField("visible_by_default", "setVisibleByDefault");
    public final static GenericField<Boolean, Entity> PERSISTENT = booleanField("persistent", "setPersistent");
    public final static GenericField<Integer, Entity> PORTAL_COOLDOWN = integerField("portal_cooldown", "setPortalCooldown");
    public final static GenericField<Float, Location> ROTATION_YAW = floatField("rotation_yaw", "setYaw");
    public final static GenericField<Float, Location> ROTATION_PITCH = floatField("rotation_pitch", "setPitch");
    public final static GenericField<Double, Location> LOCATION_X = doubleField("location_x", "setX");
    public final static GenericField<Double, Location> LOCATION_Y = doubleField("location_y", "setY");
    public final static GenericField<Double, Location> LOCATION_Z = doubleField("location_z", "setZ");
    public final static GenericField<String, Location> LOCATION_WORLD = textField("location_world", "setWorld");
    public final static GenericField<Boolean, Entity> SILENT = booleanField("silent", "setSilent");
    public final static GenericField<Integer, Entity> TICKS_LIVED = integerField("ticks_lived", "setTicksLived");
    public final static GenericField<Boolean, Entity> VISUAL_FIRE = booleanField("visual_fire", "setVisualFire");
    public final static GenericField<Double, Vector> VELOCITY_X = doubleField("velocity_x", "setX");
    public final static GenericField<Double, Vector> VELOCITY_Y = doubleField("velocity_y", "setY");
    public final static GenericField<Double, Vector> VELOCITY_Z = doubleField("velocity_z", "setZ");
    public final static GenericField<Boolean, Entity> VEHICLE = booleanField("vehicle", null);
    public final static GenericField<String, Entity> VEHICLE_ENTITY = textField("vehicle_entity", null);
    public final static GenericField<FieldCollection, Entity> LIVING_ENTITY = of("living_entity", FieldCollection.class, null);
    public final static GenericField<Boolean, Entity> ARTIFICIAL_INTELLIGENCE = booleanField("ai", "setAI");
    public final static GenericField<Integer, Entity> ARROW_COOLDOWN = integerField("arrow_cooldown", "setArrowCooldown");
    public final static GenericField<Boolean, Entity> GLIDING = booleanField("gliding", "setGliding");
    public final static GenericField<Boolean, Entity> COLLIDABLE = booleanField("collidable", "setCollidable");
    public final static GenericField<Integer, Entity> ARROWS_IN_BODY = integerField("arrows_in_body", "setArrowsInBody");
    public final static GenericField<Boolean, Entity> CAN_PICKUP = booleanField("can_pickup", "setCanPickup");
    public final static GenericField<Boolean, Entity> INVISIBLE = booleanField("invisible", "setInvisible");
    public final static GenericField<Double, Entity> LAST_DAMAGE = doubleField("last_damage", "setLastDamage");
    public final static GenericField<Double, Entity> HEALTH = doubleField("health", "setHealth");
    public final static GenericField<Integer, Entity> REMAINING_AIR = integerField("air", "setRemainingAir");
    public final static GenericField<String, Entity> LEASH_HOLDER = textField("leash_holder", null);
    public final static GenericField<Integer, Entity> MAX_AIR = integerField("max_air", "setMaximumAir");
    public final static GenericField<Integer, Entity> MAX_NO_DAMAGE_TICKS = integerField("max_no_damage_ticks", "setMaximumNoDamageTicks");
    public final static GenericField<FieldCollection[], Entity> MEMORY = of("memory", FieldCollection[].class, null);
    public final static GenericField<Integer, Entity> NO_ACTION_TICKS = integerField("no_action_ticks", "setNoActionTicks");
    public final static GenericField<Integer, Entity> NO_DAMAGE_TICKS = integerField("no_damage_ticks", "setNoDamageTicks");
    public final static GenericField<Boolean, Entity> REMOVE_AWAY = booleanField("remove_far_away", "setRemoveWhenFarAway");
    public final static GenericField<Boolean, Entity> SWIMMING = booleanField("swimming", "setSwimming");
    public final static GenericField<Double, Entity> ABSORPTION = doubleField("absorption_amount", "setAbsorptionAmount");
    public final static GenericField<FieldCollection[], Entity> POTION_EFFECTS = of("potion_effects", FieldCollection[].class, null);
    public final static GenericField<FieldCollection[], Entity> COLLIDE_EXEMPTIONS = of("collide_exemptions", FieldCollection[].class, null);
    public final static GenericField<FieldCollection[], Entity> ATTRIBUTES = of("attributes", FieldCollection[].class, null);
    public final static GenericField<FieldCollection[], Entity> EQUIPMENT = of("equipment", FieldCollection[].class, null);
    public final static GenericField<String, PotionEffect> TYPE = textField("type", null);
    public final static GenericField<Integer, PotionEffect> AMPLIFIER = integerField("amplifier", null);
    public final static GenericField<Integer, PotionEffect> DURATION = integerField("duration", null);
    public final static GenericField<Boolean, PotionEffect> IS_AMBIENT = booleanField("ambient", null);
    public final static GenericField<Boolean, PotionEffect> IS_INFINITE = booleanField("infinite", null);
    public final static GenericField<Boolean, PotionEffect> HAS_PARTICLES = booleanField("particles", null);
    public final static GenericField<Boolean, PotionEffect> HAS_ICON = booleanField("icon", null);
    public final static GenericField<Double, AttributeInstance> BASE_VALUE = of("base_value", Double.class, "setBaseValue");
    public static GenericField<KeyValueMap, Object> SERIALIZABLE(final String name) {
        return of(name, KeyValueMap.class, null);
    }

    /**
     * Get all the default generic
     * fields
     *
     * @return the default generic fields
     */
    public static GenericField<?, ?>[] values() {
        return new GenericField<?, ?>[]{
                CUSTOM_NAME,
                CUSTOM_NAME_VISIBLE,
                FALL_DISTANCE,
                GLOWING,
                GRAVITY,
                FREEZE_TICKS,
                FIRE_TICKS,
                MAX_FREEZE_TICKS,
                MAX_FIRE_TICKS,
                INVULNERABLE,
                VISIBLE_BY_DEFAULT,
                PERSISTENT,
                PORTAL_COOLDOWN,
                ROTATION_YAW,
                ROTATION_PITCH,
                LOCATION_X,
                LOCATION_Y,
                LOCATION_Z,
                LOCATION_WORLD,
                SILENT,
                TICKS_LIVED,
                VISUAL_FIRE,
                VELOCITY_X,
                VELOCITY_Y,
                VELOCITY_Z,
                VEHICLE,
                VEHICLE_ENTITY,
                LIVING_ENTITY,
                ARTIFICIAL_INTELLIGENCE,
                ARROW_COOLDOWN,
                GLIDING,
                COLLIDABLE,
                ARROWS_IN_BODY,
                CAN_PICKUP,
                INVISIBLE,
                LAST_DAMAGE,
                HEALTH,
                REMAINING_AIR,
                LEASH_HOLDER,
                MAX_AIR,
                MAX_NO_DAMAGE_TICKS,
                MEMORY,
                NO_ACTION_TICKS,
                NO_DAMAGE_TICKS,
                REMOVE_AWAY,
                SWIMMING,
                ABSORPTION,
                POTION_EFFECTS,
                COLLIDE_EXEMPTIONS,
                ATTRIBUTES,
                EQUIPMENT,
                TYPE,
                AMPLIFIER,
                DURATION,
                BASE_VALUE
        };
    }

    private final String key;
    private final Class<T> type;
    private final String setterMethod;

    /**
     * Create a new field
     *
     * @param key the field key
     * @param type the field type
     * @param setter the field setter method
     */
    private GenericField(final String key, final Class<T> type, final String setter) {
        this.key = key;
        this.type = type;
        this.setterMethod = setter;
    }

    /**
     * Get the field key
     *
     * @return the field key
     */
    public String getKey() {
        return key;
    }

    /**
     * Get the field type
     *
     * @return the field type
     */
    public Class<T> getType() {
        return type;
    }

    /**
     * Set the field value to the
     * entity
     *
     * @param who the entity to apply for
     * @param value the value to apply
     * @return if the application was successful
     */
    public boolean set(final Object who, final Object value) {
        Method method = getMethod(who.getClass());
        if (method == null) return false;

        try {
            method.invoke(who, value);
            return true;
        } catch (ReflectiveOperationException ignored) {}
        return false;
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
        return Objects.hash(key, type);
    }

    /**
     * Create a new entity field
     *
     * @param key the field key
     * @param type the field type
     * @param setter the field setter method
     * @return the entity field
     * @param <T> the field value type
     */
    public static <T, A> GenericField<T, A> of(final String key, final Class<T> type, final String setter) {
        return new GenericField<>(key, type, setter);
    }

    /**
     * Create a new text entity
     * field
     *
     * @param key the field key
     * @param setter the field setter method
     * @return the entity field
     */
    public static <A> GenericField<String, A> textField(final String key, final String setter) {
        return of(key, String.class, setter);
    }

    /**
     * Create a new byte entity
     * field
     *
     * @param key the field key
     * @param setter the field setter method
     * @return the entity field
     */
    public static <A> GenericField<Byte, A> byteField(final String key, final String setter) {
        return of(key, Byte.TYPE, setter);
    }

    /**
     * Create a new short entity
     * field
     *
     * @param key the field key
     * @param setter the field setter method
     * @return the entity field
     */
    public static <A> GenericField<Short, A> shortField(final String key, final String setter) {
        return of(key, Short.TYPE, setter);
    }

    /**
     * Create a new integer entity
     * field
     *
     * @param key the field key
     * @param setter the field setter method
     * @return the entity field
     */
    public static <A> GenericField<Integer, A> integerField(final String key, final String setter) {
        return of(key, Integer.TYPE, setter);
    }

    /**
     * Create a new long entity
     * field
     *
     * @param key the field key
     * @param setter the field setter method
     * @return the entity field
     */
    public static <A> GenericField<Long, A> longField(final String key, final String setter) {
        return of(key, Long.TYPE, setter);
    }

    /**
     * Create a new float entity
     * field
     *
     * @param key the field key
     * @param setter the field setter method
     * @return the entity field
     */
    public static <A> GenericField<Float, A> floatField(final String key, final String setter) {
        return of(key, Float.TYPE, setter);
    }

    /**
     * Create a new double entity
     * field
     *
     * @param key the field key
     * @param setter the field setter method
     * @return the entity field
     */
    public static <A> GenericField<Double, A> doubleField(final String key, final String setter) {
        return of(key, Double.TYPE, setter);
    }

    /**
     * Create a new boolean entity
     * field
     *
     * @param key the field key
     * @param setter the field setter method
     * @return the entity field
     */
    public static <A> GenericField<Boolean, A> booleanField(final String key, final String setter) {
        return of(key, Boolean.TYPE, setter);
    }

    private Method getMethod(final Class<?> from) {
        if (setterMethod == null) return null;
        try {
            try {
                return from.getDeclaredMethod(setterMethod, type);
            } catch (NoSuchMethodException ex) {
                return from.getMethod(setterMethod, type);
            }
        } catch (ReflectiveOperationException ignored) {}

        return null;
    }
}
