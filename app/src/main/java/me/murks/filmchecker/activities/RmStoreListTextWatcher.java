package me.murks.filmchecker.activities;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;

import me.murks.filmchecker.background.RmLoadStoresTask;
import me.murks.filmchecker.model.RossmannStoreLink;

/**
 * Textwatcher for searching the rm stores
 * @author zouroboros
 */
class RmStoreListTextWatcher implements TextWatcher {
    private final ArrayAdapter<RossmannStoreLink> storeAdapter;
    private RmLoadStoresTask task;
    private View rmProgressBar;

    /**
     * Constructs a new TextWatcher
     * @param storeAdapter The adapter to fill by the background task
     * @param progressBar The view to show that the data loading is in progress
     */
    public RmStoreListTextWatcher(ArrayAdapter<RossmannStoreLink> storeAdapter, View progressBar) {
        this.storeAdapter = storeAdapter;
        task = new RmLoadStoresTask(storeAdapter);
        rmProgressBar = progressBar;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        task.cancel(true);
        if (editable.length() > 2) {
            rmProgressBar.setVisibility(View.VISIBLE);
            task = new RmLoadStoresTask(storeAdapter);
            task.execute(editable.toString());
        }
    }
}
