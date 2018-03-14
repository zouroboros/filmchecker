package me.murks.filmchecker.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import me.murks.filmchecker.FilmCheckerApp;
import me.murks.filmchecker.R;
import me.murks.filmchecker.model.RmQueryModel;

public class AddFilmWizardActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private RmQueryModel rmQueryModel;
    private FilmDetailsFragment filmDetailsFragment;
    private FilmCheckerApp app;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_film_wizard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        app = new FilmCheckerApp();
    }

    public void jumpToRossmann() {
        mViewPager.setCurrentItem(1);
    }

    public void jumpToDm() {
        mViewPager.setCurrentItem(2);
        filmDetailsFragment.setRmQueryModel(null);
    }

    public void jumpToLastStep(RmQueryModel rmQueryModel) {
        this.rmQueryModel = rmQueryModel;
        filmDetailsFragment.setRmQueryModel(rmQueryModel);
        mViewPager.setCurrentItem(2);
    }

    /**
     * If the new film to be added is a rossmann film this returns the query model for rossmann films
     * @return
     */
    public RmQueryModel getRmQueryModel() {
        return rmQueryModel;
    }

    public FilmCheckerApp getApp() {
        return app;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                return ChooseStoreTypeFragment.newInstance(position);
            } else if(position == 1) {
                return RossmannChooseStoreFragment.newInstance(position);
            } else if(position == 2) {
                if(filmDetailsFragment == null) {
                    filmDetailsFragment = new FilmDetailsFragment();
                }
                return filmDetailsFragment;
            }
            throw new IllegalArgumentException("invalid position");
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
