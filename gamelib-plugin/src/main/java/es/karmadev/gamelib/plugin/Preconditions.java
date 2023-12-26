package es.karmadev.gamelib.plugin;

/**
 * GameLib preconditions
 */
public class Preconditions {

    /**
     * Verify if an element is null. If
     * null, a {@link NullPointerException} is
     * thrown
     *
     * @param element the element to validate
     * @param errorMessage the message to show if the element
     *                     is null
     * @throws NullPointerException if the element is null
     */
    public static void requireNotNull(final Object element, final String errorMessage) throws NullPointerException {
        if (element == null) {
            throw new NullPointerException(errorMessage);
        }
    }

    /**
     * Verify if an element (or any
     * of its contents is null)
     *
     * @param elementArray the element array to
     *                     validate
     * @param errorMessage the message to show if the
     *                     element is null
     * @throws NullPointerException if the element or any of
     * its contents is null
     */
    public static void requireArrayNotNull(final Object[] elementArray, final String errorMessage) throws NullPointerException {
        requireNotNull(elementArray, "Array cannot be null");
        requireArrayNotNull(elementArray, errorMessage, 0, elementArray.length);
    }

    /**
     * Verify if an element (or any
     * of its contents is null)
     *
     * @param elementArray the element array to
     *                     validate
     * @param errorMessage the message to show if the
     *                     element is null
     * @throws NullPointerException if the element or any of
     * its contents is null
     * @throws ArrayIndexOutOfBoundsException if the start
     * index is over the array max
     */
    public static void requireArrayNotNull(final Object[] elementArray, final String errorMessage, final int startFrom) throws NullPointerException,
            ArrayIndexOutOfBoundsException {

        requireNotNull(elementArray, "Array cannot be null");
        requireArrayNotNull(elementArray, errorMessage, startFrom, elementArray.length);
    }

    /**
     * Verify if an element (or any
     * of its contents is null)
     *
     * @param elementArray the element array to
     *                     validate
     * @param errorMessage the message to show if the
     *                     element is null
     * @param startFrom    the index to start to count
     *                     from
     * @param endAt        the index to stop at
     * @throws NullPointerException if the element or any of
     * its contents is null
     * @throws IndexOutOfBoundsException if the start index is
     * over the end index or out of bounds
     */
    public static void requireArrayNotNull(final Object[] elementArray, final String errorMessage,
                                           final int startFrom, final int endAt) throws NullPointerException, IndexOutOfBoundsException {
        requireNotNull(elementArray, "Array element cannot be null");
        if (startFrom < 0) {
            throw new ArrayIndexOutOfBoundsException("Array element index impossible: " + startFrom);
        }
        if (endAt >= elementArray.length) {
            throw new ArrayIndexOutOfBoundsException("Cannot verify elements to index " + endAt + ". Array max: " + (elementArray.length - 1));
        }
        if (startFrom > endAt) {
            throw new IndexOutOfBoundsException("Cannot verify elements from index " + startFrom + " to " + endAt);
        }

        for (int i = startFrom; i < endAt; i++) {
            Object element = elementArray[i];
            if (element == null) {
                throw new NullPointerException(errorMessage.replace("$", String.valueOf(i)));
            }
        }
    }
}
