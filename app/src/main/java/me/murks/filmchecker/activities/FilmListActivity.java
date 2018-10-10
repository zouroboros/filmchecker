package me.murks.filmchecker.activities;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import me.murks.filmchecker.FilmCheckerApp;
import me.murks.filmchecker.R;

/**
 * Activity for listing all films
 */
public class FilmListActivity extends AppCompatActivity {

    private FilmCheckerApp app;
    private FilmStatusListAdapter adapter;
    private View progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);

        app = new FilmCheckerApp();

        progress = findViewById(R.id.listProgress);
        progress.setVisibility(View.INVISIBLE);

        adapter = new FilmStatusListAdapter(this);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                progress.setVisibility(View.INVISIBLE);
            }
        });

        ListView filmList = (ListView) findViewById(R.id.filmList);
        filmList.setAdapter(adapter);

        loadList();
    }

    private void loadList() {
        adapter.clear();
        progress.setVisibility(View.VISIBLE);
        app.fillFilmList(this, adapter).execute();
    }

    public void addFilmClicked(View view) {
        Intent intent = new Intent(this, AddFilmWizardActivity.class);
        startActivity(intent);
    }

    public void deleteFilmClicked(View view) {
        app.removeFilm(this, app.getFilmById(this, (Long) view.getTag()));
        loadList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_film_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.update_film_list) {
            loadList();
            return true;
        }

        if(id == R.id.help) {
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
