package me.murks.filmchecker.background;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import me.murks.filmchecker.R;
import me.murks.filmchecker.activities.AddFilmWizardActivity;
import me.murks.filmchecker.io.RossmannApi;
import me.murks.filmchecker.model.RmQueryModel;
import me.murks.filmchecker.model.RmStoreModel;

/**
 * Task to query the endpoint of rm store
 * @author zouroboros
 */

public class RmStoreQueryTask extends AsyncTask<URL, Void, RmQueryModel> {

    private final ProgressDialog dialog;
    private final AddFilmWizardActivity wizard;
    private final RmStoreModel rmModel;

    public RmStoreQueryTask(AddFilmWizardActivity filmWizard, RmStoreModel model) {
        dialog = new ProgressDialog(filmWizard);
        wizard = filmWizard;
        rmModel = model;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage(dialog.getContext().getResources().getString(R.string.query_store_methods));
        dialog.show();
    }

    @Override
    protected RmQueryModel doInBackground(URL... strings) {
        try {
            return new RossmannApi().queryStoreQueryMethod(strings[0]);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(RmQueryModel result) {
        super.onPostExecute(result);
        rmModel.setRmQueryModel(result);
        wizard.jumpToLastStep(rmModel, result);
        dialog.dismiss();
    }

    @Override
    protected void onCancelled(RmQueryModel result) {
        dialog.dismiss();
    }
}
