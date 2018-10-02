package me.murks.filmchecker.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import me.murks.filmchecker.R;
import me.murks.filmchecker.background.RmStoreQueryTask;
import me.murks.filmchecker.model.RmStoreModel;
import me.murks.filmchecker.model.RossmannStoreLink;

/**
 * Fragment to choose the rm store
 * @author zouroboros
 */
public class RossmannChooseStoreFragment extends StoreModelFragment implements ErrorReceiver {
    private AddFilmWizardActivity parent;
    private View rmStoreProgressBar;
    private TextWatcher plzInputTextWatcher;
    private EditText plzInput;

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        parent = (AddFilmWizardActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rossmann_choose_store_fragment, container, false);
        plzInput = (EditText) rootView.findViewById(R.id.plz_input);
        ListView storeList = (ListView) rootView.findViewById(R.id.store_list);
        rmStoreProgressBar = rootView.findViewById(R.id.rmStoreProgressBar);
        rmStoreProgressBar.setVisibility(View.INVISIBLE);
        storeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RossmannStoreLink link = (RossmannStoreLink) adapterView.getAdapter().getItem(i);
                new RmStoreQueryTask(parent, (RmStoreModel) storeModel).execute(link.storeUrl);
            }
        });
        final ArrayAdapter<RossmannStoreLink> storeAdapter = new ArrayAdapter<>(getContext(), R.layout.rm_store_list_item, R.id.rm_store_list_item_text);
        storeList.setAdapter(storeAdapter);
        storeAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                rmStoreProgressBar.setVisibility(View.INVISIBLE);
            }
        });
        plzInputTextWatcher = new RmStoreListTextWatcher(storeAdapter, rmStoreProgressBar, this);
        plzInput.addTextChangedListener(plzInputTextWatcher);

        return rootView;
    }

    @Override
    public void errorOccured() {
        new AlertDialog.Builder(getActivity()).setMessage(R.string.generalErrorMessage)
                .setTitle(R.string.errorMessageTitle)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        plzInputTextWatcher.afterTextChanged(plzInput.getEditableText());
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getContext(), FilmListActivity.class);
                        startActivity(intent);
                    }
                }).create().show();
    }
}
