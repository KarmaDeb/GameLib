package es.karmadev.gamelib.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Represents a human player. This
 * always represents a player, and will
 * never be represented by {@link NPCEntity}
 */
public abstract class HumanPlayer extends EngineEntity {

    /**
     * Create a new engine entity
     *
     * @param name     the entity name
     * @param uniqueId the entity unique ID
     */
    public HumanPlayer(final String name, final UUID uniqueId) {
        super(name, uniqueId);
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
    public final boolean hasAI() {
        return false;
    }

    /**
     * Send a message to the player
     *
     * @param message the message to send to the
     *                player
     * @param replacements the message replacements
     */
    public abstract void sendMessage(final @NotNull String message, final Object... replacements);

    /**
     * Send a title to the player
     *
     * @param title the title
     * @param subtitle the subtitle
     */
    public void sendTitle(final @Nullable String title, final @Nullable String subtitle) {
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
    public abstract void sendTitle(final @Nullable String title, final @Nullable String subtitle, final int fadeIn, final int keep, final int fadeOut);

    /**
     * Send an actionbar to the player
     *
     * @param actionbar the actionbar to send
     */
    public abstract void sendActionbar(final @Nullable String actionbar);

    /**
     * Clear the player actionbar
     */
    public void clearActionbar() {
        sendActionbar("");
    }

    /**
     * Clear the player title
     */
    public void clearTitle() {
        sendTitle("", null);
    }

    /**
     * Clear the player subtitle
     */
    public void clearSubtitle() {
        sendTitle(null, "");
    }
}