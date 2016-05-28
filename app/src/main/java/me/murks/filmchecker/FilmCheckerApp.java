package me.murks.filmchecker;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.murks.filmchecker.activities.FilmStatusListAdapter;
import me.murks.filmchecker.io.FilmDb;
import me.murks.filmchecker.io.RossmanStatusProvider;
import me.murks.filmchecker.model.Film;
import me.murks.filmchecker.model.FilmStatus;

/**
 * Created by mark on 28.05.16.
 */
public class FilmCheckerApp {


    public AsyncListViewLoader fillFilmList(Context context, FilmStatusListAdapter adapter) {
        FilmDb db = new FilmDb(context);
        RossmanStatusProvider statusProvider = new RossmanStatusProvider();
        Collection<Film> films = db.getFilms();
        AsyncListViewLoader loader = new AsyncListViewLoader(context, adapter, films);
        return loader;
    }

    public List<Film> getFilms(Context context) {
        FilmDb db = new FilmDb(context);
        return new ArrayList<>(db.getFilms());
    }

    public void addFilm(Context context, Film film) {
        FilmDb db = new FilmDb(context);
        db.addFilm(film);
    }

    public void removeFilm(Context context, Film film) {
        FilmDb db = new FilmDb(context);
        db.deleteFilm(film);
    }
}
