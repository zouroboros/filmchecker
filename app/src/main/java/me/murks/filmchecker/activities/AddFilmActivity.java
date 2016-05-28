package me.murks.filmchecker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import me.murks.filmchecker.FilmCheckerApp;
import me.murks.filmchecker.R;
import me.murks.filmchecker.model.Film;

public class AddFilmActivity extends AppCompatActivity {

    private FilmCheckerApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_film);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        app = new FilmCheckerApp();
    }

    public void addFilmClicked(View view) {
        String orderNumber = ((EditText) findViewById(R.id.orderNumber)).getText().toString();
        String shopId = ((EditText) findViewById(R.id.shopId)).getText().toString();
        Film film = new Film(orderNumber, shopId);
        app.addFilm(this, film);
        Intent intent = new Intent(this, FilmListActivity.class);
        startActivity(intent);
    }

}
