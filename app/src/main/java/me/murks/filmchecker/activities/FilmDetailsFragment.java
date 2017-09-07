package me.murks.filmchecker.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import me.murks.filmchecker.R;
import me.murks.filmchecker.io.IStatusProvider;
import me.murks.filmchecker.model.Film;

/**
 * Fragment for entering the details of a film order
 * @author zouroboros
 * @version 9/15/17.
 */
public class FilmDetailsFragment extends Fragment {

    private AddFilmWizardActivity parent;

    public FilmDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.film_details_fragment, container, false);
        if(parent.getRmQueryModel() != null && parent.getRmQueryModel().htNumber) {
            rootView.findViewById(R.id.htnText).setEnabled(false);
        } else {
            rootView.findViewById(R.id.shopId).setEnabled(false);
        }

        Button saveButton = (Button) rootView.findViewById(R.id.saveFilmButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String orderNumber = ((EditText) rootView.findViewById(R.id.orderNumber)).getText().toString();
                String shopId = rootView.findViewById(R.id.shopId).isEnabled() ?
                        ((EditText) rootView.findViewById(R.id.shopId)).getText().toString() : null;
                String htNumber = rootView.findViewById(R.id.htnText).isEnabled() ?
                        ((EditText) rootView.findViewById(R.id.htnText)).getText().toString() : null;

                DatePicker picker = ((DatePicker) rootView.findViewById(R.id.insertDatePicker));
                Calendar date = Calendar.getInstance();
                date.set(picker.getYear(), picker.getMonth(), picker.getDayOfMonth());
                IStatusProvider provider = parent.getApp().getStatusProvider().getDmStatusProvider();
                if(parent.getRmQueryModel() != null) {
                    provider = parent.getApp().getStatusProvider().getRmStatusProvider();
                }
                Film film = new Film(orderNumber, shopId, date, provider.getId(), parent.getRmQueryModel().queryEndpoint, htNumber);
                parent.getApp().addFilm(view.getContext(), film);
                Intent intent = new Intent(view.getContext(), FilmListActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        parent = (AddFilmWizardActivity) activity;
    }
}
