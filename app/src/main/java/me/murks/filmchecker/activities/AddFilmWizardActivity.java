package me.murks.filmchecker.activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import me.murks.filmchecker.FilmCheckerApp;
import me.murks.filmchecker.R;
import me.murks.filmchecker.model.RmQueryModel;
import me.murks.filmchecker.model.StoreModel;

public class AddFilmWizardActivity extends FragmentActivity {

    private FilmCheckerApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_film_wizard);
        ChooseStoreTypeFragment storesFragment = new ChooseStoreTypeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, storesFragment).commit();
        app = new FilmCheckerApp();
    }

    public void jumpToLastStep(StoreModel model, RmQueryModel queryModel) {
        FilmDetailsFragment detailsFragment = new FilmDetailsFragment();
        detailsFragment.setQueryModel(queryModel);
        detailsFragment.setStoreModel(model);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, detailsFragment).commit();
    }

    public FilmCheckerApp getApp() {
        return app;
    }

    public void setStoreModel(StoreModel model) {
        if(model.needsRmStoreLocator()) {
            RossmannChooseStoreFragment fragment = new RossmannChooseStoreFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment).commit();
            fragment.setStoreModel(model);
        } else {
            FilmDetailsFragment detailsFragment = new FilmDetailsFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, detailsFragment).commit();
            detailsFragment.setStoreModel(model);
        }
    }
}
