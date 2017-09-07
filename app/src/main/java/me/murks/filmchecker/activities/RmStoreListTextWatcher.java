package me.murks.filmchecker.activities;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;

import me.murks.filmchecker.background.RmLoadStoresTask;
import me.murks.filmchecker.model.RossmannStoreLink;

/**
 * Textwatcher for searching the rm stores
 * @author zouroboros
 * @version 9/15/17
 */
class RmStoreListTextWatcher implements TextWatcher {
    private final ArrayAdapter<RossmannStoreLink> storeAdapter;
    private RmLoadStoresTask task;

    public RmStoreListTextWatcher(ArrayAdapter<RossmannStoreLink> storeAdapter) {
        this.storeAdapter = storeAdapter;
        task = new RmLoadStoresTask(storeAdapter);
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
            task = new RmLoadStoresTask(storeAdapter);
            task.execute(editable.toString());
        }
    }
}
