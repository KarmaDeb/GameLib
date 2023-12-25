package es.karmadev.gamelib;

/**
 * Represents the game lib
 */
public abstract class GameLib {

    private static GameLib instance;

    /**
     * Register the game library instance
     *
     * @throws SecurityException if there's an instance
     * already registered
     */
    protected final void registerAsInstance() throws SecurityException {
        if (instance != null) throw new SecurityException("Cannot define game lib instance as it has been already defined");
        GameLib.instance = this;
    }

    /**
     * Get the game lib instance
     *
     * @return the instance
     */
    public static GameLib getInstance() {
        return instance;
    }

    /**
     * Run a task asynchronously
     *
     * @param task the task to run
     */
    public abstract void runAsync(final Runnable task);

    /**
     * Run a task synchronously
     *
     * @param task the task to run
     */
    public abstract void runSync(final Runnable task);

    /**
     * Get the library player manager
     *
     * @return the player manager
     */
    public abstract PlayerManager getPlayerManager();
}
