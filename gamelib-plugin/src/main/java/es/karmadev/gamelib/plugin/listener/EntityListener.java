package es.karmadev.gamelib.plugin.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import es.karmadev.gamelib.entity.EngineEntity;
import es.karmadev.gamelib.plugin.GameLibImpl;
import es.karmadev.gamelib.plugin.GamePlugin;
import es.karmadev.gamelib.plugin.impl.entity.GameLivingEntity;
import es.karmadev.gamelib.pos.Position3D;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

@Singleton
public class EntityListener implements Listener {

    @Inject
    private GamePlugin plugin;

    @Inject
    private GameLibImpl lib;

    private boolean spawning = false;

    private int index = 0;
    private final EntityType[] types = EntityType.values();
    private EntityType nextType;
    private LivingEntity previous = null;

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        LivingEntity entity = e.getEntity();
        if (!e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG)) return;

        World world = entity.getWorld();
        if (!spawning) {
            if (previous != null) previous.remove();

            spawning = true;
            Location location = entity.getLocation();
            e.setCancelled(true);

            LivingEntity ent;
            while ((ent = trySpawn(world, location)) == null) {
                System.out.println("Removed " + nextType);
            }

            LivingEntity finalEntity = ent;
            previous = finalEntity;
            GameLivingEntity gameLiving = new GameLivingEntity(lib, previous);

            previous.setAI(false);
            previous.setInvulnerable(true);
            previous.setSilent(true);
            previous.setGravity(false);

            location.add(0d, 0d, 5d);
            previous.setCustomName(nextType.name());
            previous.setCustomNameVisible(true);

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (finalEntity.isDead() || !finalEntity.isValid()) {
                        cancel();
                        return;
                    }

                    Position3D center = gameLiving.getHeadCenter();
                    double wSize = gameLiving.getHeadWidth();
                    double hSize = gameLiving.getHeadHeight();

                    showParticleBox(center, world, wSize, hSize);
                }
            }.runTaskTimer(plugin, 0, 1);


            spawning = false;
        }
    }

    private LivingEntity trySpawn(final World world, final Location location) {
        if (index == types.length - 1) {
            index = 0;
        }
        nextType = types[index++];

        try {
            Entity ent = world.spawn(location, nextType.getEntityClass());
            if (!(ent instanceof LivingEntity) || ent instanceof Ghast ||
                    ent instanceof Giant) {
                ent.remove();
                return null;
            }

            return (LivingEntity) ent;
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private void showParticleBox(Location center, World world, double width, double height) {
        double halfWidth = width / 2.0;
        double halfHeight = height / 2.0;

        for (double x = -halfWidth; x <= halfWidth; x += 0.1) {
            for (double y = -halfHeight; y <= halfHeight; y += 0.1) {
                for (double z = -halfWidth; z <= halfWidth; z += 0.1) {
                    Location particleLocation = center.clone().add(x, y, z);

                    world.spawnParticle(Particle.BUBBLE_POP, particleLocation, 10, 0, 0, 0, 0);
                }
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        Entity entity = e.getEntity();
        System.out.println("Killed!");
        if (!(entity instanceof Player)) {
            EngineEntity engineEntity = lib.getEntity(entity.getUniqueId());
            if (engineEntity != null) {
                lib.removeEntity(engineEntity);
            }
        }
    }
}
