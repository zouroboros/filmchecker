package me.murks.filmchecker.model;

import java.util.Date;

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
     * Date when the status was last updated
     */
    private final Date date;

    /**
     * State of this film order
     */
    private final FilmOrderState state;

    /**
     * Constructs a new FilmStatus with the given status
     * @param newStatus The status
     * @param newDate The date of status (can be null)
     * @param newState The state of the film order (can be null)
     */
    public FilmStatus(String newStatus, Date newDate, FilmOrderState newState) {
        status = newStatus;
        date = newDate;
        state = newState;
    }

    /**
     * Returns the status string
     * @return The status as {@see String}
     */
    public String getStatus() {
        return status;
    }

    /**
     * Returns the date for the status (null if no date is provided)
     * @return Status date
     */
    public Date getDate() { return date; }

    /**
     * The state of the film order (null if no state is provided)
     * @return Order state
     */
    public FilmOrderState getState() { return state; }
}
