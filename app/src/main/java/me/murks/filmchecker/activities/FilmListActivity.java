package me.murks.filmchecker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import me.murks.filmchecker.FilmCheckerApp;
import me.murks.filmchecker.R;
import me.murks.filmchecker.model.Film;

public class FilmListActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    private FilmCheckerApp app;
    private FilmStatusListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        app = new FilmCheckerApp();
        adapter = new FilmStatusListAdapter(this);

        ListView filmList = (ListView) findViewById(R.id.filmList);

        filmList.setOnItemLongClickListener(this);
        filmList.setAdapter(adapter);

        loadList();
    }

    private void loadList() {
        adapter.clear();
        app.fillFilmList(this, adapter).execute();
    }

    public void addFilmClicked(View view) {
        Intent intent = new Intent(this, AddFilmActivity.class);
        startActivity(intent);
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        app.removeFilm(this, app.getFilms(this).get(position));
        loadList();
        return true;
    }
}
