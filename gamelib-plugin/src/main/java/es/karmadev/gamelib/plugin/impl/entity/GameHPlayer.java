package es.karmadev.gamelib.plugin.impl.entity;

import es.karmadev.gamelib.Condition;
import es.karmadev.gamelib.entity.human.HumanPlayer;
import es.karmadev.gamelib.plugin.GameLibImpl;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameHPlayer extends GameLivingEntity implements HumanPlayer {

    private final GameLibImpl lib;

    private final Player player;
    private final Map<Condition<?>, Object> conditions = new ConcurrentHashMap<>();

    /**
     * Create a new engine entity
     *
     * @param player the player which represents
     *               this game human player
     */
    public GameHPlayer(final GameLibImpl lib, final Player player) {
        super(lib, player);
        this.lib = lib;
        this.player = player;
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
        System.out.println(lib);

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
        String lastColor = "&r";

        StringBuilder specialChar = new StringBuilder();
        int specialAmount = -1;
        boolean canSpecial = true;
        boolean catchSpecial = false;
        boolean start = false;
        boolean end = false;
        boolean doLast = true;
        boolean doFirst = true;

        for (int i = 0; i < chars.length; i++) {
            char character = chars[i];
            if (character == '\\') {
                escape = !escape;
            }

            if (building) {
                if (character == '}') {
                    catchSpecial = false;
                    building = false;
                    int num = Integer.parseInt(current.toString());
                    current.setLength(0);

                    if (specialAmount == -1) specialAmount = 1;
                    if (!start && !end) start = true;

                    if (num > 0 && num <= replacements.length) {
                        Object replacement = replacements[num - 1];

                        for (int j = 0; j < replacements.length; j++) {
                            Object indexReplacement = replacements[j];
                            int index = specialChar.indexOf("%" + (j + 1));
                            while (index > -1) {
                                specialChar.replace(index, index + 2, String.valueOf(indexReplacement));
                                index = specialChar.indexOf("%" + (j + 1));
                            }
                        }

                        if (!canSpecial) {
                            for (int j = 0; j < specialAmount; j++) {
                                if (start && ((j == 0 && doFirst) || j > 0)) {
                                    if (specialChar.length() != 0) {
                                        builder.append(specialChar);
                                    }
                                }

                                builder.append(replacement);

                                if (end && ((j == specialAmount - 1 && doLast) || j < specialAmount - 1)) {
                                    if (specialChar.length() != 0) {
                                        builder.append(specialChar);
                                    }
                                }
                            }
                        } else {
                            builder.append(replacement);
                        }
                    } else {
                        builder.append("§c<missing:{§earg[§3").append(num).append("§e]§c}>").append(lastColor);
                    }

                    start = false;
                    end = false;
                    doLast = true;
                    doFirst = true;
                    specialChar.setLength(0);
                    canSpecial = true;
                    specialAmount = -1;
                } else {
                    if (!Character.isDigit(character)) {
                        if (character == '.' && canSpecial) {
                            canSpecial = false;
                            continue;
                        }
                        if (!canSpecial) {
                            if (!escape) {
                                switch (character) {
                                    case '%':
                                        specialChar.append(character);
                                        continue;
                                    case '+':
                                        start = true;
                                        continue;
                                    case '-':
                                        end = true;
                                        continue;
                                    case '!':
                                        doFirst = false;
                                        continue;
                                    case '?':
                                        doLast = false;
                                        continue;
                                }
                            }

                            if (character == '\'') {
                                if (escape) {
                                    specialChar.append(character);
                                    continue;
                                }

                                catchSpecial = !catchSpecial;
                                continue;
                            }

                            specialChar.append(character);
                            continue;
                        }

                        start = false;
                        end = false;
                        catchSpecial = false;
                        canSpecial = true;
                        escape = false;
                        building = false;
                        doLast = true;
                        doFirst = true;
                        builder.append("{").append(current);
                        current.setLength(0);
                        specialAmount = -1;
                    } else {
                        if (catchSpecial) {
                            specialChar.append(character);
                            continue;
                        }

                        if (!canSpecial) {
                            if (specialAmount != -1) {
                                specialChar.append(specialAmount);
                            }

                            specialAmount = Integer.parseInt(String.valueOf(character));
                            continue;
                        }

                        current.append(character);
                    }
                }
            } else {
                if (character == '{' && !escape) {
                    building = true;
                    continue;
                }

                if ((character == '&' || character == '§') && !escape) {
                    if (i + 1 < chars.length) {
                        char next = chars[++i];
                        ChatColor color = ChatColor.getByChar(Character.toLowerCase(next));
                        if (color != null) {
                            lastColor = "§" + next;
                            builder.append(lastColor);
                            continue;
                        }

                        builder.append(character).append(next);
                        continue;
                    }
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
     * Get the entity head width
     *
     * @return the entity head width
     */
    @Override
    public double getHeadWidth() {
        return 0.6; //known constant
    }

    /**
     * Get the entity head height
     *
     * @return the head height
     */
    @Override
    public double getHeadHeight() {
        return 0.6; //known constant
    }
}
