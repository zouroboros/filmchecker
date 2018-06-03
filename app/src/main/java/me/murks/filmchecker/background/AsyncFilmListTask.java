package me.murks.filmchecker.background;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import me.murks.filmchecker.R;
import me.murks.filmchecker.activities.FilmListActivity;
import me.murks.filmchecker.activities.FilmStatusListAdapter;
import me.murks.filmchecker.io.StatusProviderFactory;
import me.murks.filmchecker.model.Film;
import me.murks.filmchecker.model.FilmStatus;

/**
 * Task for loading the status of film orders
 * @author Zouroboros
 */
public class AsyncFilmListTask extends AsyncTask<String, Void, List<Pair<Film, FilmStatus>>> {
    private final FilmStatusListAdapter adapter;
    private final Collection<Film> films;
    private final StatusProviderFactory statusProviderFactory;

    public AsyncFilmListTask(FilmStatusListAdapter adapter,
                             Collection<Film> films,
                             StatusProviderFactory statusProvider) {
        this.adapter = adapter;
        this.films = films;
        this.statusProviderFactory = statusProvider;
    }

    @Override
    protected void onPostExecute(List<Pair<Film, FilmStatus>> result) {
        super.onPostExecute(result);
        adapter.addAll(result);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Pair<Film, FilmStatus>> doInBackground(String... params) {
        List<Pair<Film, FilmStatus>> results = new LinkedList<>();
        for (Film f: films) {
            try {
                FilmStatus status = statusProviderFactory
                        .getStatusProviderById(f.getStatusProvider()).getFilmStatus(f);
                results.add(new Pair<>(f, status));
            } catch (IOException ioe) {
                results.add(new Pair<>(f, new FilmStatus(ioe.getLocalizedMessage())));
            }
        }
        return results;
    }
}