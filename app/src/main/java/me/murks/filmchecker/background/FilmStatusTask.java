package me.murks.filmchecker.background;

import android.os.AsyncTask;
import android.util.Pair;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import me.murks.filmchecker.io.StatusProviderFactory;
import me.murks.filmchecker.model.Film;
import me.murks.filmchecker.model.FilmStatus;

/**
 * Task for loading the status of film orders
 * @author Zouroboros
 */
public class FilmStatusTask extends AsyncTask<Film, Void, List<Pair<Film, FilmStatus>>> {
    private final ResultListener<List<Pair<Film, FilmStatus>>> resultListener;
    private final StatusProviderFactory statusProviderFactory;

    public FilmStatusTask(ResultListener<List<Pair<Film, FilmStatus>>> resultListener,
                          StatusProviderFactory statusProvider) {
        this.resultListener = resultListener;
        this.statusProviderFactory = statusProvider;
    }

    @Override
    protected void onPostExecute(List<Pair<Film, FilmStatus>> result) {
        super.onPostExecute(result);
        resultListener.onResult(result);
    }

    @Override
    protected List<Pair<Film, FilmStatus>> doInBackground(Film... films) {
        List<Pair<Film, FilmStatus>> results = new LinkedList<>();
        for (Film f: films) {
            try {
                FilmStatus status = statusProviderFactory
                        .getStatusProviderById(f.getStoreId()).getFilmStatus(f);
                results.add(new Pair<>(f, status));
            } catch (IOException ioe) {
                results.add(new Pair<>(f, new FilmStatus(ioe.getLocalizedMessage(), new Date())));
            }
        }
        return results;
    }
}