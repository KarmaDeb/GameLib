package es.karmadev.gamelib.plugin.impl.entity;

import es.karmadev.gamelib.entity.human.HumanPlayer;
import es.karmadev.gamelib.pos.Position3D;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class GameHPlayer extends HumanPlayer {

    private final Player player;

    /**
     * Create a new engine entity
     *
     * @param player the player which represents
     *               this game human player
     */
    public GameHPlayer(final Player player) {
        super(player.getName(), player.getUniqueId());
        this.player = player;
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
     * Move the entity to the specified
     * position
     *
     * @param other the position to move to
     * @return if the movement was allowed
     */
    @Override
    public boolean moveTo(final Position3D other) {
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

    private String build(final String message, final Object... replacements) {
        StringBuilder builder = new StringBuilder();
        char[] chars = message.toCharArray();

        Map<Integer, Object> replaces = new HashMap<>();

        boolean building = false;
        boolean escape = false;

        StringBuilder current = new StringBuilder();
        for (char character : chars) {
            if (character == '\\') escape = !escape;

            if (building) {
                if (character == '}') {
                    building = false;
                    int num = Integer.parseInt(current.toString());
                    current = new StringBuilder();

                    if (num > 0 && num < replacements.length) {
                        Object replacement = replacements[num];
                        builder.append(replacement);
                    } else {
                        builder.append("<missing:{").append(num).append("}>");
                    }
                } else {
                    if (!Character.isDigit(character)) {
                        building = false;
                        builder.append(current);
                        current = new StringBuilder();
                    } else {
                        current.append(character);
                    }
                }
            } else {
                if (character == '{') {
                    building = true;
                    continue;
                }

                current.append(character);
            }
        }

        return builder.toString();
    }
}
