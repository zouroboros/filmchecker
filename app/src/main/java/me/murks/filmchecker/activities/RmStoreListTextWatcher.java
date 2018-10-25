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
    private final View rmProgressBar;
    private final ErrorReceiver errorReceiver;

    /**
     * Constructs a new TextWatcher
     * @param storeAdapter The adapter to fill by the background task
     * @param progressBar The view to show that the data loading is in progress
     * @param receiver The error receiver that is informed when an error occurs
     */
    public RmStoreListTextWatcher(ArrayAdapter<RossmannStoreLink> storeAdapter, View progressBar, ErrorReceiver receiver) {
        this.storeAdapter = storeAdapter;
        task = new RmLoadStoresTask(storeAdapter, receiver);
        rmProgressBar = progressBar;
        errorReceiver = receiver;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        storeAdapter.clear();
        task.cancel(true);
        if (editable.length() > 2) {
            rmProgressBar.setVisibility(View.VISIBLE);
            task = new RmLoadStoresTask(storeAdapter, errorReceiver);
            task.execute(editable.toString());
        }
    }
}
