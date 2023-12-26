package es.karmadev.gamelib.exception;

import es.karmadev.gamelib.pos.Position3D;
import es.karmadev.gamelib.region.GameShape;

/**
 * This exception is thrown when a call
 * to {@link es.karmadev.gamelib.GameLib#createGround(GameShape, Position3D...)} is
 * made but the number of positions does not
 * meet the shape minimum. For instance. If the
 * shape is {@link GameShape#CUBE cube} and only one
 * position is provided, this exception will be thrown
 */
public class PlaygroundPositionException extends RuntimeException {

    /**
     * Initialize the exception
     *
     * @param shape the shape
     * @param required the required positions
     * @param provided the provided positions
     */
    public PlaygroundPositionException(final GameShape shape, final int required, final int provided) {
        super("Failed to create playground of type " + shape.name() + ". " + provided + " positions provided, but " + required + " required");
    }
}
