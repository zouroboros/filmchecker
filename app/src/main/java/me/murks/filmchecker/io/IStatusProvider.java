package me.murks.filmchecker.io;

import java.io.IOException;

import me.murks.filmchecker.model.Film;
import me.murks.filmchecker.model.FilmStatus;

/**
 * Interface for film status provider implementations
 */
public interface IStatusProvider {
    /**
     * Gets the name of the status provider to show in the ui
     * @return Human readable name of this status provider
     */
    String getUiName();

    /**
     * The id of this status provider
     * @return
     */
    String getId();

    /**
     * Queries the status of the given film
     * @param film The film whose status wil be queried
     * @return The status of the film order
     * @throws IOException When an exception occurs while querying
     */
    FilmStatus getFilmStatus(Film film) throws IOException;
}
