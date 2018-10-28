package me.murks.filmchecker.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
public class FilmDetailsFragment extends StoreModelFragment {

    private AddFilmWizardActivity parent;

    private TextInputEditText shopIdField;
    private EditText orderNumberField;
    private EditText htnNumberField;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.film_details_fragment, container, false);

        shopIdField = rootView.findViewById(R.id.shopId);
        orderNumberField = rootView.findViewById(R.id.orderNumber);
        htnNumberField = rootView.findViewById(R.id.htnNumber);

        Button saveButton = rootView.findViewById(R.id.saveFilmButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String orderNumber = orderNumberField.getText().toString();

                String shopId = storeModel.getRequiredFields().contains(StoreModel.shopId)
                        ? shopIdField.getText().toString() : null;

                String htnNumber = storeModel.getRequiredFields().contains(StoreModel.htNumber)
                        ? htnNumberField.getText().toString() : null;

                Calendar date = Calendar.getInstance();
                Film film = storeModel.getFilm(shopId, htnNumber, orderNumber, date);
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRequiredFields(storeModel);
        setLabel(storeModel);
    }

    private void setLabel(StoreModel model) {
        getView().<TextInputLayout>findViewById(R.id.shopIdLayout)
                .setHint(getResources().getString(model.getShopIdFieldName()));
    }

    private void setRequiredFields(StoreModel model) {
        Set<String> requiredFields = model.getRequiredFields();

        getView().findViewById(R.id.htnNumberLayout).setVisibility(View.GONE);
        getView().findViewById(R.id.shopIdLayout).setVisibility(View.GONE);
        getView().findViewById(R.id.orderNumberLayout).setVisibility(View.GONE);


        if(requiredFields.contains(StoreModel.shopId)) {
            getView().findViewById(R.id.shopIdLayout).setVisibility(View.VISIBLE);
        }

        if(requiredFields.contains(StoreModel.htNumber)) {
            getView().findViewById(R.id.htnNumberLayout).setVisibility(View.VISIBLE);
        }

        if(requiredFields.contains(StoreModel.orderNumber)) {
            getView().findViewById(R.id.orderNumberLayout).setVisibility(View.VISIBLE);
        }
        getView().findViewById(R.id.orderNumber).requestFocus();
    }
}
