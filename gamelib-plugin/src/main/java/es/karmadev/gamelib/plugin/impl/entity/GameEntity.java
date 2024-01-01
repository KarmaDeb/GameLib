package es.karmadev.gamelib.plugin.impl.entity;

import es.karmadev.gamelib.entity.EngineEntity;
import es.karmadev.gamelib.entity.NPCEntity;
import es.karmadev.gamelib.entity.human.HumanPlayer;
import es.karmadev.gamelib.serialization.FieldCollection;
import es.karmadev.gamelib.serialization.GenericField;
import es.karmadev.gamelib.serialization.KeyValueMap;
import es.karmadev.gamelib.plugin.GameLibImpl;
import es.karmadev.gamelib.pos.Position3D;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.*;

import static es.karmadev.gamelib.serialization.GenericField.*;

public class GameEntity implements EngineEntity {

    private final Entity entity;

    /**
     * Create a new engine entity
     *
     * @param entity the entity representing
     *               the engine entity
     */
    public GameEntity(final GameLibImpl lib, final Entity entity) {
        this.entity = entity;
        lib.addEntity(this);
    }

    /**
     * Get the entity ID
     *
     * @return the entity ID
     */
    @Override
    public int getId() {
        return entity.getEntityId();
    }

    /**
     * Get the entity name
     *
     * @return the entity name
     */
    @Override
    public String getName() {
        return entity.getName();
    }

    /**
     * Get the entity unique ID
     *
     * @return the entity unique ID
     */
    @Override
    public UUID getUniqueId() {
        return entity.getUniqueId();
    }

    /**
     * Get if the entity has the default
     * game AI enabled. This is usually
     * set to false on {@link NPCEntity} and
     * always false on {@link HumanPlayer}
     *
     * @return if the entity has AI
     */
    @Override
    public boolean hasAI() {
        if (entity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) entity;
            return living.hasAI();
        }

        return false;
    }

    /**
     * Get the entity position
     *
     * @return the entity position
     */
    @Override
    public Position3D getPosition() {
        return Position3D.fromLocation(entity.getLocation());
    }

    /**
     * Get the entity world
     *
     * @return the entity world
     */
    @Override
    public World getWorld() {
        return entity.getWorld();
    }

    /**
     * Move the entity to the specified
     * position
     *
     * @param other the position to move to
     * @return if the movement was allowed
     */
    @Override
    public boolean moveTo(final Location other) {
        return entity.teleport(other);
    }

    /**
     * Serialize the object
     *
     * @return the serialized object
     */
    @Override @SuppressWarnings("UnstableApiUsage")
    public FieldCollection serialize() {
        FieldCollection collection = new FieldCollection();

        Location position = entity.getLocation();
        Vector velocity = entity.getVelocity();

        collection.set(TYPE, entity.getType().getKey().toString());
        collection.set(textField("uuid", null), entity.getUniqueId().toString());

        collection.set(CUSTOM_NAME, entity.getCustomName());
        collection.set(CUSTOM_NAME_VISIBLE, entity.isCustomNameVisible());
        collection.set(FALL_DISTANCE, entity.getFallDistance());
        collection.set(GLOWING, entity.isGlowing());
        collection.set(GRAVITY, entity.hasGravity());
        collection.set(FREEZE_TICKS, entity.getFreezeTicks());
        collection.set(FIRE_TICKS, entity.getFireTicks());
        collection.set(MAX_FREEZE_TICKS, entity.getMaxFreezeTicks());
        collection.set(MAX_FIRE_TICKS, entity.getMaxFireTicks());
        collection.set(INVULNERABLE, entity.isInvulnerable());
        collection.set(VISIBLE_BY_DEFAULT, entity.isVisibleByDefault());
        collection.set(PERSISTENT, entity.isPersistent());
        collection.set(PORTAL_COOLDOWN, entity.getPortalCooldown());
        collection.set(ROTATION_YAW, position.getYaw());
        collection.set(ROTATION_PITCH, position.getPitch());
        collection.set(LOCATION_X, position.getX());
        collection.set(LOCATION_Y, position.getY());
        collection.set(LOCATION_Z, position.getZ());
        collection.set(LOCATION_WORLD, entity.getWorld().getUID().toString());
        collection.set(SILENT, entity.isSilent());
        collection.set(TICKS_LIVED, entity.getTicksLived());
        collection.set(VISUAL_FIRE, entity.isVisualFire());
        collection.set(VELOCITY_X, velocity.getX());
        collection.set(VELOCITY_Y, velocity.getY());
        collection.set(VELOCITY_Z, velocity.getZ());
        collection.set(VEHICLE, entity.isInsideVehicle());
        Entity vehicle = entity.getVehicle();
        if (vehicle != null) {
            collection.set(VEHICLE_ENTITY, vehicle.getUniqueId().toString());
        }

        if (entity instanceof LivingEntity) {
            FieldCollection living = wrapLivingEntity((LivingEntity) entity);
            collection.set(LIVING_ENTITY, living);
        }

        return collection;
    }

    private static FieldCollection wrapLivingEntity(final LivingEntity living) {
        FieldCollection collection = new FieldCollection();

        collection.set(HEALTH, living.getHealth());
        collection.set(REMAINING_AIR, living.getRemainingAir());
        collection.set(ARTIFICIAL_INTELLIGENCE, living.hasAI());
        collection.set(ARROW_COOLDOWN, living.getArrowCooldown());
        collection.set(GLIDING, living.isGliding());
        collection.set(COLLIDABLE, living.isCollidable());
        collection.set(ARROWS_IN_BODY, living.getArrowsInBody());
        collection.set(CAN_PICKUP, living.getCanPickupItems());
        collection.set(INVISIBLE, living.isInvisible());
        collection.set(LAST_DAMAGE, living.getLastDamage());

        try {
            Entity leash = living.getLeashHolder();
            collection.set(LEASH_HOLDER, leash.getUniqueId().toString());
        } catch (IllegalStateException ignored) {}

        collection.set(MAX_AIR, living.getMaximumAir());
        collection.set(MAX_NO_DAMAGE_TICKS, living.getMaximumNoDamageTicks());

        FieldCollection[] memory = wrapMemory(living);
        collection.set(MEMORY, memory);

        collection.set(NO_ACTION_TICKS, living.getNoActionTicks());
        collection.set(NO_DAMAGE_TICKS, living.getNoDamageTicks());
        collection.set(REMOVE_AWAY, living.getRemoveWhenFarAway());
        collection.set(SWIMMING, living.isSwimming());
        collection.set(ABSORPTION, living.getAbsorptionAmount());

        FieldCollection[] potionEffects = wrapEffects(living);
        collection.set(POTION_EFFECTS, potionEffects);

        FieldCollection[] collidableExemptions = living.getCollidableExemptions()
                .stream()
                .map((id) -> {
                    FieldCollection c = new FieldCollection();
                    c.set(textField("entity", null), id.toString());

                    return c;
                }).toArray(FieldCollection[]::new);
        collection.set(COLLIDE_EXEMPTIONS, collidableExemptions);

        FieldCollection[] attributes = wrapAttributes(living);
        collection.set(ATTRIBUTES, attributes);

        FieldCollection[] equipment = wrapEquipment(living);
        collection.set(EQUIPMENT, equipment);

        return collection;
    }

    private static FieldCollection[] wrapMemory(final LivingEntity living) {
        List<FieldCollection> memoryFields = new ArrayList<>();

        for (MemoryKey<?> key : MemoryKey.values()) {
            FieldCollection collection = new FieldCollection();

            String keyName = key.getKey().toString();
            Object value = living.getMemory(key);

            if (value == null) continue;

            collection.set(TYPE, keyName);

            if (value instanceof Location) {
                Location location = (Location) value;
                World world = location.getWorld();
                if (world == null) continue;

                collection.set(LOCATION_WORLD, world.getUID().toString());
                collection.set(LOCATION_X, location.getX());
                collection.set(LOCATION_Y, location.getY());
                collection.set(LOCATION_Z, location.getZ());
                collection.set(ROTATION_YAW, location.getYaw());
                collection.set(ROTATION_PITCH, location.getPitch());
            } else if (value instanceof UUID) {
                collection.set(textField("uuid", null), value.toString());
            } else if (value instanceof Boolean) {
                collection.set(booleanField("boolean", null), (Boolean) value);
            } else if (value instanceof Long) {
                collection.set(longField("long", null), (Long) value);
            } else if (value instanceof Integer) {
                collection.set(integerField("integer", null), (Integer) value);
            }

            memoryFields.add(collection);
        }

        return memoryFields.toArray(new FieldCollection[0]);
    }

    private static FieldCollection[] wrapEffects(final LivingEntity living) {
        List<FieldCollection> collections = new ArrayList<>();

        for (PotionEffect effect : living.getActivePotionEffects()) {
            FieldCollection collection = new FieldCollection();

            collection.set(TYPE, effect.getType().getKey().toString());
            collection.set(AMPLIFIER, effect.getAmplifier());
            collection.set(DURATION, effect.getDuration());
            collection.set(IS_AMBIENT, effect.isAmbient());
            collection.set(IS_INFINITE, effect.isInfinite());
            collection.set(HAS_PARTICLES, effect.hasParticles());
            collection.set(HAS_ICON, effect.hasIcon());

            collections.add(collection);
        }

        return collections.toArray(new FieldCollection[0]);
    }

    private static FieldCollection[] wrapAttributes(final LivingEntity entity) {
        List<FieldCollection> collections = new ArrayList<>();

        for (Attribute attribute : Attribute.values()) {
            FieldCollection collection = new FieldCollection();

            AttributeInstance instance = entity.getAttribute(attribute);
            if (instance == null) continue;

            collection.set(TYPE, attribute.getKey().toString());
            collection.set(BASE_VALUE, instance.getBaseValue());

            collections.add(collection);
        }

        return collections.toArray(new FieldCollection[0]);
    }

    private static FieldCollection[] wrapEquipment(final LivingEntity entity) {
        List<FieldCollection> collections = new ArrayList<>();

        EntityEquipment equipment = entity.getEquipment();
        if (equipment == null) return new FieldCollection[0];

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack item = equipment.getItem(slot);
            if (item.getType().isAir()) continue;

            FieldCollection collection = new FieldCollection();
            collection.set(textField("slot", null), slot.name());
            collection.set(SERIALIZABLE("item"), KeyValueMap.wrap(item.serialize()));

            collections.add(collection);
        }

        return collections.toArray(new FieldCollection[0]);
    }

    /**
     * Load the data and put it in
     * the current object
     *
     * @param data the object data
     * @return the object with the loaded
     * data
     */
    @Override
    public EngineEntity loadData(final FieldCollection data) {
        for (GenericField<?, ?> field : values()) {
            Object value = data.get(field);
            if (value == null || field.set(entity, value)) continue;

            String key = field.getKey();
            switch (key.toLowerCase()) {
                case "living_entity":
                    if (!(entity instanceof LivingEntity)) continue;

                    FieldCollection livingEntityCollection = (FieldCollection) value;
                    loadData(livingEntityCollection);
                    break;
                case "memory":
                    if (!(entity instanceof LivingEntity)) continue;

                    FieldCollection[] memory = (FieldCollection[]) value;
                    unwrapMemory(memory);
                    break;
                case "potion_effects":
                    if (!(entity instanceof LivingEntity)) continue;

                    FieldCollection[] effects = (FieldCollection[]) value;
                    unwrapEffects(effects);
                    break;
                case "collide_exemptions":
                    if (!(entity instanceof LivingEntity)) continue;

                    FieldCollection[] collides = (FieldCollection[]) value;
                    unwrapCollides(collides);
                    break;
                case "attributes":
                    if (!(entity instanceof LivingEntity)) continue;

                    FieldCollection[] attributes = (FieldCollection[]) value;
                    unwrapAttributes(attributes);
                    break;
                case "equipment":
                    if (!(entity instanceof LivingEntity)) continue;

                    FieldCollection[] equipment = (FieldCollection[]) value;
                    unwrapEquipment(equipment);
                    break;
            }
        }

        return this;
    }

    @SuppressWarnings("unchecked")
    private void unwrapMemory(final FieldCollection[] data) {
        LivingEntity living = (LivingEntity) entity;

        for (FieldCollection field : data) {
            String rawType = field.get(textField("type", null));
            MemoryKey<?> mem = MemoryKey.getByKey(NamespacedKey.fromString(rawType));

            Integer in32 = field.get(integerField("integer", null));
            if (in32 != null) {
                living.setMemory((MemoryKey<Integer>) mem, in32);
                continue;
            }

            Long int64 = field.get(longField("long", null));
            if (int64 != null) {
                living.setMemory((MemoryKey<Long>) mem, int64);
                continue;
            }

            Boolean bool = field.get(booleanField("boolean", null));
            if (bool != null) {
                living.setMemory((MemoryKey<Boolean>) mem, bool);
                continue;
            }

            String uniqueId = field.get(textField("uuid", null));
            if (uniqueId != null) {
                try {
                    living.setMemory((MemoryKey<UUID>) mem, UUID.fromString(uniqueId));
                } catch (IllegalArgumentException ignored) {}
                continue;
            }

            String worldId = field.get(LOCATION_WORLD);
            Double x = field.get(LOCATION_X);
            Double y = field.get(LOCATION_Y);
            Double z = field.get(LOCATION_Z);
            Float yaw = field.get(ROTATION_YAW);
            Float pitch = field.get(ROTATION_PITCH);

            if (worldId == null || x == null || y == null ||
                    z == null || yaw == null || pitch == null) continue;

            try {
                UUID worldUID = UUID.fromString(worldId);
                World world = Bukkit.getWorld(worldUID);

                if (world == null) continue;
                Location location = new Location(world, x, y, z);
                location.setYaw(yaw);
                location.setPitch(pitch);

                living.setMemory((MemoryKey<Location>) mem, location);
            } catch (IllegalStateException ignored) {}
        }
    }

    private void unwrapEffects(final FieldCollection[] data) {
        LivingEntity living = (LivingEntity) entity;

        for (FieldCollection field : data) {
            String rawType = field.get(TYPE);
            Integer amplifier = field.get(AMPLIFIER);
            Integer duration = field.get(DURATION);
            Boolean ambient = field.get(IS_AMBIENT);
            Boolean infinite = field.get(IS_INFINITE);
            Boolean particles = field.get(HAS_PARTICLES);
            Boolean icon = field.get(HAS_ICON);

            if (rawType == null || amplifier == null || duration == null ||
                    ambient == null || infinite == null || particles == null ||
                    icon == null) continue;
            if (infinite) {
                duration = PotionEffect.INFINITE_DURATION;
            }

            PotionEffectType type = Registry.EFFECT.get(NamespacedKey.fromString(rawType));
            if (type == null) continue;

            PotionEffect effect = new PotionEffect(type, duration, amplifier, ambient, particles, icon);
            living.addPotionEffect(effect);
        }
    }

    private void unwrapCollides(final FieldCollection[] data) {
        LivingEntity living = (LivingEntity) entity;

        for (FieldCollection field : data) {
            String entityID = field.get(textField("entity", null));
            if (entityID == null) continue;

            try {
                UUID id = UUID.fromString(entityID);
                living.getCollidableExemptions().add(id);
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void unwrapAttributes(final FieldCollection[] data) {
        LivingEntity living = (LivingEntity) entity;

        for (FieldCollection field : data) {
            String rawType = field.get(TYPE);
            Double value = field.get(BASE_VALUE);

            if (rawType == null || value == null) continue;
            Attribute attribute = Registry.ATTRIBUTE.get(NamespacedKey.fromString(rawType));
            if (attribute == null) continue;

            AttributeInstance instance = living.getAttribute(attribute);
            if (instance == null) continue;

            instance.setBaseValue(value);
        }
    }

    private void unwrapEquipment(final FieldCollection[] data) {
        LivingEntity living = (LivingEntity) entity;
        EntityEquipment equipment = living.getEquipment();
        if (equipment == null) return;

        for (FieldCollection field : data) {
            String rawSlot = field.get(textField("slot", null));
            KeyValueMap serializedItem = field.get(SERIALIZABLE("item"));

            if (rawSlot == null || serializedItem == null) continue;
            try {
                EquipmentSlot slot = EquipmentSlot.valueOf(rawSlot);
                Map<String, Object> resolved = new HashMap<>();

                for (String key : serializedItem.getKeys()) {
                    resolved.put(key, serializedItem.getValue(key));
                }

                ItemStack loaded = ItemStack.deserialize(resolved);
                equipment.setItem(slot, loaded);
            } catch (IllegalArgumentException ignored) {}
        }
    }
}
