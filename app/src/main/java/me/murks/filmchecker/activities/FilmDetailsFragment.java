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
import android.widget.TextView;

import java.util.Calendar;
import java.util.Set;

import me.murks.filmchecker.R;
import me.murks.filmchecker.model.Film;
import me.murks.filmchecker.model.StoreModel;

/**
 * Fragment for entering the details of a film order
 * @author zouroboros
 * @version 9/15/17.
 */
public class FilmDetailsFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "filmdetails";


    private AddFilmWizardActivity parent;

    public static FilmDetailsFragment newInstance(int sectionNumber) {
        FilmDetailsFragment fragment = new FilmDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.film_details_fragment, container, false);

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
                Film film = parent.getStoreModel().getFilm(shopId, htNumber, orderNumber, date);
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

    @Override
    public void onResume(){
        super.onResume();
        setRequiredFields(parent.getStoreModel());
        setLabel(parent.getStoreModel());
    }

    private void setLabel(StoreModel model) {
        TextView shopIdLabel = (TextView) getView().findViewById(R.id.shopIdLabel);
        shopIdLabel.setText(getResources().getText(model.getShopIdFieldName()));
    }

    private void setRequiredFields(StoreModel model) {
        Set<String> requiredFields = model.getRequiredFields();

        getView().findViewById(R.id.htnText).setVisibility(View.INVISIBLE);
        getView().findViewById(R.id.shopId).setVisibility(View.INVISIBLE);
        getView().findViewById(R.id.orderNumber).setVisibility(View.INVISIBLE);

        getView().findViewById(R.id.htnLabel).setVisibility(View.INVISIBLE);
        getView().findViewById(R.id.shopIdLabel).setVisibility(View.INVISIBLE);
        getView().findViewById(R.id.orderNumberLabel).setVisibility(View.INVISIBLE);

        if(requiredFields.contains(StoreModel.shopId)) {
            getView().findViewById(R.id.shopIdLabel).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.shopId).setVisibility(View.VISIBLE);
        }

        if(requiredFields.contains(StoreModel.htNumber)) {
            getView().findViewById(R.id.htnLabel).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.htnText).setVisibility(View.VISIBLE);
        }

        if(requiredFields.contains(StoreModel.orderNumber)) {
            getView().findViewById(R.id.orderNumberLabel).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.orderNumber).setVisibility(View.VISIBLE);
        }
        getView().findViewById(R.id.orderNumber).requestFocus();
    }
}
