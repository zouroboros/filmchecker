package me.murks.filmchecker;

import android.content.Context;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.murks.filmchecker.background.FilmStatusTask;
import me.murks.filmchecker.background.ResultListener;
import me.murks.filmchecker.io.FilmDb;
import me.murks.filmchecker.io.StatusProviderFactory;
import me.murks.filmchecker.model.BipaAtStoreModel;
import me.murks.filmchecker.model.CeweStoreModel;
import me.murks.filmchecker.model.DmAtStoreModel;
import me.murks.filmchecker.model.DmDeStoreModel;
import me.murks.filmchecker.model.Film;
import me.murks.filmchecker.model.FilmStatus;
import me.murks.filmchecker.model.MuellerAtStoreModel;
import me.murks.filmchecker.model.MuellerDeStoreModel;
import me.murks.filmchecker.model.RmStoreModel;
import me.murks.filmchecker.model.StoreModel;

/**
 * Class for representing the FilmChecker app. Provides means
 * to retrieve and store data for activities.
 * @author zouroboros
 */
public class FilmCheckerApp {
    /**
     * Fills the given {@see FilmStatusListAdapter} and populates it with {@see Film}s and there order status
     * @param listener The listener for the results
     */
    public void loadFilmStatus(ResultListener<List<Pair<Film, FilmStatus>>> listener, Film... films) {
        new FilmStatusTask(listener, getStatusProvider()).execute(films);
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
     * Retrieves a film by its id. Returns the film if a film with the id exists,
     * otherwise it returns null
     * @param context The current context
     * @param id The id of the film
     * @return The film or null
     */
    public Film getFilmById(Context context, Long id) {
        for (Film f: getFilms(context)) {
            if(f.getId().equals(id)) {
                return f;
            }
        }
        return null;
    }

    /**
     * Permanently removes a {@see Film}
     * @param context Current context
     * @param film The {@see Film} to remove
     */
    public void removeFilm(Context context, Film film) {
        FilmDb db = new FilmDb(context);
        db.deleteFilm(film);
    }

    private StatusProviderFactory getStatusProvider() {
        return new StatusProviderFactory();
    }

    /**
     * Returns the list of supported stores
     * @return List of supported stores
     */
    public List<StoreModel> getStores() {
        return Arrays.asList(new DmDeStoreModel(), new DmAtStoreModel(),
                new RmStoreModel(), new MuellerAtStoreModel(),
                new MuellerDeStoreModel(), new BipaAtStoreModel(),new CeweStoreModel());
    }

    /**
     * Returns the {@link StoreModel} for a film
     * @param film The film whose store model is returned
     * @return The StoreModel
     * @throws IllegalArgumentException If no store is found for the film
     */
    public StoreModel getStoreModelForFilm(Film film) {
        for (StoreModel model: getStores()) {
            if (model.getStoreId().equals(film.getStoreId())) {
                return model;
            }
        }
        throw new IllegalArgumentException("No store found for store id:" + film.getStoreId()
                + " of film:" + film.toString());
    }
}
