package me.murks.filmchecker.activities;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import me.murks.filmchecker.FilmCheckerApp;
import me.murks.filmchecker.R;
import me.murks.filmchecker.model.StoreModel;

public class AddFilmWizardActivity extends FragmentActivity {

    private FilmDetailsFragment filmDetailsFragment;
    private FilmCheckerApp app;
    private StoreModel storeModel;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_film_wizard);
        ChooseStoreTypeFragment storesFragment = new ChooseStoreTypeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, storesFragment).commit();
        app = new FilmCheckerApp();
    }

    public void jumpToLastStep() {
        FilmDetailsFragment detailsFragment = new FilmDetailsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, detailsFragment).commit();
    }

    public FilmCheckerApp getApp() {
        return app;
    }

    public void setStoreModel(StoreModel model) {
        storeModel = model;
        if(storeModel.needsRmStoreLocator()) {
            RossmannChooseStoreFragment fragment = new RossmannChooseStoreFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        } else {
            FilmDetailsFragment detailsFragment = new FilmDetailsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, detailsFragment).commit();
        }
    }

    public StoreModel getStoreModel() {
        return storeModel;
    }
}
