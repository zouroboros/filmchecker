package me.murks.filmchecker.background;
/**
 * Interface for listener that get a notification when a result has arrived
 */
public interface ResultListener<T> {
    /**
     * Called when the information for all films have been loaded
     * @param result The retrieved information
     */
    void onResult(T result);
}
