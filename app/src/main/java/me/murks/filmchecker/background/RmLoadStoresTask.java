package me.murks.filmchecker.background;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import me.murks.filmchecker.activities.ErrorReceiver;
import me.murks.filmchecker.io.RossmannApi;
import me.murks.filmchecker.model.RossmannStoreLink;

/**
 *
 * Created by zouroboros on 9/13/17.
 */

public class RmLoadStoresTask extends AsyncTask<String, Void, List<RossmannStoreLink>> {

    private final ArrayAdapter<RossmannStoreLink> adapter;
    private final ErrorReceiver errorReceiver;

    public RmLoadStoresTask(ArrayAdapter<RossmannStoreLink> adapter, ErrorReceiver receiver) {
        this.adapter = adapter;
        errorReceiver = receiver;
    }

    @Override
    protected List<RossmannStoreLink> doInBackground(String... strings) {
        try {
           return new RossmannApi().queryStores(strings[0]);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<RossmannStoreLink> result) {
        super.onPostExecute(result);
        adapter.clear();
        if (result != null) {
            for (RossmannStoreLink store : result) {
                adapter.add(store);
            }
        }
        else {
            errorReceiver.errorOccurred();
        }
        adapter.notifyDataSetChanged();
    }
}
