package me.murks.filmchecker.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import me.murks.filmchecker.FilmCheckerApp;
import me.murks.filmchecker.R;
import me.murks.filmchecker.background.ResultListener;
import me.murks.filmchecker.model.Film;
import me.murks.filmchecker.model.FilmStatus;
import me.murks.filmchecker.model.StoreModel;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.TimeZone;

public class FilmActivity extends AppCompatActivity {

    public static final String FILM_ID_INTENT_EXTRA = "filmId";

    private TextView shopName, orderNumber, shopIdLabel, shopId, addedOn, statusCode;
    private SwipeRefreshLayout refresh;
    private Button openStoreTrackingButton;
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

        openStoreTrackingButton = findViewById(R.id.filmOpenStoreTrackingButton);

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

    private void bindFilm(final Film film, FilmStatus status) {
        final StoreModel model = app.getStoreModelForFilm(film);

        shopName.setText(model.getStoreName());

        orderNumber.setText(film.getOrderNumber());

        if(film.getHtNumber() != null) {
            shopIdLabel.setText(R.string.htn_number);
            shopId.setText(film.getHtNumber());
        } else {
            shopIdLabel.setText(getString(model.getShopIdFieldName()));
            shopId.setText(film.getShopId());
        }

        long when = film.getInsertDate().getTimeInMillis();
        long time = when + TimeZone.getDefault().getOffset(when);
        String formattedDate = DateFormat.getDateFormat(this).format(time);
        addedOn.setText(formattedDate);

        statusCode.setText(status.getStatus());

        String storeName = getResources().getString(model.getStoreName());
        String trackingButtonText = getResources().getString(R.string.openStoreTrackingButtonText);
        openStoreTrackingButton.setText(String.format(trackingButtonText, storeName));
        openStoreTrackingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(model.getTrackingString(film) != null) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText(
                            String.format(getString(R.string.trackingStringPasteLabel),
                                    getString(model.getStoreName())),
                            model.getTrackingString(film));
                    clipboard.setPrimaryClip(clip);

                    Toast.makeText(view.getContext(), R.string.trackingStringPasteToast,
                            Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(getResources().getString(model.getStoreUrl())));
                startActivity(intent);
            }
        });
    }
}
