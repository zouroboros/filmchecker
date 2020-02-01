package me.murks.filmchecker.model;

/**
 * Enum for states a film order can be in
 * @author zouroboros
 */
public enum FilmOrderState {
    /**
     * The state of the order is unknown
     */
    UNKNOWN,
    /**
     * The order is currently processed
     */
    PROCESSING,
    /**
     * The order finished processing
     */
    DONE
}
