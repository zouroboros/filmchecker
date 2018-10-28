package me.murks.filmchecker.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import me.murks.filmchecker.FilmCheckerApp;
import me.murks.filmchecker.R;
import me.murks.filmchecker.background.ResultListener;
import me.murks.filmchecker.model.Film;
import me.murks.filmchecker.model.FilmStatus;
import me.murks.filmchecker.model.StoreModel;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Pair;
import android.widget.TextView;

import java.util.List;
import java.util.TimeZone;

public class FilmActivity extends AppCompatActivity {

    public static final String FILM_ID_INTENT_EXTRA = "filmId";

    private TextView shopName, orderNumber, shopIdLabel, shopId, addedOn, statusCode;
    private SwipeRefreshLayout refresh;
    private FilmCheckerApp app;
    private Film film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.film_activity);

        app = new FilmCheckerApp();

        long filmId = getIntent().getExtras().getLong(FILM_ID_INTENT_EXTRA);
        film = app.getFilmById(this, filmId);

        shopName = findViewById(R.id.film_shop_name);
        orderNumber = findViewById(R.id.film_ordernumber);
        shopIdLabel = findViewById(R.id.film_shopid_label);
        shopId = findViewById(R.id.film_shopid);
        addedOn = findViewById(R.id.film_added_on);
        statusCode = findViewById(R.id.film_status_code);

        refresh = findViewById(R.id.film_refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFilm();
            }
        });

        refresh.setRefreshing(true);
        loadFilm();
    }

    private void loadFilm() {
        app.loadFilmStatus(new ResultListener<List<Pair<Film, FilmStatus>>>() {
            @Override
            public void onResult(List<Pair<Film, FilmStatus>> result) {
                for (Pair<Film, FilmStatus> pair: result) {
                    bindFilm(pair.first, pair.second);
                    film = pair.first;
                }
                refresh.setRefreshing(false);
            }
        }, film);
    }

    private void bindFilm(Film film, FilmStatus status) {
        StoreModel model = app.getStoreModelForFilm(film);

        shopName.setText(model.getStoreName());

        orderNumber.setText(film.getOrderNumber());

        if(film.getHtNumber() != null) {
            shopIdLabel.setText(R.string.htn_number);
            shopId.setText(film.getHtNumber());
        } else {
            shopIdLabel.setText(R.string.shop_id);
            shopId.setText(film.getShopId());
        }

        long when = film.getInsertDate().getTimeInMillis();
        long time = when + TimeZone.getDefault().getOffset(when);
        String formattedDate = DateUtils.formatDateTime(this, time, DateUtils.FORMAT_SHOW_DATE);
        addedOn.setText(formattedDate);

        statusCode.setText(status.getStatus());
    }
}
