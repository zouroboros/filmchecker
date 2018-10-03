package me.murks.filmchecker.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import me.murks.filmchecker.FilmCheckerApp;
import me.murks.filmchecker.model.RmQueryModel;
import me.murks.filmchecker.model.RmStoreModel;
import me.murks.filmchecker.model.StoreModel;

/**
 * Base class for fragments that display a {@link me.murks.filmchecker.model.StoreModel} and a
 * {@link me.murks.filmchecker.model.RmQueryModel}.
 * @author zouroboros
 */
public class StoreModelFragment extends Fragment {

    private static final String STORE_MODEL_ID = StoreModelFragment.class.getName() + ".storeModel";
    private static final String QUERY_MODEL = StoreModelFragment.class.getName() + ".queryModel";

    StoreModel storeModel;
    private RmQueryModel queryModel;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STORE_MODEL_ID, storeModel.getStoreId());
        if(queryModel != null) {
            outState.putParcelable(QUERY_MODEL, queryModel);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null) {
            String storeModelId = savedInstanceState.getString(STORE_MODEL_ID);
            FilmCheckerApp app = new FilmCheckerApp();
            for (StoreModel model: app.getStores()) {
                if(model.getStoreId().equals(storeModelId)) {
                    storeModel = model;
                    break;
                }
            }
            if(savedInstanceState.containsKey(QUERY_MODEL)) {
                queryModel = savedInstanceState.getParcelable(QUERY_MODEL);
                ((RmStoreModel) storeModel).setRmQueryModel(queryModel);
            }
        }
    }

    public void setStoreModel(StoreModel model) {
        storeModel = model;
    }

    public void setQueryModel(RmQueryModel model) {
        queryModel = model;
    }
}
