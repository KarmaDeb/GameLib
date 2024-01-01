package es.karmadev.gamelib.entity.human;

import es.karmadev.gamelib.Condition;
import es.karmadev.gamelib.entity.EngineLivingEntity;
import es.karmadev.gamelib.entity.NPCEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a human player. This
 * always represents a player, and will
 * never be represented by {@link NPCEntity}
 */
public interface HumanPlayer extends EngineLivingEntity, HumanOffline {
    
    /**
     * Send a message to the player
     *
     * @param message the message to send to the
     *                player
     * @param replacements the message replacements
     */
    void sendMessage(final @NotNull String message, final Object... replacements);

    /**
     * Send a title to the player
     *
     * @param title the title
     * @param subtitle the subtitle
     */
    default void sendTitle(final @Nullable String title, final @Nullable String subtitle) {
        sendTitle(title, subtitle, 10, 70, 20);
    }

    /**
     * Send a title to the player
     *
     * @param title the title
     * @param subtitle the subtitle
     * @param fadeIn the time to fade in, defaults to 10
     * @param keep the time to show, defaults to 70
     * @param fadeOut the time fade out, defaults to 20
     */
    void sendTitle(final @Nullable String title, final @Nullable String subtitle, final int fadeIn, final int keep, final int fadeOut);

    /**
     * Send an actionbar to the player
     *
     * @param actionbar the actionbar to send
     * @param replacements the message replacements
     */
    void sendActionbar(final @NotNull String actionbar, final Object... replacements);

    /**
     * Clear the player actionbar
     */
    default void clearActionbar() {
        sendActionbar("");
    }

    /**
     * Clear the player title
     */
    default void clearTitle() {
        sendTitle("", null);
    }

    /**
     * Clear the player subtitle
     */
    default void clearSubtitle() {
        sendTitle(null, "");
    }

    /**
     * Get a player condition
     *
     * @param condition the condition
     * @return the player condition
     * @param <T> the condition type
     */
    default  <T> T getCondition(final Condition<T> condition) {
        if (condition == null) return null;
        return getCondition(condition, null);
    }

    /**
     * Get a player condition
     *
     * @param condition the condition to
     *                  retrieve
     * @param def the default condition
     * @return the condition
     * @param <T> the condition type
     */
    <T> T getCondition(final Condition<T> condition, final T def);

    /**
     * Set a player condition
     *
     * @param condition the condition to set
     * @param value the condition value
     * @param <T> the condition type
     */
    <T> void setCondition(final Condition<T> condition, final @Nullable T value);

    /**
     * Get if the player is online
     *
     * @return if the player is
     * online
     */
    @Override
    default boolean isOnline() {
        return true;
    }

    /**
     * Get the human player instance
     *
     * @return the human player
     */
    @Override
    default @Nullable HumanPlayer getPlayer() {
        return this;
    }
}
