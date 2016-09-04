package me.murks.filmchecker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

import me.murks.filmchecker.FilmCheckerApp;
import me.murks.filmchecker.R;
import me.murks.filmchecker.io.IStatusProvider;
import me.murks.filmchecker.model.Film;

/**
 * Activity to add a new film order
 */
public class AddFilmActivity extends AppCompatActivity {

    private FilmCheckerApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_film);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        app = new FilmCheckerApp();

        Spinner providerSpinner = (Spinner) findViewById(R.id.providerSpinner);
        providerSpinner.setAdapter(new StatusProviderAdapter(this, app.getStatusProvider().getFilmStatusProvider()));
    }

    public void addFilmClicked(View view) {
        String orderNumber = ((EditText) findViewById(R.id.orderNumber)).getText().toString();
        String shopId = ((EditText) findViewById(R.id.shopId)).getText().toString();
        DatePicker picker = ((DatePicker) findViewById(R.id.insertDatePicker));
        Calendar date = Calendar.getInstance();
        date.set(picker.getYear(), picker.getMonth(), picker.getDayOfMonth());
        Spinner providerSpinner = (Spinner) findViewById(R.id.providerSpinner);
        IStatusProvider provider = (IStatusProvider) providerSpinner.getSelectedItem();
        Film film = new Film(orderNumber, shopId, date, provider.getId());
        app.addFilm(this, film);
        Intent intent = new Intent(this, FilmListActivity.class);
        startActivity(intent);
    }

}
