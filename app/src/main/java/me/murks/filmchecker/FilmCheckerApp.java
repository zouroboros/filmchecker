package me.murks.filmchecker;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.murks.filmchecker.activities.FilmStatusListAdapter;
import me.murks.filmchecker.io.FilmDb;
import me.murks.filmchecker.io.StatusProviderFactory;
import me.murks.filmchecker.model.Film;

/**
 * Class for representing the FilmChecker app. Provides means
 * to retrieve and store data for activities.
 * @author zouroboros
 * @version 0.1 2016-05-29
 */
public class FilmCheckerApp {
    /**
     * Fills the given {@see FilmStatusListAdapter} and populates it with {@see Film}s and there order status
     * @param context The context from which this method is called
     * @param adapter The adapter to fill
     * @return An {@see AsyncFilmListTask} object
     */
    public AsyncFilmListTask fillFilmList(Context context, FilmStatusListAdapter adapter) {
        FilmDb db = new FilmDb(context);
        Collection<Film> films = db.getFilms();
        AsyncFilmListTask loader = new AsyncFilmListTask(context, adapter, films, getStatusProvider());
        return loader;
    }

    /**
     * Retrieves a list of all saved films
     * @param context Current context
     * @return A {@see List} of {@link Film}s
     */
    public List<Film> getFilms(Context context) {
        FilmDb db = new FilmDb(context);
        return new ArrayList<>(db.getFilms());
    }

    /**
     * Permanently adds a film to the film database
     * @param context Current context
     * @param film The {@see Film}
     */
    public void addFilm(Context context, Film film) {
        FilmDb db = new FilmDb(context);
        db.addFilm(film);
    }

    /**
     * Rermanently removes a {@see Film}
     * @param context Current context
     * @param film The {@see Film} to remove
     */
    public void removeFilm(Context context, Film film) {
        FilmDb db = new FilmDb(context);
        db.deleteFilm(film);
    }

    public StatusProviderFactory getStatusProvider() {
        return new StatusProviderFactory();
    }
}
