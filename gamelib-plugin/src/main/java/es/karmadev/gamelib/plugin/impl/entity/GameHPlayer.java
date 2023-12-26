package es.karmadev.gamelib.plugin.impl.entity;

import com.google.inject.Inject;
import es.karmadev.gamelib.Condition;
import es.karmadev.gamelib.entity.EngineEntity;
import es.karmadev.gamelib.entity.human.HumanPlayer;
import es.karmadev.gamelib.plugin.GameLibImpl;
import es.karmadev.gamelib.pos.Position3D;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameHPlayer extends HumanPlayer {

    @Inject
    private GameLibImpl lib;

    private final Player player;
    private final Map<Condition<?>, Object> conditions = new ConcurrentHashMap<>();

    /**
     * Create a new engine entity
     *
     * @param player the player which represents
     *               this game human player
     */
    public GameHPlayer(final Player player) {
        super(player.getName(), player.getUniqueId());
        this.player = player;

        lib.addEntity(this);
    }

    /**
     * Get the entity ID
     *
     * @return the entity ID
     */
    @Override
    public int getId() {
        return player.getEntityId();
    }

    /**
     * Get the entity position
     *
     * @return the entity position
     */
    @Override
    public Position3D getPosition() {
        return Position3D.fromLocation(player.getLocation());
    }

    /**
     * Get the entity world
     *
     * @return the entity world
     */
    @Override
    public World getWorld() {
        return player.getWorld();
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
        return player.teleport(other);
    }

    /**
     * Send a message to the player
     *
     * @param message      the message to send to the
     *                     player
     * @param replacements the message replacements
     */
    @Override
    public void sendMessage(final @NotNull String message, final Object... replacements) {
        String parsed = build(message, replacements);
        player.sendMessage(
                ChatColor.translateAlternateColorCodes('&', parsed)
        );
    }

    /**
     * Send a title to the player
     *
     * @param title    the title
     * @param subtitle the subtitle
     * @param fadeIn   the time to fade in, defaults to 10
     * @param keep     the time to show, defaults to 70
     * @param fadeOut  the time fade out, defaults to 20
     */
    @Override
    public void sendTitle(final @Nullable String title, final @Nullable String subtitle, final int fadeIn, final int keep, final int fadeOut) {
        String t1 = null;
        String t2 = null;
        if (title != null) {
            t1 = ChatColor.translateAlternateColorCodes('&', title);
        }
        if (subtitle != null) {
            t2 = ChatColor.translateAlternateColorCodes('&', subtitle);
        }

        player.sendTitle(t1, t2, fadeIn, keep, fadeOut);
    }

    /**
     * Send an actionbar to the player
     *
     * @param actionbar the actionbar to send
     * @param replacements the message replacements
     */
    @Override
    public void sendActionbar(final @NotNull String actionbar, final Object... replacements) {
        String parsed = build(actionbar, replacements);
        player.spigot().sendMessage(
                ChatMessageType.ACTION_BAR,
                TextComponent.fromLegacyText(
                        ChatColor.translateAlternateColorCodes('&', parsed)
                )
        );
    }

    /**
     * Get a player condition
     *
     * @param condition the condition to
     *                  retrieve
     * @param def       the default condition
     * @return the condition
     */
    @Override
    public <T> T getCondition(final Condition<T> condition, final T def) {
        if (condition == null) return def;
        if (condition.equals(Condition.GL_COND_HEALTH)) {
            return condition.cast(player.getHealth());
        }

        Object value = conditions.getOrDefault(condition, def);
        return condition.cast(value);
    }

    /**
     * Set a player condition
     *
     * @param condition the condition to set
     * @param value     the condition value
     */
    @Override
    public <T> void setCondition(final Condition<T> condition, final @Nullable T value) {
        if (condition == null) return;
        if (value == null) {
            conditions.remove(condition);
            return;
        }

        conditions.put(condition, value);
    }

    private String build(final String message, final Object... replacements) {
        StringBuilder builder = new StringBuilder();
        char[] chars = message.toCharArray();

        boolean building = false;
        boolean escape = false;

        StringBuilder current = new StringBuilder();
        for (char character : chars) {
            if (character == '\\') {
                escape = !escape;
            }

            if (building) {
                if (character == '}') {
                    building = false;
                    int num = Integer.parseInt(current.toString());
                    current = new StringBuilder();

                    if (num > 0 && num < replacements.length) {
                        Object replacement = replacements[num - 1];
                        builder.append(replacement);
                    } else {
                        builder.append("<missing:{").append(num).append("}>");
                    }
                } else {
                    if (!Character.isDigit(character)) {
                        escape = false;
                        building = false;
                        builder.append(current);
                        current = new StringBuilder();
                    } else {
                        current.append(character);
                    }
                }
            } else {
                if (character == '{' && !escape) {
                    building = true;
                    continue;
                }

                if (!escape) {
                    builder.append(character);
                }
                escape = false;
            }
        }

        return builder.toString();
    }

    /**
     * Serialize the object
     *
     * @return the serialized object
     */
    @Override
    public Map<String, Object> serialize() {
        throw new UnsupportedOperationException("Player does not support serialization!");
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
        throw new UnsupportedOperationException("Player does not support serialization!");
    }
}
