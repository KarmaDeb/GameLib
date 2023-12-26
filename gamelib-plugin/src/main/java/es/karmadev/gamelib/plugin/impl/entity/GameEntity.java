package es.karmadev.gamelib.plugin.impl.entity;

import com.google.inject.Inject;
import es.karmadev.gamelib.entity.EngineEntity;
import es.karmadev.gamelib.entity.NPCEntity;
import es.karmadev.gamelib.entity.human.HumanPlayer;
import es.karmadev.gamelib.plugin.GameLibImpl;
import es.karmadev.gamelib.pos.Position3D;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.stream.Collectors;

public class GameEntity extends EngineEntity {

    @Inject
    private GameLibImpl lib;

    private final Entity entity;

    /**
     * Create a new engine entity
     *
     * @param entity the entity representing
     *               the engine entity
     */
    public GameEntity(final Entity entity) {
        super(entity.getName(), entity.getUniqueId());
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
    @Override
    public Map<String, Object> serialize() {
        Location position = entity.getLocation();
        Vector velocity = entity.getVelocity();

        Map<String, Object> data = new HashMap<>();
        data.put("identifier", entity.getUniqueId());
        data.put("type", entity.getType().getKey());
        data.put("name", entity.getName());
        data.put("custom_name", entity.getCustomName());
        data.put("custom_name_visible", entity.isCustomNameVisible());
        data.put("fall_distance", entity.getFallDistance());
        data.put("glowing", entity.isGlowing());
        data.put("gravity", entity.hasGravity());
        data.put("freeze_ticks", entity.getFreezeTicks());
        data.put("fire_ticks", entity.getFireTicks());
        data.put("max_freeze_ticks", entity.getMaxFreezeTicks());
        data.put("max_fire_ticks", entity.getMaxFireTicks());
        data.put("invulnerable", entity.isInvulnerable());
        data.put("visible_by_default", entity.isVisibleByDefault());
        data.put("persistent", entity.isPersistent());
        data.put("portal_cooldown", entity.getPortalCooldown());
        data.put("rotation_yaw", position.getYaw());
        data.put("rotation_pitch", position.getPitch());
        data.put("location_x", position.getX());
        data.put("location_y", position.getY());
        data.put("location_z", position.getZ());
        data.put("location_world", entity.getWorld().getUID().toString());
        data.put("silent", entity.isSilent());
        data.put("ticks_lived", entity.getTicksLived());
        data.put("visual_fire", entity.isVisualFire());
        data.put("velocity_x", velocity.getX());
        data.put("velocity_y", velocity.getY());
        data.put("velocity_z", velocity.getZ());
        data.put("vehicle", entity.isInsideVehicle());
        Entity vehicle = entity.getVehicle();
        if (vehicle != null) {
            data.put("vehicle_entity", vehicle.getUniqueId().toString());
        }

        if (entity instanceof LivingEntity) {
            Map<String, Object>  living = mapLivingEntity((LivingEntity) entity);
            data.put("living_entity", living);
        }

        return data;
    }

    private static Map<String, Object> mapLivingEntity(final LivingEntity living) {
        Map<String, Object> data = new HashMap<>();

        data.put("ai", living.hasAI());
        data.put("arrow_cooldown", living.getArrowCooldown());
        data.put("gliding", living.isGliding());
        data.put("collidable", living.isCollidable());
        data.put("arrows_in_body", living.getArrowsInBody());
        data.put("can_pickup", living.getCanPickupItems());
        data.put("invisible", living.isInvisible());
        data.put("last_damage", living.getLastDamage());

        try {
            Entity leash = living.getLeashHolder();
            data.put("leash_holder", leash.getUniqueId().toString());
        } catch (IllegalStateException ignored) {}

        data.put("max_air", living.getMaximumAir());
        data.put("max_no_damage_ticks", living.getMaximumNoDamageTicks());

        Map<String, Object> memory = getMemory(living);
        data.put("memory", memory);

        data.put("no_action_ticks", living.getNoActionTicks());
        data.put("no_damage_ticks", living.getNoDamageTicks());
        data.put("air", living.getRemainingAir());
        data.put("remove_far_away", living.getRemoveWhenFarAway());
        data.put("swimming", living.isSwimming());
        data.put("absorption_amount", living.getAbsorptionAmount());

        List<Map<String, Object>> potionEffects = getPotionEffects(living);
        data.put("potion_effects", potionEffects);

        Collection<String> collidableExemptions = living.getCollidableExemptions().stream().map(UUID::toString)
                .collect(Collectors.toList());
        data.put("collide_exemptions", collidableExemptions);

        List<Map<String, Object>> attributes = getAttributes(living);
        data.put("attributes", attributes);

        Map<String, Map<String, Object>> equipment = getEquipment(living);
        data.put("equipment", equipment);

        return data;
    }

    private static Map<String, Object> getMemory(final LivingEntity living) {
        Map<String, Object> memory = new HashMap<>();

        for (MemoryKey<?> key : MemoryKey.values()) {
            String keyName = key.getKey().toString();
            Object value = living.getMemory(key);

            if (value == null) continue;

            if (value instanceof Location) {
                Location location = (Location) value;
                Map<String, Object> locationData = new HashMap<>();

                World world = location.getWorld();
                if (world == null) continue;

                locationData.put("world", world.getUID().toString());
                locationData.put("location_x", location.getX());
                locationData.put("location_y", location.getY());
                locationData.put("location_z", location.getZ());
                locationData.put("location_yaw", location.getYaw());
                locationData.put("location_pitch", location.getPitch());

                memory.put(keyName, locationData);
                continue;
            }

            if (value instanceof UUID) {
                memory.put(keyName, value.toString());
                continue;
            }

            memory.put(keyName, value);
        }

        return memory;
    }

    private static List<Map<String, Object>> getPotionEffects(final LivingEntity living) {
        List<Map<String, Object>> potionEffects = new ArrayList<>();
        for (PotionEffect effect : living.getActivePotionEffects()) {
            Map<String, Object> potionEffect = new HashMap<>();
            potionEffect.put("type", effect.getType().getKey().toString());
            potionEffect.put("amplifier", effect.getAmplifier());
            potionEffect.put("duration", effect.getDuration());

            potionEffects.add(potionEffect);
        }
        return potionEffects;
    }

    private static List<Map<String, Object>> getAttributes(final LivingEntity entity) {
        List<Map<String, Object>> attributes = new ArrayList<>();
        for (Attribute attribute : Attribute.values()) {
            Map<String, Object> attributeData = new HashMap<>();

            AttributeInstance instance = entity.getAttribute(attribute);
            if (instance == null) continue;

            attributeData.put("type", attribute.getKey().toString());
            attributeData.put("base_value", instance.getBaseValue());

            attributes.add(attributeData);
        }

        return attributes;
    }

    private static Map<String, Map<String, Object>> getEquipment(final LivingEntity entity) {
        Map<String, Map<String, Object>> equipmentData = new HashMap<>();

        EntityEquipment equipment = entity.getEquipment();
        if (equipment == null) return equipmentData;

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack item = equipment.getItem(slot);
            if (item.getType().isAir()) continue;

            equipmentData.put(slot.name(), item.serialize());
        }

        return equipmentData;
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
    public EngineEntity loadData(final Map<String, Object> data) {
        return null;
    }
}
