package me.murks.filmchecker.model;

/**
 * Model for the status of a film order
 * @author zouroboros
 * @version 0.1 2016-05-29
 */
public class FilmStatus {
    /**
     * Status string describing the status of the order
     */
    private final String status;

    /**
     * Constructs a new FilmStatus with the given status
     * @param status The status
     */
    public FilmStatus(String status) {
        this.status = status;
    }

    /**
     * Returns the status string
     * @return The status as {@see String}
     */
    public String getStatus() {
        return status;
    }
}
