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

    private final Date date;

    /**
     * Constructs a new FilmStatus with the given status
     * @param newStatus The status
     * @param newDate The date of status
     */
    public FilmStatus(String newStatus, Date newDate) {
        status = newStatus;
        date = newDate;
    }

    /**
     * Returns the status string
     * @return The status as {@see String}
     */
    public String getStatus() {
        return status;
    }

    /**
     * Returns the date for the status
     * @return Status date
     */
    public Date getDate() { return date; }
}
